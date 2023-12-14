package com.dattp.testbackenddev.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.testbackenddev.dto.HistoryResponse;
import com.dattp.testbackenddev.dto.Response;
import com.dattp.testbackenddev.dto.UserRequest;
import com.dattp.testbackenddev.dto.UserResponse;
import com.dattp.testbackenddev.entity.History;
import com.dattp.testbackenddev.entity.User;
import com.dattp.testbackenddev.service.UserService;
import com.dattp.testbackenddev.utils.MyPair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/add_attendance")
    public ResponseEntity<Response> addAttendance(@RequestBody @Valid UserRequest userRequest) throws Exception {
        userService.addAttendance(userRequest.getId(), new History(null, new Date(), null));
        return ResponseEntity.ok().body(
            Response.builder()
            .code(HttpStatus.OK.value())
            .message("Success")
            .build()
        );  
    }

    @PostMapping("/get_point")
    public ResponseEntity<Response> getPoint(@RequestBody @Valid UserRequest userRequest) throws Exception {
        UserResponse userResponse = new UserResponse();
        User user = userService.getById(userRequest.getId());
        BeanUtils.copyProperties(user, userResponse);
        return ResponseEntity.ok().body(
            Response.builder()
            .code(HttpStatus.OK.value())
            .data(userResponse)
            .message("Success")
            .build()
        );
    }

    @PostMapping("/get_history")
    public ResponseEntity<Response> getHistory(@RequestBody @Valid UserRequest userRequest) throws Exception {
        List<History> history = userService.getHistory(userRequest.getId());
        return ResponseEntity.ok().body(
            Response.builder()
            .code(HttpStatus.OK.value())
            .data(history)
            .message("Success")
            .build()
        );
    }

    @PostMapping("/get_attendance")
    public ResponseEntity<Response> getAttendance(@RequestBody @Valid UserRequest userRequest) throws Exception {
        // get table point from redis
        List<MyPair<String,Integer>> tablePoint = userService.getTablePointFromRedis();
        //
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        List<History> history = userService.getHistory(userRequest.getId());
        List<HistoryResponse> list = new ArrayList<>();
        //
        tablePoint.forEach((p)->{
            try {
                HistoryResponse historyResponse = HistoryResponse.builder().point(p.getSecond())
                .date(format.parse(p.getFirst()))
                .build();
                boolean isExists = false;
                for(History h : history){
                    if(p.getFirst().equals(format.format(h.getDate()))){//date hitory = date table point
                        isExists = true;
                        break;
                    }
                }
                historyResponse.setAttendance(isExists);
                list.add(historyResponse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
        });
        return ResponseEntity.ok().body(
            Response.builder()
            .code(HttpStatus.OK.value())
            .data(list)
            .message("Success")
            .build()
        );
    }
}