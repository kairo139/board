package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileVO {
    private int boardUploadSeq;

    private int boardSeq;

    private String boardUploadName;

    private String boardUploadOriginName;

    private String boardUploadUrl;
}

