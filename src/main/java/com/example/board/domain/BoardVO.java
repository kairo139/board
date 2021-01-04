package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BoardVO {
    private Long boardSeq;

    private Long memberSeq;

    private String memberId;

    private String boardTitle;

    private String boardContent;

    private Long boardViews;

    private String boardREGDT;

    private Date boardMODDT;
}
