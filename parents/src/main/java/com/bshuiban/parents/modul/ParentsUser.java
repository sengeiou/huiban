package com.bshuiban.parents.modul;

import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.ResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinheng on 2018/5/19.<br/>
 * describe：
 */
public class ParentsUser extends ResultBean {
    /**
     * data : {"userId":"2030246","roleId":1,"realName":"戴帅帅","icoPath":"","imgPath":"","sex":1,"email":"15467167222@qq.com","phone":"15467167222","gradeId":8,"schoolId":45,"classId":[3000153],"schoolName":"青岛实验初级中学","gradeName":"八年级","provinceId":0,"provinceName":"","areaId":0,"areaName":"","cityId":0,"cityName":"","interest":[],"parentPhone":"15711168622","isHeader":0,"className":["八3班N"]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 2030246
         * roleId : 1
         * realName : 戴帅帅
         * icoPath :
         * imgPath :
         * sex : 1
         * email : 15467167222@qq.com
         * phone : 15467167222
         * gradeId : 8
         * schoolId : 45
         * classId : [3000153]
         * schoolName : 青岛实验初级中学
         * gradeName : 八年级
         * provinceId : 0
         * provinceName :
         * areaId : 0
         * areaName :
         * cityId : 0
         * cityName :
         * interest : []
         * parentPhone : 15711168622
         * isHeader : 0
         * className : ["八3班N"]
         */

        private String userId;
        private int roleId;
        private String realName;
        private String icoPath;
        private String imgPath;
        private int sex;
        private String email;
        private String phone;
        private int gradeId;
        private int schoolId;
        private String schoolName;
        private String gradeName;
        private int provinceId;
        private String provinceName;
        private int areaId;
        private String areaName;
        private int cityId;
        private String cityName;
        private String parentPhone;
        private int isHeader;
        private List<String> classId;
        private List<?> interest;
        private List<String> className;
        private List<LoginResultBean.Data.StuArrBean> stuArr;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getIcoPath() {
            return icoPath;
        }

        public void setIcoPath(String icoPath) {
            this.icoPath = icoPath;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getGradeId() {
            return gradeId;
        }

        public void setGradeId(int gradeId) {
            this.gradeId = gradeId;
        }

        public int getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(int schoolId) {
            this.schoolId = schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getParentPhone() {
            return parentPhone;
        }

        public void setParentPhone(String parentPhone) {
            this.parentPhone = parentPhone;
        }

        public int getIsHeader() {
            return isHeader;
        }

        public void setIsHeader(int isHeader) {
            this.isHeader = isHeader;
        }

        public List<String> getClassId() {
            return classId;
        }

        public void setClassId(List<String> classId) {
            this.classId = classId;
        }

        public List<?> getInterest() {
            return interest;
        }

        public void setInterest(List<?> interest) {
            this.interest = interest;
        }

        public List<String> getClassName() {
            return className;
        }

        public void setClassName(List<String> className) {
            this.className = className;
        }

        public List<LoginResultBean.Data.StuArrBean> getStuArr() {
            return stuArr;
        }

        public void setStuArr(List<LoginResultBean.Data.StuArrBean> stuArr) {
            this.stuArr = stuArr;
        }

        public void setDataContent(LoginResultBean.Data dataContent) {
            dataContent.setRealName(realName);
            dataContent.setIcoPath(icoPath);
//            dataContent.setSchoolId(schoolId+"");
//            dataContent.setSchoolName(schoolName);
//            dataContent.setGradeId(gradeId+"");
//            dataContent.setClassId(classId);
//            dataContent.setClassName(className);

            if (HomeworkBean.isEffictive(stuArr) && null == dataContent.getUserId()) {
                dataContent.setStuArr(stuArr);
                LoginResultBean.Data.StuArrBean stuArrBean = stuArr.get(0);
                if (null != stuArrBean) {
                    dataContent.setUserId(stuArrBean.getStuId());
                    dataContent.setClassId1(stuArrBean.getClassId() + "");
                    dataContent.setGradeId(stuArrBean.getGradeId() + "");
                    dataContent.setSchoolId(stuArrBean.getSchoolId() + "");
                    dataContent.setSchoolName(stuArrBean.getSchoolName() + "");
                    ArrayList<String> className = new ArrayList<>(1);
                    className.add(stuArrBean.getClassName());
                    dataContent.setClassName(className);
                }
            }
        }
    }
//    public static class ChildrenList{
//        private String stuId;
//        private String stuName;
//        private String schoolId;
//        private String classId;
//        private String gradeId;
//
//        public String getStuId() {
//            return stuId;
//        }
//
//        public void setStuId(String stuId) {
//            this.stuId = stuId;
//        }
//
//        public String getStuName() {
//            return stuName;
//        }
//
//        public void setStuName(String stuName) {
//            this.stuName = stuName;
//        }
//
//        public String getSchoolId() {
//            return schoolId;
//        }
//
//        public void setSchoolId(String schoolId) {
//            this.schoolId = schoolId;
//        }
//
//        public String getClassId() {
//            return classId;
//        }
//
//        public void setClassId(String classId) {
//            this.classId = classId;
//        }
//
//        public String getGradeId() {
//            return gradeId;
//        }
//
//        public void setGradeId(String gradeId) {
//            this.gradeId = gradeId;
//        }
//    }
}
