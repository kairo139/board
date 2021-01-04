package com.example.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadVO {

    private Long boardUploadSeq;

    private Long boardSeq;

    private String boardUploadName;

    private String boardUploadOriginName;

    private String boardUploadUrl;
}
