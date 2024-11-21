package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.FeedPicDto;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final MyFileUtils myFileUtils;


    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        int result = mapper.insFeed(p);

        // long feedId = p.getFeedId();


        String middlePath = String.format("feed/%d", p.getFeedId());
        // 폴더만들기
        myFileUtils.makeFolders(middlePath);

        FeedPicDto feedPicDto = new FeedPicDto();
        // feedPicDto에 feedId값 넣어주세요..


        feedPicDto.setFeedId(p.getFeedId());
        List<String> picsss = new ArrayList<>();

        for (MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            picsss.add(savedPicName);
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            feedPicDto.setPic(savedPicName);

            mapper.insFeedPic(feedPicDto);
        }


            FeedPostRes res = new FeedPostRes();


            // 삽입 성공 - 응답 객체 구성
            res.setFeedId(p.getFeedId()); // 업로드된 피드 ID를 응답에 포함
            res.setPics(picsss);
            res.setMessage("업로드 성공!");
            return res;
        }
    }
