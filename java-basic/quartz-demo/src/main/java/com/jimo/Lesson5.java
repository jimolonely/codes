package com.jimo;

import org.quartz.DateBuilder;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author jimo
 * @date 19-4-3 上午9:51
 */
public class Lesson5 {

    public static void main(String[] args) {

        newTrigger()
                .withIdentity("t1", "g1")
                .startAt(futureDate(5, DateBuilder.IntervalUnit.SECOND))
                .endAt(dateOf(10, 0, 0))
                .withSchedule(simpleSchedule()
                        .withRepeatCount(10)
                        .withIntervalInSeconds(2)
                        .withMisfireHandlingInstructionFireNow()
                )
                .forJob("job1", "g1")
                .build();

    }
}
