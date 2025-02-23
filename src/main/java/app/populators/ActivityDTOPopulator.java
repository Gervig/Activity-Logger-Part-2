package app.populators;

import app.dtos.ActivityDTO;
import app.enums.ActivityType;
import java.time.LocalDate;
import java.time.LocalTime;

public class ActivityDTOPopulator
{
    public static ActivityDTO[] populate()
    {
        ActivityDTO a1 = ActivityDTO.builder()
                .exerciseDate(LocalDate.now())
                .exerciseType(ActivityType.CYCLING)
                .timeOfDay(LocalTime.now())
                .duration(2)
                .distance(35)
                .comment("Gennemsnitlig cykeltur")
                .build();

        ActivityDTO a2 = ActivityDTO.builder()
                .exerciseDate(LocalDate.now().minusDays(1))
                .exerciseType(ActivityType.RUNNING)
                .timeOfDay(LocalTime.of(7, 30))
                .duration(1.5)
                .distance(10)
                .comment("Morgenløb i parken")
                .build();

        ActivityDTO a3 = ActivityDTO.builder()
                .exerciseDate(LocalDate.now().minusDays(2))
                .exerciseType(ActivityType.WALKING)
                .timeOfDay(LocalTime.of(18, 0))
                .duration(1)
                .distance(5)
                .comment("Aftentur med hunden")
                .build();

        ActivityDTO a4 = ActivityDTO.builder()
                .exerciseDate(LocalDate.now().minusDays(3))
                .exerciseType(ActivityType.SWIMMING)
                .timeOfDay(LocalTime.of(15, 0))
                .duration(1.2)
                .distance(2)
                .comment("Svømning i svømmehallen")
                .build();

        ActivityDTO a5 = ActivityDTO.builder()
                .exerciseDate(LocalDate.now().minusDays(4))
                .exerciseType(ActivityType.HIKING)
                .timeOfDay(LocalTime.of(10, 0))
                .duration(3)
                .distance(12)
                .comment("Vandretur i skoven")
                .build();

        ActivityDTO a6 = ActivityDTO.builder()
                .exerciseDate(LocalDate.now().minusDays(5))
                .exerciseType(ActivityType.SKIING)
                .timeOfDay(LocalTime.of(9, 0))
                .duration(4)
                .distance(20)
                .comment("Fantastisk dag på ski")
                .build();

        return new ActivityDTO[]{a1, a2, a3, a4, a5, a6};
    }
}
