package com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass;

import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.model.HomeworkListData;

/**
 * Created by xinheng on 2018/5/24.<br/>
 * describe：
 */
public class HomeworkInfHtml {
    /**
     * 获取一个试卷内容
     * @param index
     * @param type
     * @return
     */
    @JavascriptInterface
    public String loadData(int index, String type) {
        String json = HomeworkListData.getHomeworkInfJson(type,index);
        return json;
    }
}
