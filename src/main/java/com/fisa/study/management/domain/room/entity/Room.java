package com.fisa.study.management.domain.room.entity;

import com.fisa.study.management.domain.checkup.entity.CheckUp;

import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    @Builder.Default
//    @BatchSize(size = 20)
    private List<Snapshot> snapshotList= new ArrayList<>();

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    @Builder.Default
//    @BatchSize(size = 20)
    private List<Comment> commentList= new ArrayList<>();

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY)
    @Builder.Default
//    @BatchSize(size = 20)
    private List<CheckUp> checkUpList= new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    public void addSnapshot(Snapshot snapshot) {
        this.snapshotList.add(snapshot);
    }
    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
    public void removeSnapshot(Snapshot snapshot) {
        this.snapshotList.remove(snapshot);
    }
    public void removeComment(Comment comment) {
        this.commentList.remove(comment);
    }

}