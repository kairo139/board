package com.example.board.controller;

import com.example.board.domain.BoardVO;
import com.example.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        boardService.fileUpload(file);

        return "redirect:/board";
    }

    @RequestMapping("/detail")
    public String detail(Long seq, Model model){
        model.addAttribute("details",boardService.findPostDetail(seq));

        return "detail";
    }
}
