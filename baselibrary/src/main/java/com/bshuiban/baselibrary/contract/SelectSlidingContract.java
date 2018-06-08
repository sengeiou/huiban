package com.bshuiban.baselibrary.contract;

import com.bshuiban.baselibrary.model.TalVideoSelectedDetailBean;
import com.google.gson.JsonArray;

import java.util.List;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public interface SelectSlidingContract {
    interface View extends BaseView{
        void updateKeMu(List<TalVideoSelectedDetailBean.Data> data);
        void updateBanben(List<TalVideoSelectedDetailBean.Data> data);
        void updateFence(List<TalVideoSelectedDetailBean.Data> data);
        void updateMuLu(JsonArray msg);
        void updateXueDuan(List<TalVideoSelectedDetailBean.Data> data);
    }
    interface Present{
        void loadKeMu();
        void loadBanben(int subjectId);
        void loadFence(int subjectId,int versionId);

        /**
         * 筛选章节目录树：getFilterChapterL
         * @param subjectId 科目id
         * @param versionId 版本id
         * @param fasId 分册id
         */
        void loadZhangJieMuLu(int subjectId,int versionId,int fasId);

        /**
         * 筛选知识点目录树：getFilterKnowL
         * @param subjectId 科目id
         * @param stageId 学段id
         */
        void loadZhiShiDianMuLu(int subjectId,int stageId);
        void loadXueDuan();
    }
}
