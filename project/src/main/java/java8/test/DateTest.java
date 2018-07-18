package java8.test;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jimo on 18-7-15.
 */
public class DateTest {

    @Test
    public void clock() {
        final Clock clock = Clock.systemDefaultZone();
        System.out.println(clock.millis());
        final Instant instant = clock.instant();
        final Date date = Date.from(instant);
        final Date date1 = new Date();
        System.out.println(date);
        System.out.println(date1);
    }

    @Test
    public void timeZone() {
        System.out.println(ZoneId.getAvailableZoneIds());

        final ZoneId zone1 = ZoneId.of("Asia/Aden");
        final ZoneId zone2 = ZoneId.of("Asia/Kashgar");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());
    }

    @Test
    public void localTime() {
        final ZoneId zone1 = ZoneId.of("Asia/Aden");
        final ZoneId zone2 = ZoneId.of("Asia/Kashgar");
        final LocalTime now1 = LocalTime.now(zone1);
        final LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));

        final long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        final long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hoursBetween);
        System.out.println(minutesBetween);

        final LocalTime time = LocalTime.of(23, 59, 59);
        System.out.println(time);

        final DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.CHINA);
        final LocalTime localTime = LocalTime.parse("3:30", formatter); //bug will be fixed in JDK9
        System.out.println(localTime);
    }

    @Test
    public void localDate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);

        System.out.println(today + " " + tomorrow + " " + yesterday);//2018-07-18 2018-07-19 2018-07-17

        LocalDate nationalDay = LocalDate.of(2018, Month.OCTOBER, 1);
        DayOfWeek dayOfWeek = nationalDay.getDayOfWeek();
        System.out.println(dayOfWeek);//MONDAY

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.CHINA);
        LocalDate date = LocalDate.parse("1.10.2018", formatter);
        System.out.println(date);
    }
}
