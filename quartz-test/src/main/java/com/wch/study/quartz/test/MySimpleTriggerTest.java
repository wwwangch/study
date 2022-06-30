package com.wch.study.quartz.test;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.DateBuilder.evenMinuteDate;

import org.apache.tomcat.jni.User;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/6/20 15:18
 */
public class MySimpleTriggerTest {
    public void run() throws SchedulerException {
        SchedulerFactory schedulerFactory=new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobKey jobKey=new JobKey("job1","group1");

        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("wch",new User());
        JobDetail jobDetail=newJob().withIdentity(jobKey)
                .ofType(SimpleJob.class)
                .setJobData(jobDataMap)
                .build();

        Date now=new Date();
        Date nextMin = evenMinuteDate(now);

        Trigger trigger=newTrigger()
                .forJob(jobDetail)
                .startAt(nextMin)
                .endAt(evenMinuteDate(nextMin))
                .build();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        try {
            // wait 65 seconds to show job
            Thread.sleep(65L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        // shut down the scheduler
        scheduler.shutdown(true);

    }

    public static void main(String[] args) throws SchedulerException {
        MySimpleTriggerTest mySimpleTriggerTest=new MySimpleTriggerTest();
        mySimpleTriggerTest.run();
    }
}
