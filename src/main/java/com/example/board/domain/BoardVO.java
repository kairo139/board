package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BoardVO {
    private int boardSeq;

    private int memberSeq;

    private String memberId;

    private String boardTitle;

    private String boardContent;

    private int boardViews;

    private String boardREGDT;

    private Date boardMODDT;

}
