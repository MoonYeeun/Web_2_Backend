package com.witherview.database.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@Table(name = "tbl_study_room")
public class StudyRoom {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User host;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Column(nullable = false)
    private String description;

//    @NotNull
//    @Column(nullable = false)
//    private Byte nowUserCnt;
//
//    @NotNull
//    @Column(nullable = false)
//    private Byte maxUserCnt;

    @Column(columnDefinition = "varchar(50) default '무관'")
    private String industry;

    @Column(columnDefinition = "varchar(50) default '무관'")
    private String job;

    @Column(columnDefinition = "DATE")
    private LocalDate date;

    @Column(columnDefinition = "TIME")
    private LocalTime time;

    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyRoomParticipant> studyRoomParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chat> chats = new ArrayList<>();

    @Builder
    public StudyRoom(String title, String description,
                     String industry, String job,
                     LocalDate date, LocalTime time) {
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.job = job;
        this.date = date;
        this.time = time;
    }

    protected void updateHost(User host) {
        this.host = host;
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
        chat.updateStudyRoom(this);
    }

    public void addParticipants(StudyRoomParticipant studyRoomParticipant) {
        this.studyRoomParticipants.add(studyRoomParticipant);
    }

    public void update(String title, String description,
                       String industry, String job,
                       LocalDate date, LocalTime time) {
        this.title = title;
        this.description = description;
        this.industry = industry;
        this.job = job;
        this.date = date;
        this.time = time;
    }
}
