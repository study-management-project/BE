package com.fisa.study.management.domain.comment.entity;


import com.fisa.study.management.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @CreatedDate
    private LocalDateTime date;

    private String content;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.date = now;
    }
}

