package com.fisa.study.management.domain.checkup.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
public class OX {
    private int O=0;

    private int X=0;

    public void addO() {
        this.O++;
    }
    public void addX() {
        this.X++;
    }
}
