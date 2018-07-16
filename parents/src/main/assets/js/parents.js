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
//$(".tea3").bind("click", function () {
//     window.android.toNextActivity(9)
//})
//个人头像点击
    $(".userImg").click(function(){
        window.android.toggleSlide();
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
for (pro of res1) {
if (pro.subjectName == "") {
pro.subjectName = "无课";
}
str1 += `<span>${pro.subjectName}</span>`;
}
if (res1[0].subjectName == "无课" && res1[1].subjectName == "无课" && res1[2].subjectName == "无课" && res1[3].subjectName == "无课") {
str1 = `<span>上午没有课程 </span>`;
}
for (pro of res2) {
if (pro.subjectName == "") {
pro.subjectName = "无课";
}
str2 += `<span>${pro.subjectName}</span>`;
$(".teachtime").html("下午课程")
}
if (res2[0].subjectName == "无课" && res2[1].subjectName == "无课" && res2[2].subjectName == "无课" && res2[3].subjectName == "无课") {
str2 = `<span>下午没有课程 </span>`;
}
for (pro of res3) {
if (pro.subjectName == "") {
pro.subjectName = "无课";
}
str3 += `<span>${pro.subjectName}</span>`;
$(".teachtime").html("晚上课程")
}
if (res3[0].subjectName == "无课" && res3[1].subjectName == "无课") {
console.log("1111")
str3 = `<span>今晚没有课程 </span>`;
}
if (hour >= 1 && hour <= 12) {
$(".teachtime").html("上午课程")
curriculum.html(str1)
} else if (hour > 12 && hour <= 18) {
$(".teachtime").html("下午课程")
curriculum.html(str2)
} else {
$(".teachtime").html("晚上课程")
curriculum.html(str3)
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

//var list = document.querySelector('.listoo');
//var myIscroll = new IScroll('#main', {
//    scrollbars: true,
//    probeType: 3
//});
//var flag = true;
//myIscroll.on('scroll', function () {
//    if (this.y > 40) {
//        list.setAttribute('uptext', '释放刷新');
//    } else if (this.y < 40 && this.y > 0) {
//        list.setAttribute('uptext', '下拉刷新');
//    } else if (this.y < this.maxScrollY - 40) {
//        flag = true;
//    }
//})
//myIscroll.on('scrollEnd', function () {
//    list.setAttribute('text', '下拉刷新');
//    if (this.y === this.maxScrollY) {
//        console.log("上拉");
//    } else if (this.y === 0) {
//        console.log("下拉");
//    }
//})