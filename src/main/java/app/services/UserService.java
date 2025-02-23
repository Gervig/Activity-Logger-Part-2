package app.services;

import app.daos.UserDAO;
import app.entities.UserDTO;
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
    public UserDTO persistUser(app.dtos.UserDTO userDTO)
    {
        UserDTO user = UserDTO.builder()
                .name(userDTO.getName())
                .age(userDTO.getAge())
                .weight(userDTO.getWeight())
                .build();
        user = userDAO.create(user);
        return user;
    }
}
