package com.bshuiban.baselibrary.model;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public class PrepareVideoAnswerBean {
    private   int  videoId;
    private int status;//作答状态
    private boolean  isChange;//是否有改变
    private String vedioTitle;

    public String getVedioTitle() {
        return vedioTitle;
    }

    public void setVedioTitle(String vedioTitle) {
        this.vedioTitle = vedioTitle;
    }

    private List<VideoAnswerBean> videoAns;

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    public List<VideoAnswerBean> getVideoAns() {
        return videoAns;
    }

    public void setVideoAns(List<VideoAnswerBean> videoAns) {
        this.videoAns = videoAns;
    }
}
