package com.bshuiban.teacher.view.webView.webFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.MessageList;
import com.bshuiban.baselibrary.view.webview.webActivity.ErrorFilterActivity;
import com.bshuiban.baselibrary.view.webview.webFragment.InteractionBaseWebViewFragment;
import com.bshuiban.teacher.contract.PrepareLessonsContract;
import com.bshuiban.teacher.present.PrepareLessonsPresent;
import com.bshuiban.teacher.view.activity.PrepareLessonInfActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static com.bshuiban.teacher.view.activity.PrepareLessonInfActivity.PREID;

/**
 * Created by xinheng on 2018/5/30.<br/>
 * describe：备课
 */
public class PrepareLessonsWebFragment extends InteractionBaseWebViewFragment<PrepareLessonsPresent> implements PrepareLessonsContract.View {
    private static final int SEARCH_REQUESTCODE = 111;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tPresent = new PrepareLessonsPresent(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadFileHtml("classList");
        PrepareLessonsHtml lessonsHtml = new PrepareLessonsHtml();
        lessonsHtml.setOnListener(new MessageList.OnMessageListListener() {
            @Override
            public void refresh() {
                tPresent.refresh();
            }

            @Override
            public void loadMore() {
                tPresent.loadMoreData();
            }
        });
        registerWebViewH5Interface(lessonsHtml);
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadLessons(null);
    }

    @Override
    public void updateList(String json) {
        loadJavascriptMethod("getContent", json);
    }

    @Override
    public void startDialog() {
        showLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void fail(String error) {
        toast(error);
    }

    @Override
    public void update(Bundle bundle) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent i) {
        switch (requestCode) {
            case SEARCH_REQUESTCODE:
                if (i != null) {
                    int mSubjectId = i.getIntExtra("mSubjectId", -1);
                    int mVersionId = i.getIntExtra("mVersionId", -1);
                    int mFasId = i.getIntExtra("mFasId", -1);
                    int mChapBranId = i.getIntExtra("mChapBranId", -1);
                    int mSeriBrandId = i.getIntExtra("mSeriBrandId", -1);
                    int stageId = i.getIntExtra("stageId", -1);
                    String organs = i.getStringExtra("organs");

                    tPresent.clearArray();
                    //tPresent.setSelectInf(mSubjectId,mVersionId,mFasId,mChapBranId,mSeriBrandId);
                    String json = new Gson().toJson(getJsonMap(mSubjectId, mVersionId, mFasId, mChapBranId, mSeriBrandId, stageId, organs));
                    tPresent.loadLessons(json);
                }
                break;

        }
    }

    private Map<String, Object> getJsonMap(int mSubjectId, int mVersionId, int mFasId, int mChapBranId, int mSeriBrandId, int stageId, String organs) {
        Map<String, Object> map = new HashMap<>();
//        map.put("userId", userId);
//        map.put("index",start);
//        map.put("limit",limit);

        map.put("subjectId",clean100(mSubjectId));
        map.put("versionId",clean100(mVersionId));
        map.put("fasId",clean100(mFasId));
        map.put("chapBranId",clean100(mChapBranId));
        map.put("seriBrandId",clean100(mSeriBrandId));
        map.put("stageId",clean100(stageId));
        map.put("schoolId", organs);
        return map;
    }

    private Object clean100(int tag) {
        if (tag == -100) {
            return "";
        }
        return tag;
    }

    class PrepareLessonsHtml extends MessageList {
        /**
         * 跳转搜索页面
         */
        @JavascriptInterface
        public void toSearchPage() {
            getActivity().runOnUiThread(() -> {
                startActivityForResult(new Intent(getActivity(), ErrorFilterActivity.class), SEARCH_REQUESTCODE);
            });
        }

        /**
         * item点击
         *
         * @param preId
         */
        @JavascriptInterface
        public void itemClick(String preId) {
            startActivity(new Intent(getActivity(), PrepareLessonInfActivity.class).putExtra(PREID, preId));
        }
    }
}
