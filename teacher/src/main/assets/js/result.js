
//console.log(data.onLine[3])
// 题目函数
var res;
function exercise(data) {
     res = JSON.parse(data);
    console.log(data)

    // 单、多选题
    if (res.optionName == "radio" || res.optionName == "check") {
        if (res.classRate == -1) {
            res.classRate = "--"
        }
        $(".boxx").html(`<ul class="list">${res.content}</ul>
     <div id="xuan"> 
    <span class="span">A</span>
    <span class="span">B</span>
    <span class="span">C</span>
    <span class="span">D</span>
     </div>`);
     $("ol").children().addClass("li");
     var lis = document.getElementsByClassName('li');
    var button = document.getElementsByClassName('span');
        for (var i = 0; i < button.length; i++) {
            for (var j = 0; j < res.stuAnswer.length; j++) {
                if (res.stuAnswer == button[i].innerHTML) {
                    button[i].style.background = "#07cab9";
                    button[i].style.color = "white";
                    lis[i].style.color = "#07cab9";
                }
            }
        }
        var imgtype = document.querySelectorAll('[img-type="tex"]')
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '16px';
            imgtype[i].style.height = '16px';
        }
    }
    // 主观题、填空题
    if (res.optionName == "subjective" || res.optionName == "fill") {
        if (res.classRate == -1) {
            res.classRate = "--"
        } 
        $(".boxx").html(`<ul class="list">${res.content}</ul>
        <p class="p">学生答案：</p>
        <div class="room"><div>`
    );
        var reg =  /^png|jpg|svg|gif|bmp|swf|tiff|psd$/;
        if(reg.test(res.stuAnswer)) {
            $(".room").html(`<img class="answerimg" src=${res.stuAnswer}>`);
        } else {
            $(".room").html(`<p class="answertext">${res.stuAnswer}</p>`);
        }
        $(".tkbox").css({
            "display":"none",
            
        })
        var imgtype = document.querySelectorAll('[img-type="tex"]')
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '16px';
            imgtype[i].style.height = '16px';
        }
    }
    // 判断题
    if (res.optionName == "judge") {
        if (res.classRate == -1) {
            res.classRate = "--"
        }
        $(".boxx").html(`<ul class="list">${res.content}</ul>
            <div id="judge"> 
            <span class="img1"></span> 
            <span class="img2"></span>
            </div>`);
        var imgs = document.getElementsByTagName('span');
        if (res.stuAnswer == "对") {
            imgs[0].className = "img2";
            imgs[1].className = "img3";
        } else {
            imgs[0].className = "img1";
            imgs[1].className = "img4";
        }
    }
    var imgtype = document.querySelectorAll('[img-type="tex"]')
    for (var i = 0; i < imgtype.length; i++) {
        imgtype[i].style.width = '16px';
        imgtype[i].style.height = '16px';
    }
}
// 题目渲染
exercise(data)