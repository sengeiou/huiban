package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.HomeworkPendingInfContract;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.LogUtils;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.HomeworkPendingInfPresent;
import com.bshuiban.baselibrary.utils.BitMapUtils;
import com.bshuiban.baselibrary.utils.DialogUtils;
import com.bshuiban.baselibrary.utils.FileUtils;
import com.bshuiban.baselibrary.utils.SpaceItemDecoration;
import com.bshuiban.baselibrary.utils.aes.Base64;
import com.bshuiban.baselibrary.view.activity.CameraActivity;
import com.bshuiban.baselibrary.view.activity.SelectImgActivity;
import com.bshuiban.baselibrary.view.activity.TextActivity;
import com.bshuiban.baselibrary.view.adapter.HomeworkCountAdapter;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.HomeworkInfHtml;

import java.io.File;
import java.util.List;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_PREPARE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * 待完成作业
 * 做题
 * HTML
 */
public class HomeworkPendingInfActivity extends BaseWebActivity<HomeworkPendingInfPresent> implements HomeworkPendingInfContract.View {
    private int homeType, workId, prepareId;
    private RecyclerView recycleView;
    private List<HomeworkBean> homeworkBean;
    /**
     * 当前题目
     */
    private HomeworkBean bean, shouldBean;
    private long startTime;
    /**
     * 试卷内容
     */
    private String jsonExam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_complete);
        Intent intent = getIntent();
        homeType = intent.getIntExtra(HomeworkListWebActivity.HOME_TYPE, 1);
        workId = intent.getIntExtra(HOME_Work_Id, -1);
        prepareId = intent.getIntExtra(HOME_PREPARE, -1);

        mWebView = findViewById(R.id.webview);

        recycleView = findViewById(R.id.recycleView);
        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {
                toNextPage();
            }
        });
        tPresent = new HomeworkPendingInfPresent(this);
        if (User.getInstance().isParents()) {
            loadFileHtml("homework_details");
            titleView.setRight_text(null, -1, 0);
        } else {
            loadFileHtml("workMsg");
        }
        registerWebViewH5Interface(new HomeworkPendingHtml());
        startTime = System.currentTimeMillis();
        int dimension = (int) getResources().getDimension(R.dimen.dp_10);
        int dimension5 = (int) getResources().getDimension(R.dimen.dp_5);
        recycleView.setPadding(dimension5, dimension5, 0, dimension5);
        recycleView.addItemDecoration(new SpaceItemDecoration(this, RecyclerView.HORIZONTAL, dimension, Color.WHITE));
    }

    @Override
    protected boolean initWebView() {
        return false;
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadHomeworkInf(workId, homeType);
    }

    @Override
    public void updateHomeworkTitleList(List<HomeworkBean> list, int time) {
        this.homeworkBean = list;
        startTime -= (time * 1000);
        if (HomeworkBean.isEffictive(list)) {
            //LogUtils.e(TAG, "updateHomeworkTitleList: "+new Gson().toJson(list));
            HomeworkCountAdapter adapter = new HomeworkCountAdapter(0);
            adapter.setList(list);
            recycleView.setAdapter(adapter);
            adapter.setOnItemClickListener(this::loadHtmlData);
            loadHtmlData(list.get(0));
        } else {
            TitleView titleView = findViewById(R.id.titleView);
            titleView.setRight_text(null, -1, 1);
        }
    }

    private void loadHtmlData(HomeworkBean bean1) {
        shouldBean = this.bean;
        this.bean = bean1;
        String type = this.bean.getType();
        int pageIndex = this.bean.getPageIndex();
        int typeIndex = this.bean.getTypeIndex();
        int problemIndex = this.bean.getProblemIndex();
        String json = HomeworkBean.getProblemContent(User.getInstance().getHomeworkInfBean(), type, pageIndex, typeIndex, problemIndex);
        loadJavascriptMethod("rende", json);
    }

    @Override
    public void updateAnswerResult(String answer) {
        LogUtils.e("TAG", "url=" + answer);
        changeHomeworkStuAnswer(bean, answer);
        loadJavascriptMethod("subproblem", answer);
    }

    /**
     * 修改/填写 答案
     *
     * @param bean
     * @param answer
     */
    private void changeHomeworkStuAnswer(HomeworkBean bean, String answer) {
        String type = bean.getType();
        int pageIndex = bean.getPageIndex();
        int typeIndex = bean.getTypeIndex();
        int problemIndex = bean.getProblemIndex();
        HomeworkBean.setProblemAnswer(bean, answer, 0, User.getInstance().getHomeworkInfBean(), type, pageIndex, typeIndex, problemIndex);

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
//        TitleView titleView=findViewById(R.id.titleView);
//        titleView.setRight_text(null,-1,1);
    }

    private File imageFile;
    private static final int CAMERA_REQUEST_CODE = 100;

    private void startCamera() {
        startActivityForResult(new Intent(this, CameraActivity.class), CameraActivity.TAKE_PICTURE);
    }

    private void startSelectImage() {
        startActivityForResult(new Intent(this, SelectImgActivity.class), SelectImgActivity.SELECT_PICTURE);
    }

    private void startText() {
        startActivityForResult(new Intent(this, TextActivity.class), TextActivity.TEXT_HOMEWORK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CameraActivity.TAKE_PICTURE://拍照
                if (resultCode == RESULT_OK) {
                    String imagePath = data.getStringExtra("path");
                    updateImage(imagePath);
                }
                break;
            case SelectImgActivity.SELECT_PICTURE://选择照片
                if (resultCode == RESULT_OK) {
                    String imagePath = data.getStringExtra("path");
                    updateImage(imagePath);
                }
                break;
            case TextActivity.TEXT_HOMEWORK:
                if (resultCode == RESULT_OK) {
                    String text = data.getStringExtra("text");
                    if (!TextUtils.isEmpty(text)) {
                        updateAnswerResult(text);
                    }
                }
                break;
        }
    }

    private void updateImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            String name = FileUtils.isCheckFileExist(imagePath);
            if (!TextUtils.isEmpty(name)) {
                BitMapUtils.saveBitmap(User.imagePath + name, BitMapUtils.duplicateBitmap(BitmapFactory.decodeFile(imagePath), 800, 500));
                tPresent.uploadImage(imagePath);
            } else {
                //Log.i("info", "取消拍照------>" + data);
            }
        }
    }

    class HomeworkPendingHtml extends HomeworkInfHtml {
        @JavascriptInterface
        public int getUserType() {
            return User.getInstance().getUserType();
        }

        @JavascriptInterface
        public boolean isParentsLook() {
            return User.getInstance().isParents();
        }

        /**
         * 启动答题页
         *
         * @param type 1 拍照；2 选择图片；3 文字
         */
        @JavascriptInterface
        public void startAnswerPage(int type) {
            switch (type) {
                case 1:
                    startCamera();
                    break;
                case 2:
                    startSelectImage();
                    break;
                case 3:
                    startText();
                    break;
            }
        }

        @JavascriptInterface
        public void updateStuAnswer(String stuAnswer) {
            Log.e(TAG, "setStuAnswer: " + stuAnswer);
            changeHomeworkStuAnswer(shouldBean, stuAnswer);
        }

        @JavascriptInterface
        public void openImage(String src) {
            Log.e(TAG, "openImage: " + src);
            runOnUiThread(() -> DialogUtils.showImageDialog(HomeworkPendingInfActivity.this, src));
        }

        @JavascriptInterface
        public String getExamContent() {
            return jsonExam;
        }
    }

    boolean check;

    private void toNextPage() {
        if (!check) {
            check = true;
            long time = (System.currentTimeMillis() - startTime) / 1000;
            shouldBean = bean;
            loadJavascriptMethod("updateStudentAnswer");
            User.getInstance().getHomeworkInfBean().setTimes(time + "");
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getApplicationContext(), HomeworkResultWebActivity.class).putExtra(HOME_Work_Id, workId).putExtra(HOME_PREPARE, prepareId)
                        .putExtra("complete", false)
                        .putExtra("time", time));
                check = false;
            }, 500);
        }
    }
}
