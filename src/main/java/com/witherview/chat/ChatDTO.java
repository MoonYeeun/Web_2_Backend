package com.witherview.chat;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class ChatDTO {
    @Getter @Setter
    public static class MessageDTO {
        private String id = UUID.randomUUID().toString(); // message id
        private Long roomId; // 방 번호
        private String type; // 메세지 타입
        private String sender; // 메세지 보낸사람
        private String contents; // 메세지
    }

    @Getter @Setter
    public static class FeedBackDTO {
        private String id = UUID.randomUUID().toString(); // message id

        @NotNull(message = "스터디 연습내역 아이디는 반드시 입력해야 합니다.")
        private Long studyHistoryId; // 방 번호

        @NotNull(message = "피드백 보낸사람 아이디는 반드시 입력해야 합니다.")
        private Long writtenUserId; // 피드백 보낸사람

        @NotNull(message = "피드백 받는사람 아이디는 반드시 입력해야 합니다.")
        private Long targetUserId; // 피드백 받는사람

        @NotBlank(message = "피드백 메세지는 반드시 입력해야 합니다.")
        private String message; // 피드백

        private String createdAt;
    }

    @Getter @Setter
    public static class SaveDTO {
        @NotNull(message = "스터디 연습내역 아이디는 반드시 입력해야 합니다.")
        private Long studyHistoryId;
    }

    @Getter @Setter
    public static class SavedFeedBackDTO {
        private Long id;
        private Long studyHistoryId; // 스터디 연습내역 아이디
        private Long writtenUserId; // 피드백 보낸사람
        private Long targetUserId; // 피드백 받는사람
        private String message; // 피드백
        private LocalDateTime createdAt;
    }
}
