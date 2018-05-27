var date;
var title = document.getElementsByClassName("title")[0];
var circle = document.getElementsByClassName("circle")[0];
var ul = document.getElementsByClassName("ul")[0];
 text = document.getElementsByTagName('p');
var swiperContainer = document.getElementsByClassName('swiper-container')[0];
var swiperWrapper = document.getElementsByClassName('swiper-wrapper')[0];
var fl = true;
var trans,answer,pen,answerimg,text;
var index1=0;
var index2=0;

circle.onclick = function () {
    if (fl) {
        ul.style.display = "block";
        circle.src = "../../images/work/2830391172353919.png"
        fl = false;
    }
    else {
        ul.style.display = "none";
        circle.src = "../../images/work/2830391172353919.png"
        fl = true;
    }
}
// 所有题的容器
var res = [];
var res2 = [];
var room = [];
room[0] = [];
var res3 = [0];
var types = [];
var indexnum, swiper, lengths;
// 渲染标题
function item(data) {
    var res4 = JSON.parse(data);
    res.length = res4.length;
    res2.length = res4.length;
    lengths = res4.length;
    types.length = res.length;
    room.length = res4.length;
    types[0] = res4[0].type;
    for (var i = 0; i < res.length; i++) {
        res[i] = [];
        res2[i] = i;

    }
    firstone(window.android.loadData(0,res4[0].type))
    var ul = document.getElementsByClassName("ul")[0];
    var str1 = "";
    title.innerHTML = res4[0].title;
    for (var pro of res4) {
        str1 += `<li class="titles">${pro.title}</li>`;
    }
    ul.innerHTML = str1;
    $('.titles').click(function () {
        swiper.slideTo(0, 1000, false);//切换到第一个slide，速度为1秒
    })
    var titles = document.getElementsByClassName("titles");
    for (var i = 0; i < titles.length; i++) {
        (function (i) {
            titles[i].onclick = function () {
                indexnum = i;
                index1 = i;
                types[indexnum] = res4[indexnum].type;
                title.innerHTML = this.innerHTML;
                ul.style.display = 'none';
                circle.src = "../../images/work/28046656282239871.png";
                // 传入type 获取后台数据data
                //   window.android.loadData(indexnum,res4[indexnum].type) 
                if (res3.indexOf(indexnum) == -1) {
                    add(indexnum, window.android.loadData(indexnum,res4[indexnum].type))
                    rende2(res[indexnum]);
                    room[i] = [];
                    res3.push(indexnum)
                } else {
                    
                }
            }
        })(i)
    }
}

function add(indexnum, date) {
    var date;
    if(typeof date == "string") {
         date = JSON.parse(date)
    }
    if (res[indexnum] == "") {
        for (var k = 0; k < date.length; k++) {
            for (var j = 0; j < date[k].next.length; j++) {
                res[indexnum].push(date[k].next[j])
            }
        }
    }
    homeWork(res[indexnum])
}
// 作业列表
function homeWork(data) {
    var num1 = data.length;
    console.log(num1)
    // 题目个数超过9个开启轮播效果
    if (num1 <= 9) {
        var a = document.getElementsByClassName("swiper-slide")[0];
    } else {
        swiper = new Swiper('.swiper-container', {
            slidesPerView: num1 * 1.3,
            freeMode: false,
        });
    }

    var str = "";
    for (var i = 0; i < num1; i++) {
        str += `
        <div id="slide" class="swiper-slide">${i + 1}</div>
    `;
    }

    swiperWrapper.innerHTML = str;
    var button = document.getElementsByClassName("swiper-slide");
    var this_;
    for (var i = 0; i < button.length; i++) {
        (function (i) {
            button[i].onclick = function () {
                console.log(i)
                this.style.background = '#c6c6c6';
                this.style.width = "0.66rem";
                this.style.height = "0.66rem";
                this.style.fontSize = "0.4rem";
                this.style.marginTop = "0.1rem";
                this.style.lineHeight = "0.61rem";
                this.style.marginLeft = ".22rem";
                console.log(data[i].optionName)
                rende(data, i);
                index2 = i;
                this_ = this;
                for (var j = 0; j < button.length; j++) {
                    if (this_ != button[j]) {
                        button[j].style.color = null;
                        ques[j].style.width = "0.5rem";
                        ques[j].style.height = "0.5rem";
                        ques[j].style.fontSize = "0.3rem";
                        ques[j].style.marginTop = "0.2rem";
                        ques[j].style.lineHeight = ""
                        ques[j].style.marginLeft = ".3rem";
                    }
                }
            }
        })(i)
    }
}
function rende2(data) {
    if (data[0].optionName == "subjective") {
        typee.innerHTML = `${data[0].content} 
        <ul class="Trans">
        <li class="camera"></li><li class="drawing"></li><li class="words"></li>
        </ul>
        <div id="answer">
            <p>答案<span class="pen"></span></p>
            <div class="picture">
                <img class="answerimg" src="" alt="">
            </div>
        </div>`;
        var imgtype = document.querySelectorAll('[img-type="tex"]');
         answerimg = document.getElementsByClassName("answerimg")[0];
         trans = document.getElementsByClassName('Trans')[0];
         answer = document.getElementById("answer");
         pen = document.getElementsByClassName('pen')[0];
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.height = '0.4rem';
        }
        // 相机
        function camera(data) {
            // 拍照点击事件
            document.getElementsByClassName("camera")[0].onclick = function () {
                console.log("111")
                trans.style.display = "none";
                answer.style.display = 'block';
                window.android.startAnswerPage(1,index1,0);
                // 返回一个src

            }
        }
        // 调取本地图片
        camera(data)
        // 图片点击事件
        function drawing(data) {
            document.getElementsByClassName("drawing")[0].onclick = function () {
                trans.style.display = "none";
                answer.style.display = 'block';
               window.android.startAnswerPage(2,index1,0);
                console.log("111")
                // 返回一个src;
//
            }
        }
        // 写字
        drawing(data)
        // 写字点击事件
        function words(data) {
            document.getElementsByClassName("words")[0].onclick = function () {
                trans.style.display = "none";
                answer.style.display = 'block';
               window.android.startAnswerPage(3,index1,0);
                console.log("111")
                // 返回一个src;

            }
        }
        // 调取写字
        words(data)
//        function subproblem(date, index) {
//            answerimg.src = date
//            data[index].stuAnswer = date;
//        }
         trans = document.getElementsByClassName('Trans')[0];
         answer = document.getElementById("answer");
         pen = document.getElementsByClassName('pen')[0];
        pen.onclick = function () {
            trans.style.display = "block"
        }
    }
    // 是判断题的条件
    if (data[0].optionName == "judge") {
        typee.innerHTML = `<div class="judge">
    <h3>${data[0].content}</ul></h3>
    <span class="img1"></span> 
    <span class="img2"></span>
    </div>`;
        var judge = document.getElementsByClassName('judge')[0];
        var imgs = document.getElementsByTagName('span');
        judge.addEventListener('click', function (e) {
            if (e.target.nodeName == "SPAN") {
                if (e.target.className == "img1") {
                    e.target.className = "img3"
                    data[0].stuAnswer = "对"
                    console.log(data[0].stuAnswer)
                } else if (e.target.className == "img2") {
                    e.target.className = "img4"
                    data[0].stuAnswer = "错"
                    console.log(data[0].stuAnswer)
                }
                for (var i = 0; i < imgs.length; i++) {
                    if (e.target != imgs[i]) {
                        if (e.target.className == 'img3') {
                            imgs[i].className = "img2"
                        } else if (e.target.className == 'img4') {
                            imgs[i].className = "img1"
                        }
                    }
                }
            }
        })
    };
    // 是填空题的调件
    if (data[0].optionName == "fill") {
        typee.innerHTML = `<ul class="list">${data[0].content}<textarea class="tkbox" placeholder="请在此处填写答案..."></textarea></ul>`;
        var answerres = [];
        var imgtype = document.querySelectorAll('[img-type="tex"]');
        var tkbox = document.getElementsByClassName("tkbox")[0];
        tkbox.style.display = "none";
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '0.32rem';
            imgtype[i].style.height = '0.32rem';
        }
        $(".tkbox").bind('input propertychange', function () {
            // console.log($(this).val())
            answerres[0] = $(this).val();
            data[0].stuAnswer = answerres;
            console.log(data[0].stuAnswer)
        });
    };
    if (data[0].optionName == "radio") {
        typee.innerHTML = `<ul class="list">${data[0].content}</ul> <div id="xuan"> 
        <span class="span">A</span>
        <span class="span">B</span>
        <span class="span">C</span>
        <span class="span">D</span>
    </div>`;
        var button = document.getElementsByClassName('span');
        for (var i = 0; i < button.length; i++) {
            if (data[0].stuAnswer == button[i].innerHTML) {
                button[i].style.background = '#07cab9';
                button[i].style.color = "white";
            }
        }
        xuan.addEventListener('click', function (e) {
            if (e.target.nodeName == "SPAN") {
                e.target.style.background = '#07cab9';
                e.target.style.color = "white";
                data[0].stuAnswer = e.target.innerHTML;
                console.log(e.target.innerHTML)
                for (var i = 0; i < button.length; i++) {
                    if (e.target != button[i]) {
                        button[i].style.background = null;
                        button[i].style.color = "#37afa5"
                    }
                }
            }
        })
    }
    // 是多选题的调件
    if (data[0].optionName == "check") {
        var button = document.getElementsByClassName('span');
        for (var i = 0; i < button.length; i++) {
            for (var j = 0; j < data[0].stuAnswer.length; j++) {
                if (data[0].stuAnswer[j] == button[i].innerHTML) {
                    button[i].style.background = '#07cab9';
                    button[i].style.color = "white";
                }
            }
        }
        typee.innerHTML = `<ul class="list">${data[0].content}</ul> <div id="xuan"> 
        <span class="span">A</span>
        <span class="span">B</span>
        <span class="span">C</span>
        <span class="span">D</span>
    </div>`;
        var flag = [true, true, true, true];
        var button = document.getElementsByClassName('span');
        var imgtype = document.querySelectorAll('[img-type="tex"]')
        var strs = "";
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '0.32rem';
            imgtype[i].style.height = '0.32rem';
        }
        var i;
        for ( i = 0; i < button.length; i++) {
            button[i].onclick = function () {
                if (flag[i] == true) {
                    this.style.background = '#00c7b5';
                    this.style.color = "white";
                    flag[i] = false;
                    strs += button[i].innerHTML;
                    for (var j = 0; j < strs.length; j++) {
                        if (data[0].stuAnswer.indexOf(strs[j]) == -1) {
                            data[0].stuAnswer += strs[j];
                        }
                    }
                    console.log(data[0].stuAnswer)

                } else {
                    this.style.background = 'white';
                    this.style.color = "#00c7b5"
                    flag[i] = true;
                    var strs2 = strs.split("");
                    for (var j = 0; j < strs2.length; j++) {
                        if (strs2[j] == this.innerHTML) {
                            strs2.splice(j, 1)
                        }
                    }
                    strs = strs2.join("")
                    data[0].stuAnswer = strs;
                    console.log(data[0].stuAnswer)
                }
            }
        }

    }
}
function rende(data, index) {
    // 是主观题的条件
    if (data[index].optionName == "subjective") {
        typee.innerHTML = `${data[index].content} 
        <ul class="Trans">
        <li class="camera"></li><li class="drawing"></li><li class="words"></li>
        </ul>
        <div id="answer">
            <p>答案<span class="pen"></span></p>
            <div class="picture">
                <img class="answerimg" src="" alt="">
            </div>
        </div>`;
        var imgtype = document.querySelectorAll('[img-type="tex"]');
          answerimg = document.getElementsByClassName("answerimg")[0];
         trans = document.getElementsByClassName('Trans')[0];
         answer = document.getElementById("answer");
         pen = document.getElementsByClassName('pen')[0];
         text = document.getElementsByClassName("picture")[0];
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.height = '0.4rem';
        }
        // 相机
        function camera(data) {
            // 拍照点击事件
            document.getElementsByClassName("camera")[0].onclick = function () {
                console.log("111")
                trans.style.display = "none";
                answer.style.display = 'block';
                console.log(data[index])

                 window.android.startAnswerPage(1,index1,index2);



            }
        }
        // 调取本地图片
        camera(data)
        // 图片点击事件
        function drawing(data) {
            document.getElementsByClassName("drawing")[0].onclick = function () {
                trans.style.display = "none";
                answer.style.display = 'block';
              window.android.startAnswerPage(2,index1,index2);

            }
        }
        // 写字
        drawing(data)
        // 写字点击事件
        function words(data) {
            document.getElementsByClassName("words")[0].onclick = function () {
                trans.style.display = "none";
                answer.style.display = 'block';
                window.android.startAnswerPage(3,index1,index2);

            }
        }
        // 调取写字
        words(data)


        pen.onclick = function () {
            trans.style.display = "block"
        }
    }
    // 是判断题的条件
    if (data[index].optionName == "judge") {
        typee.innerHTML = `<div class="judge">
    <h3>${data[index].content}</ul></h3>
    <span class="img1"></span> 
    <span class="img2"></span>
    </div>`;
        var judge = document.getElementsByClassName('judge')[0];
        var imgs = document.getElementsByTagName('span');
        judge.addEventListener('click', function (e) {
            if (e.target.nodeName == "SPAN") {
                if (e.target.className == "img1") {
                    e.target.className = "img3"
                    data[index].stuAnswer = "对"
                    console.log(data[index].stuAnswer)
                } else if (e.target.className == "img2") {
                    e.target.className = "img4"
                    data[index].stuAnswer = "错"
                    console.log(data[index].stuAnswer)
                }
                for (var i = 0; i < imgs.length; i++) {
                    if (e.target != imgs[i]) {
                        if (e.target.className == 'img3') {
                            imgs[i].className = "img2"
                        } else if (e.target.className == 'img4') {
                            imgs[i].className = "img1"
                        }
                    }
                }
            }
        })
    };
    // 是填空题的调件
    if (data[index].optionName == "fill") {
        typee.innerHTML = `<ul class="list">${data[index].content}<textarea class="tkbox" placeholder="请在此处填写答案..."></textarea></ul>`;
        var answerres = [];
        var imgtype = document.querySelectorAll('[img-type="tex"]');
        var tkbox = document.getElementsByClassName("tkbox")[0];
        tkbox.style.display = "none";
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '0.32rem';
            imgtype[i].style.height = '0.32rem';
        }
        $(".tkbox").bind('input propertychange', function () {
            // console.log($(this).val())
            answerres[0] = $(this).val();
            data[index].stuAnswer = answerres;
            console.log(data[index].stuAnswer)
        });
    };
    if (data[index].optionName == "radio") {
        typee.innerHTML = `<ul class="list">${data[index].content}</ul> <div id="xuan"> 
        <span class="span">A</span>
        <span class="span">B</span>
        <span class="span">C</span>
        <span class="span">D</span>
    </div>`;
        var button = document.getElementsByClassName('span');
        for (var i = 0; i < button.length; i++) {
            if (data[index].stuAnswer == button[i].innerHTML) {
                button[i].style.background = '#07cab9';
                button[i].style.color = "white";
            }
        }
        xuan.addEventListener('click', function (e) {
            if (e.target.nodeName == "SPAN") {
                e.target.style.background = '#07cab9';
                e.target.style.color = "white";
                data[index].stuAnswer = e.target.innerHTML;
                console.log(e.target.innerHTML)
                for (var i = 0; i < button.length; i++) {
                    if (e.target != button[i]) {
                        button[i].style.background = null;
                        button[i].style.color = "#37afa5"
                    }
                }
            }
        })
    }
    // 是多选题的调件
    if (data[index].optionName == "check") {
        var button = document.getElementsByClassName('span');
        for (var i = 0; i < button.length; i++) {
            for (var j = 0; j < data[index].stuAnswer.length; j++) {
                if (data[index].stuAnswer[j] == button[i].innerHTML) {
                    button[i].style.background = '#07cab9';
                    button[i].style.color = "white";
                }
            }
        }
        typee.innerHTML = `<ul class="list">${data[index].content}</ul> <div id="xuan"> 
    <span class="span">A</span>
    <span class="span">B</span>
    <span class="span">C</span>
    <span class="span">D</span>
</div>`;
        var flag = [true, true, true, true];
        var button = document.getElementsByClassName('span');
        var imgtype = document.querySelectorAll('[img-type="tex"]')

        var strs = "";
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '0.32rem';
            imgtype[i].style.height = '0.32rem';
        }
        var i;
        for ( i = 0; i < button.length; i++) {
            button[i].onclick = function () {
                if (flag[i] == true) {
                    this.style.background = '#00c7b5';
                    this.style.color = "white";
                    flag[i] = false;
                    strs += button[i].innerHTML;
                    for (var j = 0; j < strs.length; j++) {
                        if (data[index].stuAnswer.indexOf(strs[j]) == -1) {
                            data[index].stuAnswer += strs[j];
                        }
                    }
                    console.log(data[index].stuAnswer)

                } else {
                    this.style.background = 'white';
                    this.style.color = "#00c7b5"
                    flag[i] = true;
                    var strs2 = strs.split("");
                    for (var j = 0; j < strs2.length; j++) {
                        if (strs2[j] == this.innerHTML) {
                            strs2.splice(j, 1)
                        }
                    }
                    strs = strs2.join("")
                    data[index].stuAnswer = strs;
                    console.log(data[index].stuAnswer)
                }
            }
        }
    }
}
var wrapper = document.getElementsByClassName('swiper-wrapper')[0];
var answerlist = document.getElementsByClassName("answer")[0];
var button = document.getElementsByClassName('span');
var list = document.getElementById('xuan');
var ques = document.getElementsByClassName("swiper-slide");
var typee = document.getElementById('text');
// 第一张卷子
function firstone(data) {
    date = JSON.parse(data);
    rendes();
}

function rendes() {
    for (var k = 0; k < date.length; k++) {
        for (var j = 0; j < date[k].next.length; j++) {
            res[0].push(date[k].next[j])
        }
    }
    // window初始化页面先加载第一套第一题
    //  通过0下标 获取到第一套试卷 
    homeWork(res[0])
    rende2(res[0])
}
// windows初始化
window.onload = function () {
    // 接收时间
    // times(timestr)
    for (var i = 0; i < ques.length; i++) {
        ques[i].style.width = "0.5rem";
        ques[i].style.height = "0.5rem";
    }
    // 开始计时
    // int = setInterval(timer, 1000);
    timefn()
}


// 页面跳转到习题列表
answerlist.onclick = function () {
    // 把endtime传过去
    window.android.setTime(endtime)
    // 跳转页面计时器会暂停
    clearInterval(int)
    // 传递时间
    // 传递当前显示试卷index
    // titmenu
    for (var i = 0; i < lengths; i++) {
        if (room[i] != undefined) {
            for (var j = 0; j < res[i].length; j++) {
                if (room[i].length < res[i].length) {
                    room[i].push({ "examId": res[i][j].examId, "stuAnswer": res[i][j].stuAnswer })
                }
            }
        }
    }
    // 传递答案
    for (var i = 0; i < room.length; i++) {
        if (room[i] != undefined) {
            console.log(JSON.stringify(room[i]));
            window.android.setStuAnswer(types[i],i,JSON.stringify(room[i]))
        }
    }

    window.android.toNextPage()
}


// 计时器
var hour, minute, second;//时 分 秒//初始化
hour = minute = second = 0;
var int;
var endtime;

//计时
function timer() {
    second = second + 1;
    if (second >= 60) {
        second = 0;
        minute = minute + 1;
    }
    if (minute >= 60) {
        minute = 0;
        hour = hour + 1;
    }
    endtime = hour + '时' + minute + '分' + second + '秒';
}
// 计时函数
function timefn() {
    int = setInterval(timer, 1000);
}
function subproblem(data,index1,index2,type) {
trans.style.display = "none";
answer.style.display = 'block';
var num1 = parseInt(index1);
var num2 = parseInt(index2);
res[num1][num2].stuAnswer = date;
if (type == "1" || type == "2") {
answerimg.src = data;
} else {
answerimg.style.display = "none";
text.innerHTML = data
}
}