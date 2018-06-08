package com.bshuiban.teacher.contract;

import com.bshuiban.baselibrary.contract.BaseView;

import java.util.Map;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：
 */
public interface CorrectResultContract {
    interface View extends BaseView{
        void commitSuccess();
    }
    interface Present{
        /**
         * 提交批改学生答题
         * @param preId
         * @param type
         * @param workId
         * @param studentId
         * @param json
         */
        void commitHomeworkResult(String preId, int type, int workId, String studentId, String json);

        /**
         *
         * @param userId //int,老师id
         * @param preId 备课标识
         * @param type 课前1，课中2，课后3
         * @param workId 作业id，选传
         * @param studentId 批阅的学生id
         * @param scoreStr 题对错
         * @return
         */
        Map<String,Object> getjsonMap(String userId,String preId,int type,int workId,String studentId,String scoreStr);
    }
}
