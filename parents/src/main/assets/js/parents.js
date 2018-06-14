var leftMenu = $('.leftMenu'),
    nowTime = $('.nowDate'),
    nowWeek = $('.week'),
    weather = $('.weatherImg'),
    curriculum = $('.tab>.content');
// 当日整时
var hour;
//获取时间
function getNowTime() {
    function p(s) {
        return s < 10 ? '0' + s : s;
    }
    //首页年月日
    var myDate = new Date();
    //获取当前年
    var year = myDate.getFullYear();
    //获取当前月
    var month = myDate.getMonth() + 1;
    //获取当前日
    var date = myDate.getDate();
    //获取当前星期
    var nowDate = p(month) + "/" + p(date) + "/" + year;
    var day = new Date(Date.parse(nowDate));
    var today = new Array('星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六');
    var week = today[day.getDay()];
    //当前年月日
    var now = year + '年' + p(month) + "月" + p(date) + "日";
    nowTime.html(now);
    nowWeek.html(week);
    // 获取当日整时
    hour = myDate.getHours();
    console.log(hour)
}
getNowTime();
// 班级页
var classes = document.getElementsByClassName('class')[0];
//  课表页 
var timetable = document.getElementsByClassName('timeTable')[0];
// 通知页 
var notice = document.getElementsByClassName('notice')[0];
// 关注页 
var follow = document.getElementsByClassName('follow')[0];
// 慧辅导页 
var homepage = document.getElementsByClassName('looktotal')[0];
// 留言页
var messa = document.getElementsByClassName('messa')[0];
// 个人头像
var userimg = document.getElementsByClassName("userImg")[0];
function picture(src) {
    userimg.src = src;
}
userimg.onclick = function () {
    window.android.toggleSlide()
}
// picture(src)
// 班级页
classes.onclick = function () {
    window.android.toNextActivity(0)
}
//  课表页 
timetable.onclick = function () {
    window.android.toNextActivity(1)
}
// 通知页 

notice.onclick = function () {
    window.android.toNextActivity(2)
}
// 慧辅导页 
homepage.onclick = function () {
    window.android.toNextActivity(4)
}
//相关课程
$(".tea1").bind("click", function () {
     window.android.toNextActivity(7)
})
// 今日作业
$(".tea2").bind("click", function () {
    window.android.toNextActivity(8)
})
// 家长确认
$(".tea3").bind("click", function () {
     window.android.toNextActivity(9)
})
//切换课表
function switchs(list) {
    var data = JSON.parse(list);
    var tab = document.getElementsByClassName("tab")[0];
    var str1 = "";
    var str2 = "";
    var str3 = "";
    var res1 = [];
    var res2 = [];
    var res3 = [];
    for (var pro of data) {
        if (pro.num <= 4) {
            res1.push(pro)
        } else if (pro.num <= 8) {
            res2.push(pro)
        } else {
            res3.push(pro)
        }
    }
//    if (hour > 0 && hour < 12) {
//        for (pro of res1) {
//            if (pro.subjectName == "") {
//                pro.subjectName = "无课";
//            }
//            str1 += `<span>${pro.subjectName} ${pro.num}</span>`;
//            $(".teachtime").html("上午课程")
//        }
//        curriculum[0].innerHTML = str1;
//    } else if (hour > 12 && hour < 18) {
//        for (pro of res2) {
//            if (pro.subjectName == "") {
//                pro.subjectName = "无课";
//            }
//            str2 += `<span>${pro.subjectName} ${pro.num}</span>`;
//            $(".teachtime").html("下午课程")
//        }
//        curriculum[0].innerHTML = str2;
//    } else {
//        for (pro of res3) {
//            if (pro.subjectName == "") {
//                pro.subjectName = "无课";
//            }
//            str3 += `<span>${pro.subjectName} ${pro.num}</span>`;
//            $(".teachtime").html("晚上课程")
//        }
//        curriculum[0].innerHTML = str3;
//    }
        if (hour > 0 && hour < 12) {
        for (pro of res1) {
        if (pro.subjectName == "") {
        pro.subjectName = "无课";
        }
        str1 += `<span>${pro.subjectName} ${pro.num}</span>`;
        $(".teachtime").html("上午课程")
        }
        if(res1[0].subjectName=="无课" && res1[1].subjectName== "无课" && res1[2].subjectName== "无课" && res1[3].subjectName== "无课") {
        str1 = `<span>上午没有课程 </span>`;
        }
        curriculum[0].innerHTML = str1;
        } else if (hour > 12 && hour < 18) {
        for (pro of res2) {
        if (pro.subjectName == "") {
        pro.subjectName = "无课";
        }
        str2 += `<span>${pro.subjectName} ${pro.num}</span>`;
        $(".teachtime").html("下午课程")
        }
        if(res2[0].subjectName=="无课" && res2[1].subjectName== "无课" && res2[2].subjectName== "无课" && res2[3].subjectName== "无课") {
        str2 = `<span>下午没有课程 </span>`;
        }
        curriculum[0].innerHTML = str2;
        } else {
        for (pro of res3) {
        if (pro.subjectName == "") {
        pro.subjectName = "无课";
        }
        str3 += `<span>${pro.subjectName} ${pro.num}</span>`;
        $(".teachtime").html("晚上课程")
        }
        if(res3[0].subjectName=="无课" && res3[1].subjectName== "无课") {
        console.log("1111")
        str3 = `<span>今晚没有课程 </span>`;
        }
        curriculum[0].innerHTML = str3;
        }
}

// 慧辅导导航
function imag(list) {
    var data = JSON.parse(list)
    var str = "";
    var list = document.getElementsByClassName("list")[0];
    str += `
      <div class="course">
      <img src=${data[0].imgUrl} class="userImg" onerror="this.src='./images/reset/classPic.png'">
      </div>
      <div class="course">
      <img src=${data[1].imgUrl} class="userImg" onerror="this.src='./images/reset/classPic.png'">
      </div>
    `;
    list.innerHTML = str;
    var course = document.getElementsByClassName('course');
    for(var i=0; i<course.length; i++) {
    (function(i){
    course[i].onclick = function() {
    window.android.toNextHuiFuActivity(data[i].CourseId)
                     }
             })(i)
         }
    }
}
var list = '[{"id":3004049,"classId":3000153,"semesterId":3000120,"week":2,"num":1,"subjectId":2,"teacherId":2030213,"beginTime":"08:20:00","endTime":"09:00:00","subjectName":"\u6570\u5b66","teacherName":"\u6768\u6cfd\u6668"},{"id":3004056,"classId":3000153,"semesterId":3000120,"week":2,"num":2,"subjectId":4,"teacherId":2030219,"beginTime":"09:10:00","endTime":"09:50:00","subjectName":"\u7269\u7406","teacherName":"\u694a\u694a"},{"id":3004063,"classId":3000153,"semesterId":3000120,"week":2,"num":3,"subjectId":1,"teacherId":2029920,"beginTime":"10:20:00","endTime":"10:55:00","subjectName":"\u8bed\u6587","teacherName":"\u8d75\u5efa\u5ddd"},{"id":3004077,"classId":3000153,"semesterId":3000120,"week":2,"num":5,"subjectId":3,"teacherId":2030216,"beginTime":"14:30:00","endTime":"15:10:00","subjectName":"\u82f1\u8bed","teacherName":"\u6c88\u9633Y"},{"id":3004084,"classId":3000153,"semesterId":3000120,"week":2,"num":6,"subjectId":0,"teacherId":0,"beginTime":"15:20:00","endTime":"16:00:00","subjectName":"","teacherName":""},{"id":3004091,"classId":3000153,"semesterId":3000120,"week":2,"num":7,"subjectId":4,"teacherId":2030219,"beginTime":"16:25:00","endTime":"17:05:00","subjectName":"\u7269\u7406","teacherName":"\u694a\u694a"},{"id":3004098,"classId":3000153,"semesterId":3000120,"week":2,"num":8,"subjectId":0,"teacherId":0,"beginTime":"17:15:00","endTime":"17:45:00","subjectName":"","teacherName":""},{"id":3004105,"classId":3000153,"semesterId":3000120,"week":2,"num":9,"subjectId":0,"teacherId":0,"beginTime":"18:50:00","endTime":"19:30:00","subjectName":"","teacherName":""},{"id":3004112,"classId":3000153,"semesterId":3000120,"week":2,"num":10,"subjectId":0,"teacherId":0,"beginTime":"19:40:00","endTime":"20:20:00","subjectName":"","teacherName":""},{"id":0,"classId":0,"week":"2","num":4,"subjectName":"","teacherName":""}]'
var imgs = '[{"AddTime":1526356573,"ChapBranId":10759,"CheckStatus":3,"FasciculeId":19,"IsPublic":1,"Name":"CaCwReHX45yW1522384500.mp4","RealName":"\u694a\u694a","SchoolId":"45","SeriBranId":130884,"SiteName":"\u9752\u5c9b\u5b9e\u9a8c\u521d\u7ea7\u4e2d\u5b66","SpeakerId":2030219,"StageId":"2","SubjectId":"4","UploaderId":2030219,"VersionId":12,"VideoId":"4565","VideoType":2,"WatchCnt":0,"id":"4565","_version_":1600500870338838528,"name":"CaCwReHX45yW1522384500.mp4","flv_url":null,"speakerId":2030219,"videoId":"4565","checkStatus":3,"isTrans":0,"totalLength":0,"addTime":"2018-05-15","schoolName":"\u9752\u5c9b\u5b9e\u9a8c\u521d\u7ea7\u4e2d\u5b66","stageName":"\u521d\u4e2d","subjectName":"\u7269\u7406","knowName":"\u6838\u80fd","speakerName":"\u694a\u694a","uploadName":"\u694a\u694a","totalTime":0,"viewCount":"0","dzCount":"0","studyCount":"0","imgUrl":"http:\/\/192.168.0.3:8091\/group2\/M00\/00\/29\/wKgAA1r6Wk2AZSgGAAXmdArc_4M614.png","ShareSites":[],"statusName":"\u4e0a\u67b6","frontStatus":"\u8f6c\u7801\u4e2d\u2026"},{"AddTime":1518076971,"ChapBranId":30122,"CheckStatus":3,"FasciculeId":19,"IsPublic":1,"Name":"\u529b.mp4","RealName":"\u694a\u694a","SchoolId":"45","SeriBranId":3559,"SiteName":"\u9752\u5c9b\u5b9e\u9a8c\u521d\u7ea7\u4e2d\u5b66","SpeakerId":2030219,"StageId":"2","SubjectId":"4","UploaderId":2030219,"VersionId":12,"VideoId":"2107","VideoType":1,"WatchCnt":3,"id":"2107","_version_":1600497397399552000,"name":"\u529b.mp4","flv_url":"http:\/\/playvideo.oss-cn-shanghai.aliyuncs.com\/2018\/2107\/A93D62ED9C0046A285FEFFAC04BE2F1C.m3u8","speakerId":2030219,"videoId":"2107","checkStatus":3,"isTrans":1,"totalLength":222,"addTime":"2018-02-08","schoolName":"\u9752\u5c9b\u5b9e\u9a8c\u521d\u7ea7\u4e2d\u5b66","stageName":"\u521d\u4e2d","subjectName":"\u7269\u7406","knowName":"\u529b\u5b66","speakerName":"\u694a\u694a","uploadName":"\u694a\u694a","totalTime":222,"viewCount":"3","dzCount":"0","studyCount":"2","imgUrl":"http:\/\/192.168.0.3:8091\/group2\/M00\/03\/C3\/ag4bNlp8BCmATe31AADU-qMcUQg720.png","ShareSites":[],"statusName":"\u4e0a\u67b6","frontStatus":""}]'

imag(imgs);
switchs(list)
var list = document.querySelector('.listoo');
var myIscroll = new IScroll('#main', {
    scrollbars: true,
    probeType: 3
});
var flag = true;
myIscroll.on('scroll', function () {
    if (this.y > 40) {
        list.setAttribute('uptext', '释放刷新');
    } else if (this.y < 40 && this.y > 0) {
        list.setAttribute('uptext', '下拉刷新');
    } else if (this.y < this.maxScrollY - 40) {
        flag = true;
    }
})
myIscroll.on('scrollEnd', function () {
    list.setAttribute('text', '下拉刷新');
    if (this.y === this.maxScrollY) {
        console.log("上拉");
    } else if (this.y === 0) {
        console.log("下拉");
    }
})