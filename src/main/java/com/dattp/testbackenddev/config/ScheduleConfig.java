package com.dattp.testbackenddev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.dattp.testbackenddev.service.UserService;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    private UserService userService;
    @Scheduled(cron = "0 1 0 1 * ?")//Seconds Minutes Hours DayOfMonth Month DayOfWeek
    public void resetHistoryAttendance(){
        userService.trancateHistory();
        System.out.println("TRUNCATE HISTORY ATTENDANCE SUCCESS");
    }
}