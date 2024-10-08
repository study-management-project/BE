package com.fisa.study.management.domain.comment.entity;


import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void setRoom(Room room) {
        if (this.room != null) {
            this.room.getCommentList().remove(this);
        }
        this.room = room;
        room.getCommentList().add(this);
    }
}

