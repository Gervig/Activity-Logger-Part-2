package app.daos;

import app.entities.UserDTO;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class UserDAO implements IDAO<UserDTO,Long>
{

    private static EntityManagerFactory emf;
    private static UserDAO instance;

    private UserDAO()
    {
    }

    //Singleton pattern
    public static UserDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new UserDAO();
        }
        return instance;
    }


    @Override
    public UserDTO create(UserDTO user)
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
        }
    }

    @Override
    public UserDTO read(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(UserDTO.class, id);
        }
    }

    @Override
    public List<UserDTO> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT u FROM ActivityDTO u", UserDTO.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of users", e);
        }
    }

    @Override
    public UserDTO update(UserDTO user)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            //Merge har en returtype, hvorfor man skal koble det på et updated objekt
            UserDTO updatedUser = em.merge(user);
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
            UserDTO user = em.find(UserDTO.class, id);
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
