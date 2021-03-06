package com.witherview.database.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity @Getter
@NoArgsConstructor
@Table(name = "tbl_study_feedback")
public class StudyFeedback extends CreatedBaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id", nullable = false)
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "written_user_id", nullable = false)
    private User writtenUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_history_id", nullable = false)
    private StudyHistory studyHistory;

    @NotNull
    private Byte score;

    @NotNull
    private Boolean passOrFail;

    @Builder
    public StudyFeedback(User targetUser, User writtenUser,
                         Byte score, Boolean passOrFail) {
        this.targetUser = targetUser;
        this.writtenUser = writtenUser;
        this.score = score;
        this.passOrFail = passOrFail;
    }

    protected void updateStudyHistory(StudyHistory studyHistory) {
        this.studyHistory = studyHistory;
    }
}
