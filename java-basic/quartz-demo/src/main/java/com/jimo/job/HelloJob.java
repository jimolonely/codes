package com.jimo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author jimo
 * @date 19-4-3 上午9:05
 */
public class HelloJob implements Job {

    public HelloJob() {
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("now is: " + System.currentTimeMillis());
    }
}
