package com.green.greengramver1.user;

import com.green.greengramver1.user.model.UserInsReq;
import com.green.greengramver1.user.model.UserSignInRes;
import com.green.greengramver1.user.model.UserSignInReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserInsReq p);
    UserSignInRes selUserForSignIn (UserSignInReq p);

}
