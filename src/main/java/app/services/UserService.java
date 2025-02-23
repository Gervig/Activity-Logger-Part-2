package app.services;

import app.daos.impl.UserDAO;
import app.dtos.UserDTO;
import app.entities.Users;
import jakarta.persistence.EntityManagerFactory;


public class UserService
{
    private static EntityManagerFactory emf;
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    //Could this be void?
    public Users persistUser(UserDTO userDTO)
    {
        Users user = Users.builder()
                .name(userDTO.getName())
                .age(userDTO.getAge())
                .weight(userDTO.getWeight())
                .build();
        user = userDAO.create(user);
        return user;
    }
}
