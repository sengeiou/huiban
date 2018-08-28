// 题目函数
var res;
function exercise(data) {
    res = JSON.parse(data);
        if(res.content == undefined) {
        res.content = ""
        }
        if(res.classRate == undefined) {
        res.classRate = ""
        }
        if(res.stuList == undefined) {
        res.stuList = ""
        }
        if(res.analysis == undefined) {
        res.analysis = ""
        }
    // 单、多选题
    if (res.optionName == "radio" || res.optionName == "check") {
        var rates;
        if (res.classRate == -1) {
            rates = "--"
        } else {
             rates = res.classRate+"%";
        }
        $(".boxx").html(`<ul class="list">${res.content}</ul>
     <div id="xuan"> 
    <span class="span">A</span>
    <span class="span">B</span>
    <span class="span">C</span>
    <span class="span">D</span>
     </div>
    <div id="analyze">
    <ul>
    <li>正确率：<span>${rates}</span></li>
    <li class="fenb"></li>
    <li class="wrongs">答错的人：${res.stuList}</li>
    <li>${res.analysis} </li>
    </ul>
    </div>`);
        var str = `
    <span class="mar">A.${res.opArr[0].cnt}人</span>
    <span class="mar">B.${res.opArr[1].cnt}人</span>
    <span class="mar">C.${res.opArr[2].cnt}人</span>
    <span class="mar">D.${res.opArr[3].cnt}人</span>
    `;
        $(".fenb").html(`选项分布：${str}`)
        $("ol").children().addClass("li");
        var button = document.getElementsByClassName('span');
        var mar = document.getElementsByClassName('mar');
        var lis = document.getElementsByClassName('li');
        for (var i = 0; i < button.length; i++) {
            for (var j = 0; j < res.answer.length; j++) {
                if (res.answer[j] == button[i].innerHTML) {
                    button[i].style.background = "#07cab9";
                    button[i].style.color = "white";
                    lis[i].style.color = "#07cab9";
                    mar[i].style.color = "#07cab9";
                }
            }
        }
    }
    // 主观题、填空题
    if (res.optionName == "subjective" || res.optionName == "fill") {
         var rates;
                if (res.classRate == -1) {
                    rates = "--"
                } else {
                    rates = res.classRate+"%";
                }
        $(".boxx").html(`<ul class="list">${res.content}</ul>
        <div id="analyze">
        <ul>
        <li>正确率：<span>${rates}</span></li>
        <li class="wrongs">答错的人：${res.stuList}</li>
        <li>${res.analysis} </li>
        </ul>
        </div>`);
        $("ol").children().addClass("li");
    }
    // 判断题
    if (res.optionName == "judge") {
         var rates;
                if (res.classRate == -1) {
                    rates = "--"
                } else {
                     rates = res.classRate+"%";
                }
        $(".boxx").html(`<ul class="list">${res.content}</ul>
            <div id="judge"> 
            <span class="img1"></span> 
            <span class="img2"></span>
            </div>
           <div id="analyze">
           <ul>
           <li>正确率：<span>${rates}</span></li>
           <li class="fenb"></li>
           <li class="wrongs">答错的人：${res.stuList}</li>
           <li>${res.analysis} </li>
           </ul>
           </div>`);
        var imgs = document.getElementsByTagName('span');
        if (res.answer == "对") {
            imgs[0].className = "img2";
            imgs[1].className = "img3";
        } else {
            imgs[0].className = "img1";
            imgs[1].className = "img4";
        }
    }
    $("input").attr("readonly","true")
}

// 题目渲染