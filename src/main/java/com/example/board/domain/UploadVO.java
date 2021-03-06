package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadVO {

    private int boardUploadSeq;

    private int boardSeq;

    private String boardUploadName;

    private String boardUploadOriginName;

    private String boardUploadUrl;
}
