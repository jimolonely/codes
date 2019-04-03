package com.jimo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @author jimo
 * @date 19-4-3 上午9:17
 */
public class DumpJob implements Job {

    String word;
    float floatValue;

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        JobKey key = ctx.getJobDetail().getKey();

        System.out.println(key + "," + word + "," + floatValue);
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }
}
