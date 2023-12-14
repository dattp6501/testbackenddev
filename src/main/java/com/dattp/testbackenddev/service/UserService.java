package com.dattp.testbackenddev.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.dattp.testbackenddev.entity.History;
import com.dattp.testbackenddev.entity.User;
import com.dattp.testbackenddev.repository.HistoryRepository;
import com.dattp.testbackenddev.repository.UserRepository;
import com.dattp.testbackenddev.utils.MyPair;
import com.dattp.testbackenddev.utils.Period;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private RedisTemplate<Object,Object> template;

    public User save(User user){
        return userRepository.save(user);
    }

    public User getById(String userId) throws Exception{
        User user = userRepository.findById(userId).orElse(null);
        if(user==null) throw new Exception("User not found");
        return user;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public History addAttendance(String userId, History history) throws Exception{
        // ====================================time
        // get list period from redis
        ArrayList<Period> times = getListTimeAttendanceFromRedis();
        // check time
        boolean ok = false;
        Date current = new Date();
        for(Period p : times){
            if(p.check(current)){
                ok = true;
                break;
            }
        }
        if(!ok) throw new Exception("Outside the attendance time");
        // =======================================  date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        // get table point from redis
        List<MyPair<String,Integer>> tablePoint = getTablePointFromRedis();
        // get date and point
        int indexcurrentAttendance = tablePoint.indexOf(new MyPair<>(format.format(history.getDate()), null));
        if(indexcurrentAttendance<0) throw new Exception("Outside the attendance time");
        // ============================================  check history
        User user = userRepository.findById(userId).orElse(null);
        if(user==null) throw new Exception("User not found");
        if(user.getHistory().contains(history)) throw new Exception("Users have taken attendance");
        history.setUser(user);
        historyRepository.save(history);
        // update point
        user.getHistory().add(history);
        user.addPoint(tablePoint.get(indexcurrentAttendance).getSecond());//get point from point table
        userRepository.save(user);
        // 
        return history;
    }

    public List<History> getHistory(String userId) throws Exception{
        User user = userRepository.findById(userId).orElse(null);
        if(user==null) throw new Exception("User not found");
        return user.getHistory();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void trancateHistory(){
        historyRepository.truncate();
    }

    public void addListTimeAttendanceToRedis(ArrayList<Period> times){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            template.opsForValue().set("times", objectMapper.writeValueAsString(times));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Period> getListTimeAttendanceFromRedis(){
        ArrayList<Period> list = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String json = (String)template.opsForValue().get("times");
        try {
            list = objectMapper.readValue(json, new TypeReference<ArrayList<Period>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addTablePointToRedis(List<MyPair<String,Integer>> table){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            template.opsForValue().set("table_point", objectMapper.writeValueAsString(table));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<MyPair<String,Integer>> getTablePointFromRedis(){
        List<MyPair<String,Integer>> list = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String json = (String)template.opsForValue().get("table_point");
        try {
            list = objectMapper.readValue(json, new TypeReference<ArrayList<MyPair<String,Integer>>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return list;
    }
}
