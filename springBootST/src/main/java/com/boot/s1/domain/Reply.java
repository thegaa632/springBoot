package com.boot.s1.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity {

    @Id
//    mysql auto_increment속성을 사용해 기본키를 만듬
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    private String replyer;

    public void changeText(String text) {
        this.replyText = text;
    }
}

