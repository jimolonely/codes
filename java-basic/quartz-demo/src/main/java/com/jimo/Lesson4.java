package com.jimo;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Date;

/**
 * @author jimo
 * @date 19-4-3 上午9:39
 */
public class Lesson4 {

    public static void main(String[] args) throws SchedulerException {
        HolidayCalendar cal = new HolidayCalendar();
        cal.addExcludedDate(new Date());

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.addCalendar("holiday", cal, false, false);
    }
}
