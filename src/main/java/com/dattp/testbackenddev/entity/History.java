package com.dattp.testbackenddev.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "HISTORY")
@Getter
@Setter
public class History {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public History(Long id, Date date, User user) {
        this.id = id;
        this.date = date;
        this.user = user;
    }

    public History() {
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof History)) return false;
        History other = (History) obj;
        if(other.id==this.id) return true;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(this.date).equals(format.format(other.date));
    }
}