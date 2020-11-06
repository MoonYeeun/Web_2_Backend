package com.witherview.selfPractice.Question;

import com.witherview.database.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SelfQuestionDTO {
    @Getter @Setter @Builder
    public static class SaveDTO {
        @NotBlank(message = "질문은 반드시 입력해야 합니다.")
        private String question;

        private String answer;

        @NotNull(message = "순서는 반드시 입력해야 합니다.")
        private Integer order;

        public Question toEntity() {
            return Question.builder()
                    .question(question)
                    .answer(answer)
                    .order(order)
                    .build();
        }
    }

    @Getter @Setter @Builder
    public static class UpdateDTO {
        @NotNull(message = "질문 id는 반드시 입력해야 합니다.")
        private Long id;

        @NotBlank(message = "질문은 반드시 입력해야 합니다.")
        private String question;

        private String answer;
        private Integer order;
    }

    @Getter @Setter
    public static class ResponseDTO {
        private Long id;
        private String question;
        private String answer;
        private Integer order;
    }

    @Getter @Setter
    public static class DeleteResponseDTO {
        private Long id;
    }

    @Getter @Setter @AllArgsConstructor
    public static class UpdateResponseDTO {
        private String message;
    }
}
