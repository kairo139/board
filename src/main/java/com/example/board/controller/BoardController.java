package com.example.board.controller;

import com.example.board.domain.BoardVO;
import com.example.board.domain.CommentVO;
import com.example.board.domain.FileVO;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping("/board")
    public String board(Model model){
        model.addAttribute("posts",boardService.findPost());

        return "board";
    }

    @RequestMapping("/write")
    public String write(Model model){

        return "write";
    }

    @RequestMapping("/insert")
    public String write(@ModelAttribute BoardVO boardVO, @RequestPart MultipartFile file) throws IOException {
        boardService.insertPost(boardVO);
        boardService.fileUpload(file,boardVO);

        return "redirect:/board";
    }

    @RequestMapping("/detail")
    public String detail(int seq, Model model){
        model.addAttribute("details",boardService.findPostDetail(seq));
        model.addAttribute("upload",boardService.findPostUpload(seq));
        model.addAttribute("comments",boardService.findPostComment(seq));

        return "detail";
    }

    @RequestMapping("/download/{seq}")
    private void download(@PathVariable("seq") int seq, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, Exception{
        boardService.download(seq, request, response);
    }

    @ResponseBody
    @RequestMapping("/insertComment")
    private void insertComment(@RequestBody CommentVO commentVO){

        boardService.insertReCommend(commentVO);
    }
}
