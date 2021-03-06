package com.example.board.mapper;

import com.example.board.domain.BoardVO;
import com.example.board.domain.CommentVO;
import com.example.board.domain.FileVO;
import com.example.board.domain.UploadVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardMapper {

    public List<BoardVO> findPost();

    public void insertPost(BoardVO boardVO);

    public BoardVO findPostDetail(int seq);

    public void insertUpload(UploadVO uploadVO);

    public FileVO findPostUpload(int boardSeq);

    public List<CommentVO> findPostComment(int boardSeq);

    public void insertReCommend(CommentVO commentVO);
}
