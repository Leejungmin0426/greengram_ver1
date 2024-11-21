package com.green.greengramver1.user;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.user.model.UserInsReq;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;

    public int postSignUp(MultipartFile pic, UserInsReq p) {
        //프로필 이미지 파일 처리
        String savedPicName = (pic != null ? myFileUtils.makeRandomFileName(pic) : null);// 삼항식,

        String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        log.info("hashedPassword: {}", hashedPassword);
        p.setUpw(hashedPassword);
        p.setPic(savedPicName);

        int result = mapper.insUser(p); //영향받은 행만 무조건 가져옴, pk값이 넘 받고 싶다면 'p' 레퍼런스 파라미터를 사용. 넣는 것도, 담는 것도 p 를 통해

        if (pic == null) {
            return result;
        }
        // 저장 위치 만든다.
        // middlePath = user/${userId}
        // filePath = user/${userId}/${savedPicName}, 저장 위치 통일이라 파일명만 데이터베이스에 저장함.
        long userId = p.getUserId(); //userId를 insert 후에 얻을 수 있다. 방금 들어간 튜플의 pk 값 얻어내기
        String middlePath = String.format("user/%d", userId);
        myFileUtils.makeFolders(middlePath);
        log.info("middlePath: {}", middlePath);
        String filePath = String.format("%s/%s", middlePath, savedPicName);
        try {
            myFileUtils.transferTo(pic, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public UserSignInRes postSignIn(UserSignInReq p) {
        UserSignInRes res = mapper.selUserForSignIn(p);
        if (res == null) { // 없는 아이디
            res = new UserSignInRes();
            res.setMessage("아이디를 확인해 주세요.");
            return res;
        }
        if( !BCrypt.checkpw(p.getUpw(), res.getUpw())){ // 비밀번호가 다를 시
            res = new UserSignInRes();
            res.setMessage("비밀번호를 확인해 주세요.");
            return res;
        }

        res.setMessage("로그인 성공!");

        return res;
    }


}