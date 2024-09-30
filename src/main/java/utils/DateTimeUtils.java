package utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeUtils {

    public static long calculateDaysBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toDays();
    }
}