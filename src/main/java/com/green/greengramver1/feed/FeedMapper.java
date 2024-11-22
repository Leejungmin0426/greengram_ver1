package com.green.greengramver1.feed;


import com.green.greengramver1.feed.model.FeedPicDto;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {
    int insFeed (FeedPostReq p);

    int insFeedPic(FeedPicDto feedPicDto);
}
