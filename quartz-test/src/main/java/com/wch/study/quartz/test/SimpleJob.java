package com.wch.study.quartz.test;

import java.util.Date;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/20 15:25
 */

public class SimpleJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(SimpleJob.class);

    /**
     * Quartz requires a public empty constructor so that the
     * scheduler can instantiate the class whenever it needs.
     */
    public SimpleJob() {
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();

        _log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
        System.out.println(mergedJobDataMap.get("wch"));;
    }

}

