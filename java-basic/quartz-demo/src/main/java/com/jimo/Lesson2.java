package com.jimo;

import com.jimo.job.DumpJob;
import com.jimo.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author jimo
 * @date 19-4-3 上午8:03
 */
public class Lesson2 {

    public static void main(String[] args) throws SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail jobDetail = newJob(DumpJob.class)
                .withIdentity("dumpJob", "group1")
                .usingJobData("word", "jimo yes2!")
                .usingJobData("floatValue", 9.24f)
                .build();

        SimpleTrigger trigger = newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).withRepeatCount(5))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();
    }
}
