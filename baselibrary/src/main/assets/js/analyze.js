var res;
var text = document.getElementById("text");
var usertype = window.android.getUserType();
var isParentsLook = window.android.isParentsLook();
var type;
var jie="解析：";
if(usertype == 1) {
    type = "你的答案：";
} else  {
    type = "孩子答案：";
}
function rende(data) {
    res =  JSON.parse(data);
    // 主观题
       if(res.content == undefined) {
        res.content = ""
        }
        if(res.stuAnswer == undefined) {
        res.content = ""
        }
        if(res.answer == undefined) {
        res.answer = ""
        }
        if(res.analysis == undefined) {
        res.analysis = ""
        }
    if (res.optionName == "subjective") {
        if(res.analysis != undefined) {
            if(res.analysis.indexOf("解析") != -1) {
            jie="";
            }
        }
         text.innerHTML = `<ul class="list">${res.content}</ul> 
                    <div id="analyze">
                    <ul>
                    <li>${type}${res.stuAnswer}</li>
                    <li><span>${res.answer}</span></li>
                    <li>${jie} ${res.analysis} </li>
                    </ul>
                    </div>`;
    }
    if (res.optionName == "judge") {
        if(res.analysis != undefined) {
                    if(res.analysis.indexOf("解析") != -1) {
                    jie="";
                    }
                }
        text.innerHTML = `<div class="judge">
                        <h3>${res.content}</ul></h3>
                        <span class="img1" style="margin-top:25px"></span> 
                        <span class="img2" style="margin-top:25px"></span>
                        </div>
                        <div id="analyze">
                        <ul>
                          <li>正确答案：<span>${res.answer}</span></li>
                            <li>${type}${res.stuAnswer}</li>
                            <li>${jie} ${res.analysis} </li>
                        </ul>
                        </div>`;
        var imgs = document.getElementsByTagName('span');
        if (res.answer == "对") {
            img[0].className = "img1"
            img[1].className = "img4"
        } else {
            img[0].className = "img2"
            img[1].className = "img3"
        }
    };
    // 填空题
    if (res.optionName == "fill") {
        if(res.analysis != undefined) {
                    if(res.analysis.indexOf("解析") != -1) {
                    jie="";
                    }
                }
        text.innerHTML = `<ul class="list">${res.content}</ul> 
                    <div id="analyze">
                    <ul>
                        <li>正确答案：<span>${res.answer}</span></li>
                        <li>${type}${res.stuAnswer}</li>
//                        <li><span>${res.answer}</span></li>
                        <li>${jie} ${res.analysis} </li>
                    </ul>
                    </div>`;
        var tkbox = document.getElementsByClassName('tkbox')[0];
        var button = document.getElementsByClassName('span');
        var answ = document.getElementsByClassName("lxitem_ans_frm")[0]
        tkbox.style.display = "none"
        for (var pro of button) {
            if (res.answer == pro.innerHTML) {
                pro.style.background = "#07cab9";
                pro.style.color = "white";
            }
        }
        var imgtype = document.querySelectorAll('[img-type="tex"]')
        for (var i = 0; i < imgtype.length; i++) {
            imgtype[i].style.width = '16px';
            imgtype[i].style.height = '16px';
        }
    };
    // 单选题
    if (res.optionName == "radio") {
        if(res.analysis != undefined) {
                    if(res.analysis.indexOf("解析") != -1) {
                    jie="";
                    }
                }
        text.innerHTML = `<ul class="list">${res.content}</ul> <div id="xuan"> 
                <span class="span">A</span>
                <span class="span">B</span>
                <span class="span">C</span>
                <span class="span">D</span>
                 </div>
                <div id="analyze">
                <ul>
              <li>正确答案：<span>${res.answer}</span></li>
               <li >${type}${res.stuAnswer}</li>
                <li>${jie} ${res.analysis} </li>
                </ul>
                </div>`;
        console.log(type);
        var button = document.getElementsByClassName('span');
        for (var pro of button) {
            if (res.stuAnswer == pro.innerHTML) {
                pro.style.background = "#07cab9";
                pro.style.color = "white";
            }
        }
    }
    // 多选题
    if (res.optionName == "check") {
        if(res.analysis != undefined) {
                    if(res.analysis.indexOf("解析") != -1) {
                    jie="";
                    }
                }
        text.innerHTML = `<ul class="list">${res.content}</ul> <div id="xuan"> 
                    <span class="span">A</span>
                    <span class="span">B</span>
                    <span class="span">C</span>
                    <span class="span">D</span>
                     </div>
                    <div id="analyze">
                    <ul>
                    <li>正确答案：<span>${res.answer}</span></li>
                    <li >${type}${res.stuAnswer}</li>
                    <li>${jie}${res.analysis} </li>
                    </ul>
                    </div>`;
        var button = document.getElementsByClassName('span');
        for (var pro of button) {
            for (var j = 0; i < res.stuAnswer.length; j++) {
                if (res.stuAnswer[j] == pro.innerHTML) {
                    pro.style.background = "#07cab9";
                    pro.style.color = "white";
                }
            }
        }
    }

    console.log(isParentsLook);
    if(!isParentsLook) {
        console.log("aaaaaa")
        $(".dis").html('');
    }

}



