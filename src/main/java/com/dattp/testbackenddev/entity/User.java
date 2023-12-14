package com.dattp.testbackenddev.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_")
@Getter
@Setter
public class User {
    @Id
    private String id;

    private String name;

    private int point;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> history;

    public User(String id, String name, int point, List<History> history) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.history = history;
    }

    public User() {
    }

    public void addPoint(int point){
        this.point += point;
    }
}