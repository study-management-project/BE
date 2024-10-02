package com.fisa.study.management.domain.snapshot.entity;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Snapshot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void setRoom(Room room) {
        if (this.room != null) {
            this.room.getSnapshotList().remove(this);
        }
        this.room = room;
        room.getSnapshotList().add(this);
    }
    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

