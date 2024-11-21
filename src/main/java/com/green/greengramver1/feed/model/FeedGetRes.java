package com.green.greengramver1.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private String contents;
    private String location;
    private long writerId;
    private String writerNm;
    private String writerPic;
    private long feedId;
    private String createdAt;

    private List<String> pics;

}


