package com.green.greengramver1.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component //빈등록
public class MyFileUtils {
    private final String uploadPath;

    /*
    @Value("${file.directory}")은
    yaml 파일에 있는 file.directory 속성에 저장된 값을 생성자 호출할 때 값을 넣어준다.
     */
    public MyFileUtils(@Value("${file.directory}") String uploadPath){
        log.info("MyFileUtils - 생성자: {}", uploadPath);
        this.uploadPath = uploadPath;
    }
    // path = "ddd/aaa"
    // D:\LJM\GeenGramVer1/ddd/aaa
    //디렉토리 생성
    public String makeFolders(String path) {
        File file = new File(uploadPath, path); // 생성자 호출, String 타입 , 아규먼트가 인자- 인자는 보내는 값이고 이 코드는 2개의 생성자 인자를 보내고 있다.
        if(!file.exists()) { // 객체화 하고 주소값. (file.) 으로 호출했기 때문에 static 아니고 인스턴트 변수
            // 파라미터 없음 >> 호출 때 인자를 보내지 않았기 때문에
            // 리턴타입은 boolean! if() 안에서 호출했기 때문에
            // 메소드명은 exists

            file.mkdirs(); // 존재하지 않는다면 생성한다. 라서 !을 붙여줌
        }
        return file.getAbsolutePath();
    }


    //파일명에서 확장자 추출
    public String getExt(String fileName) {
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
    }

    //랜덤파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //랜덤파일명 + 확장자 생성하여 리턴
    public String makeRandomFileName(String originalFileName) {
        return makeRandomFileName() + getExt(originalFileName);
    }

    public String makeRandomFileName(MultipartFile file) {
        return makeRandomFileName(file.getOriginalFilename());
    } // 확장자 알아내려고 오리지널 파일명 알아내려고 하는 거...

    //파일을 원하는 경로에 저장
    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);
        mf.transferTo(file);
    }

}

