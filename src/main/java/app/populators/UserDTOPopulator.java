package app.populators;


import app.dtos.UserDTO;

public class UserDTOPopulator
{
    public static UserDTO[] populate()
    {
        UserDTO u1 = UserDTO.builder()
                .age(33)
                .name("Casper")
                .weight(85F)
                .build();

        UserDTO u2 = UserDTO.builder()
                .age(27)
                .name("Sophie")
                .weight(62.2F)
                .build();

        UserDTO u3 = UserDTO.builder()
                .age(40)
                .name("Michael")
                .weight(90.5F)
                .build();

        UserDTO u4 = UserDTO.builder()
                .age(22)
                .name("Emma")
                .weight(55.3F)
                .build();

        UserDTO u5 = UserDTO.builder()
                .age(36)
                .name("Daniel")
                .weight(78.7F)
                .build();

        UserDTO u6 = UserDTO.builder()
                .age(29)
                .name("Olivia")
                .weight(58.1F)
                .build();

        return new UserDTO[]{u1, u2, u3, u4, u5, u6};
    }
}
