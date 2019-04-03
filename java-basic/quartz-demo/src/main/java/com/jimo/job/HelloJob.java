package com.jimo.job;

import org.quartz.*;

/**
 * @author jimo
 * @date 19-4-3 上午9:14
 */
public class HelloJob implements Job {

    public HelloJob() {
    }

    public void execute(JobExecutionContext ctx) throws JobExecutionException {

        JobKey key = ctx.getJobDetail().getKey();

        JobDataMap map = ctx.getJobDetail().getJobDataMap();

        System.out.println(key + "," + map.getString("word") + "," + map.getFloat("floatValue"));

    }
}
