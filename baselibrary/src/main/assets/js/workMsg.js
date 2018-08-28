var res,stu,oldStu,type=false;
function updateStudentAnswer(){
    console.log(stu+", "+type);
    if( stu!= undefined) {//非首次
        if(type){//上一道题是选择、判断
            if(oldStu!=stu){
                window.android.updateStuAnswer(stu);
            }
        }
    }
}
function b64DecodeUnicode(str) {
    return decodeURIComponent(atob(str).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
}
function rende(data) {
    updateStudentAnswer();
    //console.log(data);
    res = JSON.parse(b64DecodeUnicode(data));
    console.log(res.ansType);
    stu = data.stuAnswer;
    oldStu = stu;
    var problemType=res.optionName;
    console.log(problemType);
    if(problemType == "radio" || problemType=="check" || problemType == "judge"|| problemType == "fill") {
        type=true;
   }
    // 是主观题的条件
    var text = document.getElementById("text");
    if (res.optionName == "subjective") {
        text.innerHTML = `${res.content} 
        <ul class="Trans">
        <li class="camera"></li><li class="drawing"></li><li class="words"></li>
        </ul>
        <div id="answer">
            <p>答案<span class="pen"></span></p>
            <div class="picture">
                <p class="texts"></p>
                <img class="answerimg" src="" alt="">
            </div>
        </div>`;
        var imgtype = document.querySelectorAll('[img-type="tex"]');
        answerimg = document.getElementsByClassName("answerimg")[0];
        texts = document.getElementsByClassName("texts")[0];
        trans = document.getElementsByClassName('Trans')[0];
        answer = document.getElementById("answer");
        pen = document.getElementsByClassName('pen')[0];
       if(res.stuAnswer != "") {
            answer.style.display = 'block';
            trans.style.display = 'none';
           subproblem(res.stuAnswer)
       }
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.height = '0.4rem';
        }
        // 相机
        function camera(data) {
            // 拍照点击事件
            document.getElementsByClassName("camera")[0].onclick = function () {
                console.log("111")

              
                console.log(res)
                window.android.startAnswerPage(1);
      
            }
        }
        // 调取本地图片
        camera(data)
        // 图片点击事件
        function drawing(data) {
            document.getElementsByClassName("drawing")[0].onclick = function () {

              
                window.android.startAnswerPage(2);
            }
        }
        // 写字
        drawing(data)
        // 写字点击事件
        function words(data) {
            document.getElementsByClassName("words")[0].onclick = function () {

               
                window.android.startAnswerPage(3);
            }
        }
        // 调取写字
        words(data)

        pen.onclick = function () {
            trans.style.display = "block"
        }
    }
    // 是判断题的条件
    if (res.optionName == "judge") {
        text.innerHTML = `<div class="judge">
    <h3>${res.content}</ul></h3>
    <span class="img1"></span> 
    <span class="img2"></span>
    </div>`;
        var judge = document.getElementsByClassName('judge')[0];
        var imgs = document.getElementsByTagName('span');
        if(res.stuAnswer != "") {
            if(res.stuAnswer == "对") {
                imgs[0].className = "img3";
            } 
            if(res.stuAnswer == "错") {
                imgs[1].className = "img4"
             } 
        }
        judge.addEventListener('click', function (e) {
            if (e.target.nodeName == "SPAN") {
                if (e.target.className == "img1") {
                    e.target.className = "img3"
                    res.stuAnswer = "对"
                     stu = res.stuAnswer;
                    console.log(res.stuAnswer)
                } else if (e.target.className == "img2") {
                    e.target.className = "img4"
                    res.stuAnswer = "错"
                    stu = res.stuAnswer;
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
    if (res.optionName == "fill") {
//        text.innerHTML = `<ul class="list">${res.content}<textarea class="tkbox" placeholder="请在此处填写答案..."></textarea></ul>`;
//        if(res.stuAnswer != ""){
//            $(".tkbox").val(res.stuAnswer[0])
//        }
//        var imgtype = document.querySelectorAll('[img-type="tex"]');
//        var tkbox = document.getElementsByClassName("tkbox")[0];
//        tkbox.style.display = "none";
//        for (var i = 0; i < imgtype.length; i++) {
//            imgtype[i].style.width = '0.32rem';
//            imgtype[i].style.height = '0.32rem';
//        }
//        $(".tkbox").bind('input propertychange', function () {
//            res.stuAnswer = $(this).val();
//            stu = res.stuAnswer;
//            //console.log(res.stuAnswer);
//        });
          text.innerHTML = `${res.content}
                <ul class="Trans">
                <li class="camera"></li><li class="drawing"></li><li class="words"></li>
                </ul>
                <div id="answer">
                    <p>答案<span class="pen"></span></p>
                    <div class="picture">
                        <p class="texts"></p>
                        <img class="answerimg" src="" alt="">
                    </div>
                </div>`;
                 var tkbox = document.getElementsByClassName("tkbox")[0];
                        tkbox.style.display = "none"
                var imgtype = document.querySelectorAll('[img-type="tex"]');
                answerimg = document.getElementsByClassName("answerimg")[0];
                texts = document.getElementsByClassName("texts")[0];
                trans = document.getElementsByClassName('Trans')[0];
                answer = document.getElementById("answer");
                pen = document.getElementsByClassName('pen')[0];
               if(res.stuAnswer != "") {
                    answer.style.display = 'block';
                    trans.style.display = 'none';
                   subproblem(res.stuAnswer)
               }
                for (var i = 0; i < imgtype.length; i++) {
                    imgtype[i].style.height = '0.4rem';
                }
                // 相机
                function camera(data) {
                    // 拍照点击事件
                    document.getElementsByClassName("camera")[0].onclick = function () {
                        console.log("111")


                        console.log(res)
                        window.android.startAnswerPage(1);

                    }
                }
                // 调取本地图片
                camera(data)
                // 图片点击事件
                function drawing(data) {
                    document.getElementsByClassName("drawing")[0].onclick = function () {


                        window.android.startAnswerPage(2);
                    }
                }
                // 写字
                drawing(data)
                // 写字点击事件
                function words(data) {
                    document.getElementsByClassName("words")[0].onclick = function () {


                        window.android.startAnswerPage(3);
                    }
                }
                // 调取写字
                words(data)

                pen.onclick = function () {
                    trans.style.display = "block"
                }
    };
    if (res.optionName == "radio") {
        text.innerHTML = `<ul class="list">${res.content}</ul> <div id="xuan"> 
        <span class="span">A</span>
        <span class="span">B</span>
        <span class="span">C</span>
        <span class="span">D</span>
    </div>`;
        var button = document.getElementsByClassName('span');
        for (var i = 0; i < button.length; i++) {
            if (res.stuAnswer == button[i].innerHTML) {
                button[i].style.background = '#07cab9';
                button[i].style.color = "white";
            }
        }
        xuan.addEventListener('click', function (e) {
            if (e.target.nodeName == "SPAN") {
                e.target.style.background = '#07cab9';
                e.target.style.color = "white";
                res.stuAnswer = e.target.innerHTML;
                stu = res.stuAnswer;
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
        if (res.optionName == "check") {
            text.innerHTML = `<ul class="list">${res.content}</ul> <div id="xuan">
        <span class="span">A</span>
        <span class="span">B</span>
        <span class="span">C</span>
        <span class="span">D</span>
    </div>`;
        var flag = [true, true, true, true];
        var button = document.getElementsByClassName('span');
        var strs;
        for (var i = 0; i < button.length; i++) {
            if (res.stuAnswer != "") {
                strs = res.stuAnswer;
                for (var j = 0; j < res.stuAnswer.length; j++) {
                    if (res.stuAnswer[j] == button[i].innerHTML) {
                        console.log(flag[i])
                        flag[i] = false;
                        button[i].style.background = '#07cab9';
                        button[i].style.color = "white";
                    }
                }
            } else {
                var strs = "";
            }
        }
        var imgtype = document.querySelectorAll('[img-type="tex"]')
        for (var l = 0; l < button.length; l++) {
            for (var p = 0; p < res.stuAnswer.length; p++) {
                if (button[l].innerHTML == res.stuAnswer[p].innerHTML) {
                    button[l].style.background = '#00c7b5';
                    button[l].style.color = "white";
                }
            }
        }

        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '0.32rem';
            imgtype[i].style.height = '0.32rem';
        }
        for (let i = 0; i < button.length; i++) {
            button[i].onclick = function () {
                if (flag[i] == true) {
                    this.style.background = '#00c7b5';
                    this.style.color = "white";
                    flag[i] = false;
                    strs += button[i].innerHTML;
                    for (var j = 0; j < strs.length; j++) {
                        if (res.stuAnswer.indexOf(strs[j]) == -1) {
                            res.stuAnswer += strs[j];
                        
                        }
                    }
                    // console.log(res.stuAnswer)
                    stu = res.stuAnswer;
                    var stu2 = stu.split("");
                    stu2.sort(function(a, b) { //排序
                        return a.localeCompare(b);
                    });
                   stu = stu2.join("")
                    console.log(stu)
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
                    res.stuAnswer = strs;
                    // console.log(res.stuAnswer)
                    stu = res.stuAnswer;
                    var stu2 = stu.split("");
                    stu2.sort(function(a, b) { //排序
                        return a.localeCompare(b);
                    });
                   stu = stu2.join("")
                    console.log(stu)
                }
            }
        }
    }
}
  function fn(str) {
     if(str.indexOf("<img") != -1) {
            var index1 = str.indexOf("src=\"");
            console.log(index1)
             var index2 = str.indexOf("/>");
             console.log(index2)
             var num = index1+5;
            var b = str.slice(num,index2-1)
            return b;
            }
            return str;
  }
// 图片、拍摄、文本
function subproblem(data) {
   console.log(data)
   data = fn(data)
   //<img src="http://192.168.0.4:8090/group/M00/00/1F/wKgABFtr9dqATP6hAFI_1hdsPS8355.jpg"/>
    res.stuAnswer = data;//http://192.168.0.4:8090/
    var regex = /^http\.bmp|\.jpg|\.jpeg|\.png|\.tiff|\.gif|\.pcx|\.tga|\.exif|\.fpx|\.svg|\.psd|\.cdr|\.pcd|\.dxf|\.ufo|\.eps|\.ai|\.raw|\.WMF|\.webp$/;
    if (regex.test(data)) {
        trans.style.display = "none";
        answerimg.style.display = "block";
        answerimg.src = data;
        answer.style.display = 'block';
        texts.style.display = "none";
    } else {
       texts.style.display = "block";
        trans.style.display = "none";
        answer.style.display = 'block';
        answerimg.style.display = "none";
        texts.innerHTML = data;
    }
}
    // 点击图片放大
        $("#text").find("img").click(function(){
            var src = $(this).attr("src");
            window.android.imageClick(src);
        })
