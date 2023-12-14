package com.dattp.testbackenddev.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class Period {
    @JsonFormat(pattern = "HH:mm:ss")
    private Date from;
    @JsonFormat(pattern = "HH:mm:ss")
    private Date to;
    public Period() {
        super();
    }

    public Period(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public boolean check(Date date){
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        return 0>=format.format(from).compareTo(format.format(date))
        &&format.format(date).compareTo(format.format(to))<=0;
    }
}