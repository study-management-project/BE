package com.fisa.study.management.domain.checkup.entity;


import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "check_up")
public class CheckUp extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Embedded
    @Builder.Default
    private OX ox = new OX();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Setter
    private Boolean isOpen;

    public void addO() {
        this.ox.addO();
    }

    public void addX() {
        this.ox.addX();
    }

    public void setRoom(Room room) {
        if (this.room != null) {
            this.room.getCheckUpList().remove(this);
        }
        this.room = room;
        room.getCheckUpList().add(this);
    }

    @PrePersist
    public void onPrePersist() {
        if (this.title == null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            this.title = LocalDateTime.now().format(formatter);
        }
    }

}
