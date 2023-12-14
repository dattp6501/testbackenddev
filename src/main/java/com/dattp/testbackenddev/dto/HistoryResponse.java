package com.dattp.testbackenddev.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryResponse {
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private int point;
    private boolean isAttendance;

    @Builder
    public HistoryResponse(Long id, Date date, boolean isAttendance, int point) {
        this.id = id;
        this.date = date;
        this.isAttendance = isAttendance;
        this.point = point;
    }
    public HistoryResponse() {
    }
}
