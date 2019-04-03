package com.jimo;

import com.jimo.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author jimo
 * @date 19-4-3 上午8:03
 */
public class Lesson1 {

    public static void main(String[] args) throws SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail jobDetail = newJob(HelloJob.class).withIdentity("myJob", "group1").build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }
}
