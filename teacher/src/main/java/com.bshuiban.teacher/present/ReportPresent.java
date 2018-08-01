package com.bshuiban.teacher.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.present.BasePresent;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.teacher.contract.ReportContract;
import com.bshuiban.teacher.model.GradeListBean;
import com.bshuiban.teacher.model.SubjectListBean;
import com.bshuiban.teacher.model.TeachClassBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinheng on 2018/6/3.<br/>
 * describe：报告
 */
public class ReportPresent extends BasePresent<ReportContract.View> implements ReportContract.Present {
    private TeachClassBean gradeClass;
    private GradeListBean grade;
    private List<SubjectListBean.Data> subjectList;

    public ReportPresent(ReportContract.View view) {
        super(view);
    }

    @Override
    public void loadReportsOfClasses(int subjectId, int gradeId, String timeSlot) {
        askInternet("getReportsOfClasses", getjsonMap(subjectId, gradeId, timeSlot), new RetrofitService.CallHTML() {
            @Override
            protected void success(String msg) {
                if(isEffective()){
                    view.updateView(1,msg);
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }

    Gson gson = new Gson();
    @Override
    public void loadGraspingOfStudents(int subjectId, int gradeId, String timeSlot,int classId) {
        askInternet("getGraspingOfStudents", getjsonMap(subjectId, gradeId, timeSlot), new RetrofitService.CallHTMLJsonArray() {

            @Override
            protected void success(JsonArray msg) {
                if(isEffective()){
                    JsonArray array;
                    if(classId!=-1) {
                        array=new JsonArray();
                        if (msg != null && msg.size() > 0) {
                            for (int i = 0; i < msg.size(); i++) {
                                JsonElement jsonElement = msg.get(i);
                                if (jsonElement.isJsonObject()) {
                                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                                    int classId1 = jsonObject.get("classId").getAsInt();
                                    if (classId1 ==classId){
                                        array.add(jsonObject);
                                    }
                                }
                            }
                        }
                    }else {
                        array=msg ;
                    }
                    view.updateView(2, gson.toJson(array));
                }
            }

            @Override
            protected void fail(String error) {
                if(isEffective()){
                    view.fail(error);
                }
            }
        });
    }

    @Override
    public void loadSubjectList() {

        askInternet("getSubjectList","{\"siteId\":\""+User.getInstance().getSchoolId()+"\"}", new RetrofitService.CallResult<SubjectListBean>(SubjectListBean.class) {
            @Override
            protected void success(SubjectListBean subjectListBean) {
                if(isEffective()){
                    subjectList=subjectListBean.getData();
                    updateList();
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                    updateList();
                }
            }
        });
    }

    @Override
    public void loadGradeList() {
        askInternet("getGradeBySiteId","{\"siteId\":\""+User.getInstance().getSchoolId()+"\"}", new RetrofitService.CallResult<GradeListBean>(GradeListBean.class) {
            @Override
            protected void success(GradeListBean gradeListBean) {
                if(isEffective()) {
                    grade = gradeListBean;
                    updateList();
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                    updateList();
                }
            }
        });
    }

    @Override
    public void loadGradeClassList() {
        askInternet("getClassTeacherNowC","{\"userId\":\""+ User.getInstance().getUserId()+"\"}",new RetrofitService.CallResult<TeachClassBean>(TeachClassBean.class){

            @Override
            protected void success(TeachClassBean teachClassBean) {
                if(isEffective()){
                    gradeClass = teachClassBean;
                    updateList();
                }
            }

            @Override
            protected void error(String error) {
                if(isEffective()){
                    view.fail(error);
                    updateList();
                }
            }
        });
    }
    private int listCount;
    private void updateList(){
        ++listCount;
        if(listCount==3){
            view.loadListComplete();
        }
    }
    private Map<String, Object> getjsonMap(
            //Object schoolId,    //int,  学校id
            Object subjectId,        //int，学科类别id
            Object gradeId,        //int，	年级id
            Object timeSlot        //string，	所选月份   201801
            //Object userId        //int	,用户id
    ) {
        Map<String, Object> map= new HashMap<>(5);
        map.put("schoolId",User.getInstance().getSchoolId());
        map.put("subjectId",subjectId);
        map.put("gradeId",gradeId);
        map.put("timeSlot",timeSlot);
        map.put("userId", User.getInstance().getUserId());
        return map;
    }

    public TeachClassBean getGradeClass() {
        return gradeClass;
    }

    public GradeListBean getGrade() {
        return grade;
    }

    public List<String> getSubjectList() {
        return getListString(subjectList);
    }

    public List<String> getGradeListString() {
        return getListString(grade.getData());
    }

    private List<String> getListString(List list){
        return TextUtils.getListString(list);
    }

    public int getSubjectId(int leftIndex) {
        return subjectList.get(leftIndex).getSubjectId();
    }

    public int getGradeId(int index) {
        return grade.getData().get(index).getGradeId();
    }

    public boolean getGradeClassEff() {
        return gradeClass!=null&&HomeworkBean.isEffictive(gradeClass.getData());
    }
    public boolean getGradeListEff() {
        return grade!=null&&HomeworkBean.isEffictive(grade.getData());
    }
    public boolean getSubjectEff() {
        return HomeworkBean.isEffictive(subjectList);
    }
}
