package com.example.board.service;

import com.example.board.domain.BoardVO;
import com.example.board.domain.UploadVO;
import com.example.board.mapper.BoardMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardMapper boardMapper;

    public List<BoardVO> findPost(){ return boardMapper.findPost(); }

    public void insertPost(BoardVO boardVO){ boardMapper.insertPost(boardVO); }

    public BoardVO findPostDetail(Long seq){ return boardMapper.findPostDetail(seq); }

    public void insertUpload(UploadVO uploadVO){ boardMapper.insertUpload(uploadVO);}

    public void fileUpload(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            String fileName = file.getOriginalFilename();
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
            File destinationFile;
            String destinationFileName;
            String fileUrl = "C:\\Users\\byunghyun\\Documents\\test\\";



            do{
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
            }while(destinationFile.exists());

            destinationFile.getParentFile().mkdirs();
            file.transferTo(destinationFile);

            UploadVO uploadVO = new UploadVO();
            uploadVO.setProductSeq();
            uploadVO.setBoardUploadName(destinationFileName);
            uploadVO.setBoardUploadOriginName(fileName);
            uploadVO.setBoardUploadUrl(fileUrl);

            insertUpload(uploadVO);
        }
    }
}
