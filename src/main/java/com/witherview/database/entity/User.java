package com.witherview.database.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@Table(name = "tbl_user")
public class User {

    @Id @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String mainIndustry;

    private String subIndustry;

    private String mainJob;

    private String subJob;

    @ColumnDefault("0")
    private Long selfPracticeCnt = 0L;

    @ColumnDefault("0")
    private Long groupPracticeCnt = 0L;

    @ColumnDefault("0")
    private Byte reliability = 70;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionList> questionLists = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyRoom> hostedStudyRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyRoomParticipant> participatedStudyRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudyHistory> studyHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelfHistory> selfHistories = new ArrayList<>();

    @Builder
    public User(String email, String password, String name,
                String mainIndustry, String subIndustry, String mainJob, String subJob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.mainIndustry = mainIndustry;
        this.subIndustry = subIndustry;
        this.mainJob = mainJob;
        this.subJob = subJob;
    }

    public void increaseSelfPracticeCnt() {
        this.selfPracticeCnt += 1;
    }

    public void increaseGroupPracticeCnt() {
        this.groupPracticeCnt += 1;
    }

    public void addQuestionList(QuestionList questionList) {
        questionList.updateOwner(this);
        this.questionLists.add(questionList);
    }

    public void addHostedRoom(StudyRoom studyRoom) {
        studyRoom.updateHost(this);
        this.hostedStudyRooms.add(studyRoom);
    }

    public void addStudyHistory(StudyHistory studyHistory) {
        studyHistory.updateUser(this);
        this.studyHistories.add(studyHistory);
    }

    public void addParticipatedRoom(StudyRoomParticipant participatedStudyRoom) {
        this.participatedStudyRooms.add(participatedStudyRoom);
    }

    public void addSelfHistory(SelfHistory selfHistory) {
        selfHistory.updateUser(this);
        this.selfHistories.add(selfHistory);
    }
}
