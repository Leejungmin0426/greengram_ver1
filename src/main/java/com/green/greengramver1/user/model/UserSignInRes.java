package com.green.greengramver1.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInRes {
    private long userId;
    private String nickName;
    private String pic;
    @JsonIgnore
    private String upw;
    @JsonIgnore
    private String message;
}
