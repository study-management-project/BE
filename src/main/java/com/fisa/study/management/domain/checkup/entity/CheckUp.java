package com.fisa.study.management.domain.checkup.entity;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title ;

    @Embedded
    private OX ox;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @PrePersist
    private void setDefaultValues() {
        if (this.title == null || this.title.isEmpty()) {
            this.title = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }
    }
    private void addO(){
        this.ox.addO();
    }
    private void addX(){
        this.ox.addX();
    }
}
