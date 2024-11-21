package com.green.greengramver1.user;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.user.model.UserInsReq;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "유저", description = "회원가입, 로그인")
public class UserController {
    private final UserService service;

    /*
    파일 + Data
        파일 업로드 시에는 RequestBody를 사용할 수 없음.
        RequestPart 애노테이션 사용해야 함
     */
    @PostMapping("sign-up")
    @Operation(summary = "회원 가입")
    public ResultResponse<Integer> signUp(@RequestPart UserInsReq p, @RequestPart(required = false) MultipartFile pic) {// RequestPart는 파일 받을 때
        log.info("UserInsReq: {}, file: {}", p, pic != null ? pic.getOriginalFilename() : null); // 에러 안 나기 위해 null 어쩌구 삼항식을... null이 아니었을 때만 메소드 주소값 호출
        int result = service.postSignUp(pic, p);


        return ResultResponse.<Integer>builder()
                .resultMessage("회원가입 완료")
                .resultData(result)
                .build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq p) { //값 받을 때
        UserSignInRes res = service.postSignIn(p);
        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(res.getMessage())
                .resultData(res)
                .build();

    }
}
