package com.dattp.testbackenddev;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dattp.testbackenddev.entity.User;
import com.dattp.testbackenddev.service.UserService;
import com.dattp.testbackenddev.utils.MyPair;
import com.dattp.testbackenddev.utils.Period;

@SpringBootApplication
public class TestbackenddevApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestbackenddevApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return arg0->{
			userService.save(new User("u12345", "user1", 0, null));
			// 
			ArrayList<Period> times = new ArrayList<>();
			SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm:ss");
			times.add(new Period(formatHour.parse("07:00:00"),formatHour.parse("12:00:00")));
			times.add(new Period(formatHour.parse("14:00:00"),formatHour.parse("18:00:00")));
			userService.addListTimeAttendanceToRedis(times);
			// 
			List<MyPair<String,Integer>> tablePoint = new ArrayList<>();
			for(int i=14; i<=16; i++){
				tablePoint.add(new MyPair<String,Integer>(String.format("%2d/12/2023", i), i*100));
			}
			userService.addTablePointToRedis(tablePoint);
			// 
		};
	}
}