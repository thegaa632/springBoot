package com.boot.s1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class BoardImage implements Comparable<BoardImage>{


    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other) {
        return this.ord - other.ord;
    }
    //Comparable 인터페이스 사용

    public void changeBoard(Board board) {
        this.board = board;
    }
}
