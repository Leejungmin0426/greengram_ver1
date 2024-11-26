package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final MyFileUtils myFileUtils;

    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {

        FeedPostRes res = new FeedPostRes();
        int result = mapper.insFeed(p);

        //파일 저장
        //middlePath: feed/${feedId}
        long feedId = p.getFeedId();
        String middlePath = String.format("feed/%d", feedId);

        //폴더 만들기
        myFileUtils.makeFolders(middlePath);

        //파일 저장
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);
        //feedPicDto에 feedId값 넣어주세요.
        res.setFeedId(feedId);
        List<String> picList = new ArrayList<>(pics.size());
        for(MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            feedPicDto.setPic(savedPicName);
            picList.add(savedPicName);

            mapper.insFeedPic(feedPicDto); //만들어주세요.
        }
        res.setPics(picList);
        return res;
    }

    public List<FeedGetRes> getFeedList(FeedGetReq p) {
        List<FeedGetRes> list = mapper.selFeedList(p); // JPA ' N + 1 이슈 ', 나쁜 문제! 해결해야 함. 하지만 쉽다. select 한 번으로 해결하는 게 best지만 느릴수도 있다. 두 번째는 select 2번으로 해결. 장단점이 있다.
        //사진 매핑, 튜플 하나당 select 한 번씩.... 하지만 어떤 원리인지는 알아야 한다.
        for(FeedGetRes res : list) {
            //DB에서 각 피드에 맞는 사진 정보를 가져온다. N + 1 이슈는 개발자의 해결능력이 낮음을 보여준다.
            List<String> picList = mapper.selFeedPicList(res.getFeedId());
            res.setPics(picList);
        }
        return list;
    }

}
