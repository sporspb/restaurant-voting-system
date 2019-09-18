package ru.spor.topjava.graduation.to;

import ru.spor.topjava.graduation.HasId;

import java.io.Serializable;

public abstract class BaseTo implements HasId, Serializable {
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}