package com.example.board.service;

import com.example.board.domain.BoardVO;
import com.example.board.domain.CommentVO;
import com.example.board.domain.FileVO;
import com.example.board.domain.UploadVO;
import com.example.board.mapper.BoardMapper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardMapper boardMapper;

    //게시글 모두 가져오기
    public List<BoardVO> findPost(){ return boardMapper.findPost(); }

    //게시글 등록
    public void insertPost(BoardVO boardVO){ boardMapper.insertPost(boardVO); }

    //게시글 상세
    public BoardVO findPostDetail(int seq){ return boardMapper.findPostDetail(seq); }

    //파일 업로드 정보 DB 저장
    public void insertUpload(UploadVO uploadVO){ boardMapper.insertUpload(uploadVO);}

    //파일 업로드
    public void fileUpload(MultipartFile file,BoardVO boardVO) throws IOException {
        if(!(file.isEmpty())){
            String fileName = file.getOriginalFilename();
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();

            File destinationFile;
            String destinationFileName;

            //파일 저장 경로
            String fileUrl = "C:\\Users\\byunghyun\\Documents\\test\\";

            do{//파일 이름 중복을 피하기 위해서 랜덤값 + 이름
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
                destinationFile = new File(fileUrl + destinationFileName);
            }while(destinationFile.exists());

            destinationFile.getParentFile().mkdirs();
            file.transferTo(destinationFile);

            UploadVO uploadVO = new UploadVO();
            uploadVO.setBoardSeq(boardVO.getBoardSeq());
            uploadVO.setBoardUploadName(destinationFileName);
            uploadVO.setBoardUploadOriginName(fileName);
            uploadVO.setBoardUploadUrl(fileUrl);

            insertUpload(uploadVO);
        }
    }

    //파일 다운로드
    public void download(int detailSeq, HttpServletRequest request, HttpServletResponse response){
        try{
            request.setCharacterEncoding("UTF-8");  //한글 처리를 위해서 UTF-8 설정
            FileVO fileVO = findPostUpload(detailSeq);

            String fileUrl = fileVO.getBoardUploadUrl();

            fileUrl += "/";
            String savePath = fileUrl;  //url 백업
            String fileName = fileVO.getBoardUploadName();

            String originalFileName = fileVO.getBoardUploadOriginName();
            InputStream in = null;
            OutputStream out = null;
            File file = null;
            Boolean skip = false;
            String client = "";

            try{
                file = new File(savePath,fileName);
                in = new FileInputStream(file);
            }catch (FileNotFoundException fe){
                skip =true;
            }

            client = request.getHeader("User-Agent");

            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description","HTML Generated Data");

            if(!skip){
                if(client.indexOf("MSIE") != -1){
                    response.setHeader("Content-Disposition","attachment; filename=\""
                            + java.net.URLEncoder.encode(originalFileName,"UTF-8").
                            replaceAll("\\+","\\ ") + "\"");
                }else if(client.indexOf("Triendt") != -1){
                    response.setHeader("Content-Disposition","attachment; filename=\""
                            + java.net.URLEncoder.encode(originalFileName,"UTF-8").
                            replaceAll("\\+","\\ ") + "\"");
                }else{
                    response.setHeader("Content-Disposition","attachment; filename=\""
                            + new String(originalFileName.getBytes("UTF-8"),"ISO8859_1") + "\"");
                    response.setHeader("Content-Type","application/octet-stream; charset=utf-8");
                }

                response.setHeader("Content-Lenth",""+file.length());
                out = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;

                while ((leng = in.read(b)) > 0){
                    out.write(b, 0, leng);
                }
            }else{
                response.setContentType("text/html; chatset=UTF-8");
                PrintWriter outWrite = response.getWriter();
                outWrite.println("<script> alert('파일을 찾을 수 없습니다.'); history.back(); </script>");
                outWrite.flush();
            }
            in.close();
            out.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public FileVO findPostUpload(int boardSeq){ return boardMapper.findPostUpload(boardSeq); }

    public List<CommentVO> findPostComment(int boardSeq){ return boardMapper.findPostComment(boardSeq); }

    public void insertReCommend(CommentVO commentVO){ boardMapper.insertReCommend(commentVO); }
}
