package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentVO {

    private int boardCommentSeq;

    private int boardCommentParent;

    private int boardSeq;

    private int memberSeq;

    private String boardCommentContent;

    private String boardCommentREGDT;
}
