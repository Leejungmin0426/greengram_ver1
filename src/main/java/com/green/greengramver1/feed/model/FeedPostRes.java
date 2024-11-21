package com.green.greengramver1.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedPostRes { // 응답 때 줘야하는 친구
    // feed PK값과 파일이름 여러 개를 리턴할 수 있어야 함.
    private long feedId;

    private List<String> pics;
    @JsonIgnore
    private String message;
}
