package app.populators;

import app.entities.Users;

public class UserPopulator
{
    public static Users[] populate()
    {
        Users u1 = Users.builder()
                .age(33)
                .name("Casper")
                .weight(85.0F)
                .build();

        Users u2 = Users.builder()
                .age(27)
                .name("Sophie")
                .weight(62.2F)
                .build();

        Users u3 = Users.builder()
                .age(40)
                .name("Michael")
                .weight(90.5F)
                .build();

        Users u4 = Users.builder()
                .age(22)
                .name("Emma")
                .weight(55.3F)
                .build();

        Users u5 = Users.builder()
                .age(36)
                .name("Daniel")
                .weight(78.7F)
                .build();

        Users u6 = Users.builder()
                .age(29)
                .name("Olivia")
                .weight(58.1F)
                .build();

        return new Users[]{u1, u2, u3, u4, u5, u6};
    }
}
