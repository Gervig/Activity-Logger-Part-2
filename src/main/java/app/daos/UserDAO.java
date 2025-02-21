package app.daos;

import app.dtos.UserDTO;
import app.entities.Users;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDAO implements IDAO<Users,Long>
{

    private static EntityManagerFactory emf;
    private static UserDAO INSTANCE;

    //Constructoren - fordi det er singleton pattern, laver man en privat constructor så den er cuttet af og andre ikke kan bruge den
    private UserDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    //Singleton pattern
    public static UserDAO getInstance(EntityManagerFactory emf)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new UserDAO(emf);
        }
        return INSTANCE;
    }


    @Override
    public Users create(Users user)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating user", e);
            //Eventuelt kunne man have en rollback
        }
    }

    @Override
    public Users read(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Users.class, id);
        }
    }

    @Override
    public List<Users> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT u FROM Activity u", Users.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of users", e);
        }
    }

    @Override
    public Users update(Users user)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            //Merge har en returtype, hvorfor man skal koble det på et updated objekt
            Users updatedUser = em.merge(user);
            //Ryd cache
            em.flush();
            //Vi vil gerne sikre os det KUN er det ene objekt der er opdateret der kommer med
            em.refresh(updatedUser);
            em.getTransaction().commit();
            return updatedUser;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating user", e);
        }
    }

    @Override
    public void delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Users user = em.find(Users.class, id);
            if (user == null)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting user, user was not found");
            }
            em.remove(user);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error removing user", e);
        }

    }

}
