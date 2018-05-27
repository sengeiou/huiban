package com.bshuiban.baselibrary.view.webview.webActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.bshuiban.baselibrary.contract.HomeworkPendingInfContract;
import com.bshuiban.baselibrary.model.Homework;
import com.bshuiban.baselibrary.model.HomeworkInfBean;
import com.bshuiban.baselibrary.model.HomeworkListData;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.HomeworkPendingInfPresent;
import com.bshuiban.baselibrary.utils.BitMapUtils;
import com.bshuiban.baselibrary.utils.FileUtils;
import com.bshuiban.baselibrary.view.activity.CameraActivity;
import com.bshuiban.baselibrary.view.activity.SelectImgActivity;
import com.bshuiban.baselibrary.view.activity.TextActivity;
import com.bshuiban.baselibrary.view.webview.javascriptInterfaceClass.HomeworkInfHtml;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_PREPARE;
import static com.bshuiban.baselibrary.view.webview.webActivity.HomeworkListWebActivity.HOME_Work_Id;

/**
 * 待完成作业
 *
 * HTML
 * 几套试卷列表 - item
 */
public class HomeworkPendingInfActivity extends BaseWebActivity<HomeworkPendingInfPresent> implements HomeworkPendingInfContract.View {
    private int homeType, workId,prepareId;
    private Gson gson = new Gson();
    private String homeworkTime;
    private Homework homework;
    private boolean isrefresh;
    /**
     * HTML暂存
     * 答案类型 mType
     * 下标 mIndex
     */
    private int mType,mIndex1,mIndex2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        homeType = intent.getIntExtra(HomeworkListWebActivity.HOME_TYPE, 1);
        workId = intent.getIntExtra(HOME_Work_Id, -1);
        prepareId = intent.getIntExtra(HOME_PREPARE, -1);
        tPresent = new HomeworkPendingInfPresent(this);
        homework=new Homework();
        loadFileHtml("workMsg");
        registerWebViewH5Interface(new HomeworkPendingHtml());
    }

    @Override
    protected void webViewLoadFinished() {
        tPresent.loadHomeworkInf(workId, homeType);
    }

    @Override
    public void updateHomeworkTitleList(List<HomeworkListData> list) {
        homework.setTitle(User.getInstance().getHomeworkInfBean().getTitle());
        loadJavascriptMethod("item", gson.toJson(list));
    }

    @Override
    public void updataAnswerResult(String answer) {//"1","3";
        //loadJavascriptMethod("");
        String methodName="subproblem";
        String url = "javascript:" + methodName + "('" + answer + "',"+mIndex1+","+mIndex2+","+mType+")";
        Log.e(TAG, "updataAnswerResult: "+url );
        mWebView.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isrefresh) {
            loadJavascriptMethod("timefn", homeworkTime);
        }
    }

    @Override
    public void startDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void fail(String error) {
        toast(error);
    }
    private File imageFile;
    private static final int CAMERA_REQUEST_CODE=100;
    private void startCamera(){
        startActivityForResult(new Intent(this, CameraActivity.class), CameraActivity.TAKE_PICTURE);
    }
    private void startSelectImage(){
        startActivityForResult(new Intent(this, SelectImgActivity.class), SelectImgActivity.SELECT_PICTURE);
    }
    private void startText(){
        startActivityForResult(new Intent(this, TextActivity.class), TextActivity.TEXT_HOMEWORK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CameraActivity.TAKE_PICTURE://拍照
                if (resultCode == RESULT_OK) {
                    String imagePath = data.getStringExtra("path");
                    if (!TextUtils.isEmpty(imagePath)) {
                        if (FileUtils.isCheckFileExist(imagePath)) {
                            BitMapUtils.saveBitmap(imagePath, BitMapUtils.duplicateBitmap(BitmapFactory.decodeFile(imagePath), 800, 500));
                            tPresent.uploadImage(imagePath);
                        } else {
                            Log.i("info", "取消拍照------>" + data);
                        }
                    }
                }
                break;
            case SelectImgActivity.SELECT_PICTURE://选择照片
                if (resultCode == RESULT_OK) {
                    String imagePath = data.getStringExtra("path");
                    if (!TextUtils.isEmpty(imagePath)) {
                        if (FileUtils.isCheckFileExist(imagePath)) {
                            BitMapUtils.saveBitmap(imagePath, BitMapUtils.duplicateBitmap(BitmapFactory.decodeFile(imagePath), 800, 500));
                            tPresent.uploadImage(imagePath);
                        } else {
                            //Log.i("info", "取消拍照------>" + data);
                        }
                    }
                }
                break;
            case TextActivity.TEXT_HOMEWORK:
                if (resultCode == RESULT_OK) {
                    String text = data.getStringExtra("text");
                    if (!TextUtils.isEmpty(text)) {
                        updataAnswerResult(text);
                    }
                }
                break;
        }
    }

    class HomeworkPendingHtml extends HomeworkInfHtml {
        /**
         * 启动答题页
         * @param type 1 拍照；2 选择图片；3 文字
         */
        @JavascriptInterface
        public void startAnswerPage(int type,int index1,int index2){
            HomeworkPendingInfActivity.this.mType=type;
            HomeworkPendingInfActivity.this.mIndex1=index1;
            HomeworkPendingInfActivity.this.mIndex2=index2;
            isrefresh=true;
            switch (type){
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
        public void setStuAnswer(String type, int index, String data) {
            JsonElement parse = new JsonParser().parse(data);
            List<Homework.Data> list = new ArrayList<>();
            if (parse.isJsonArray()) {
                JsonArray asJsonArray = parse.getAsJsonArray();
                HomeworkInfBean.DataBean homeworkInfBean = User.getInstance().getHomeworkInfBean();
                Type type1 = new TypeToken<List<String>>() {
                }.getType();
                switch (type) {
                    case "onLine":
                        List<HomeworkInfBean.DataBean.OnLineBean> onLine = homeworkInfBean.getOnLine();
                        HomeworkInfBean.DataBean.OnLineBean onLineBean;
                        List<HomeworkInfBean.DataBean.OnLineBean.NextBean> next;
                        HomeworkInfBean.DataBean.OnLineBean.NextBean nextBean;
                        for (int i = 0; i < asJsonArray.size(); i++) {
                            JsonElement jsonElement = asJsonArray.get(i);
                            Homework.Data dataHome = new Homework.Data();
                            if (jsonElement.isJsonObject()) {
                                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                                int examId = asJsonObject.get("examId").getAsInt();
                                JsonElement stuAnswer = asJsonObject.get("stuAnswer");
                                for (int j = 0; j < onLine.size(); j++) {
                                    onLineBean = onLine.get(j);
                                    next = onLineBean.getNext();
                                    for (int k = 0; next != null && k < next.size(); k++) {
                                        nextBean = next.get(k);
                                        if (examId == nextBean.getExamId()) {
                                            if (stuAnswer.isJsonArray()) {
                                                JsonArray asJsonArray1 = stuAnswer.getAsJsonArray();
                                                List<String> beanOnes = gson.fromJson(asJsonArray1, type1);
                                                nextBean.setStuAnswer(beanOnes);
                                                if(asJsonArray1.size()>0){
                                                    dataHome.setComplete(true);
                                                }else{
                                                    dataHome.setComplete(false);
                                                }
                                            } else {
                                                String asString = stuAnswer.getAsString();
                                                nextBean.setStuAnswer(asString);
                                                dataHome.setComplete(!TextUtils.isEmpty(asString));
                                                if (dataHome.isComplete()&&"radio".equals(nextBean.getOptionName()) || "check".equals(nextBean.getOptionName())) {
                                                    if (asString.equals(nextBean.getAnswer())) {
                                                        dataHome.setResult(1);
                                                    }else{
                                                        dataHome.setResult(0);
                                                    }
                                                }
                                            }
                                            list.add(dataHome);
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case "examPaper":
                        HomeworkInfBean.DataBean.ExamPaperBean.ExamBean examBean;
                        HomeworkInfBean.DataBean.ExamPaperBean examPaperBean = homeworkInfBean.getExamPaper().get(index);
                        List<HomeworkInfBean.DataBean.ExamPaperBean.ExamBean> exam = examPaperBean.getExam();
                        HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX nextBeanX;
                        List<HomeworkInfBean.DataBean.ExamPaperBean.ExamBean.NextBeanX> next1;
                        for (int i = 0; i < asJsonArray.size(); i++) {
                            JsonElement jsonElement = asJsonArray.get(i);
                            Homework.Data dataHome = new Homework.Data();
                            if (jsonElement.isJsonObject()) {
                                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                                int examId = asJsonObject.get("examId").getAsInt();
                                JsonElement stuAnswer = asJsonObject.get("stuAnswer");
                                for (int j = 0; exam != null && j < exam.size(); j++) {
                                    next1 = exam.get(j).getNext();
                                    for (int k = 0; next1 != null && k < next1.size(); k++) {
                                        nextBeanX = next1.get(k);
                                        if (examId == nextBeanX.getExamId()) {
                                            if (stuAnswer.isJsonArray()) {
                                                JsonArray asJsonArray1 = stuAnswer.getAsJsonArray();
                                                List<String> beanOnes = gson.fromJson(asJsonArray1, type1);
                                                nextBeanX.setStuAnswer(beanOnes);
                                                if(asJsonArray1.size()>0){
                                                    dataHome.setComplete(true);
                                                }else{
                                                    dataHome.setComplete(false);
                                                }
                                            } else {
                                                String asString = stuAnswer.getAsString();
                                                nextBeanX.setStuAnswer(asString);
                                                dataHome.setComplete(!TextUtils.isEmpty(asString));
                                                if (dataHome.isComplete()&&"radio".equals(nextBeanX.getOptionName()) || "check".equals(nextBeanX.getOptionName())) {
                                                    if (asString.equals(nextBeanX.getAnswer())) {
                                                        dataHome.setResult(1);
                                                    }else{
                                                        dataHome.setResult(0);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    case "video":
                        HomeworkInfBean.DataBean.VideoBean videoBean = homeworkInfBean.getVideo().get(index);
                        List<HomeworkInfBean.DataBean.VideoBean.Exam> exam1 = videoBean.getExam();
                        for (int i = 0; i < asJsonArray.size(); i++) {
                            Homework.Data dataHome = new Homework.Data();
                            JsonElement jsonElement = asJsonArray.get(i);
                            if (jsonElement.isJsonObject()) {
                                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                                int examId = asJsonObject.get("examId").getAsInt();
                                JsonElement stuAnswer = asJsonObject.get("stuAnswer");
                                for (int j = 0; exam1 != null && j < exam1.size(); j++) {
                                    HomeworkInfBean.DataBean.VideoBean.Exam exam2 = exam1.get(j);
                                    if (examId == exam2.getExamId()) {
                                        if (stuAnswer.isJsonArray()) {

                                        } else {
                                            String asString = stuAnswer.getAsString();
                                            exam2.setStuAnswer(asString);
                                            dataHome.setComplete(!TextUtils.isEmpty(asString));
                                            if (dataHome.isComplete()&&"radio".equals(exam2.getOptionName()) || "check".equals(exam2.getOptionName())) {
                                                if (asString.equals(exam2.getAnswer())) {
                                                    dataHome.setResult(1);
                                                }else{
                                                    dataHome.setResult(0);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                }
                homework.setHomework(list);
            }
        }

        @JavascriptInterface
        public void setTime(String time) {
            homeworkTime = time;
            homework.setTime(time);
        }

        @JavascriptInterface
        public void toNextPage() {
            runOnUiThread(() -> {
                startActivity(new Intent(getApplicationContext(),HomeworkResultWebActivity.class).putExtra(HOME_Work_Id,workId).putExtra(HOME_PREPARE,prepareId).putExtra("complete",false));
            });
        }
    }
}
