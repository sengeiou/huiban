package com.bshuiban.baselibrary.contract;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public interface HuiFuDaoListContract {
    interface View extends ListContract.View{
        void loadScreeningData(String json);
        void loadAllSubject(String json);
        void loadGuessWhatYouThink(String json);
    }
    interface Present{
        /**
         * 筛选获取课程
         * @param key
         * @param json
         */
        void screeningLesson(String key,String json);

        /**
         * 获取慧辅导科目列表
         */
        void getAllSubject();

        /**
         * 获取筛选的数据
         * @param json
         */
        void getScreeningData(String json);

        /**
         * 猜你所想
         */
        void guessWhatYouThink(String subjectId);

    }
}
