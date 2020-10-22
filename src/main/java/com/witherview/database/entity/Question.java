package com.witherview.database.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity @Getter
@NoArgsConstructor
@Table(name = "tbl_question")
public class Question {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_list_id", nullable = false)
    private QuestionList belongList;

    @NotBlank
    @Column(nullable = false)
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @NotNull
    @Column(name = "order_num", nullable = false)
    private Integer order;

    @Builder
    public Question(String question, String answer, Integer order) {
        this.question = question;
        this.answer = answer;
        this.order = order;
    }

    protected void updateBelongList(QuestionList questionList) {
        this.belongList = questionList;
    }

    public void updateOrder(Integer order) {
        this.order = order;
    }
}