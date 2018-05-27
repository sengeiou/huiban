var title = document.getElementsByClassName("title")[0];
var circle = document.getElementsByClassName("circle")[0];
var ul = document.getElementsByClassName("ul")[0];
var text = document.getElementsByTagName('p');
var wrapper = document.getElementsByClassName('swiper-wrapper')[0];
var answerlist = document.getElementsByClassName("answer")[0];
var button = document.getElementsByClassName('span');
var list = document.getElementById('xuan');
var typee = document.getElementById('text');
var ques = document.getElementsByClassName("swiper-slide");
var analyze = document.getElementById("analyze");
var fl=true;
circle.onclick = function () {
    if(fl) {
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
var date;
// 所有题的容器
var res =[]; 
var indexnum;
// 渲染标题
function item(data) {
    var res2 = JSON.parse(data);
    for(var i=0; i<res2.length; i++) {
        res[i] = []
    }
    firstone(window.android.loadData(0,res2[0].type))
   
    var ul = document.getElementsByClassName("ul")[0];
    var str1 = "";
    title.innerHTML = res2[0].title;
    for (var pro of res2) {
        str1 += `<li class="titles">${pro.title}</li>`;
    }
    ul.innerHTML = str1;
    var titles = document.getElementsByClassName("titles");
    for(var i=0; i<titles.length; i++) {
        (function(i) {
        titles[i].onclick = function() {
            indexnum = i;
            title.innerHTML = this.innerHTML;
            ul.style.display = 'none';
            circle.src = "../../images/work/28046656282239871.png";
            // 传入type 获取后台数据data
        //   window.android.loadData(indexnum,res2[indexnum].type) 
           add(indexnum,window.android.loadData(indexnum,res2[indexnum].type) )
           rende2(res[indexnum])
        }
    })(i)
    }
}
function add(indexnum,date) {
    var date = JSON.parse(date)
    if(res[indexnum] == "") {
    for (var k = 0; k < date.length; k++) {
        for (var j = 0; j < date[k].next.length; j++) {
            res[indexnum].push(date[k].next[j])
        }
    }
 } 
 homeWork(res[indexnum])
}
 // 页面跳转
 var totalanswer = document.getElementsByClassName("answer")[0];
 totalanswer.onclick = function() {
    //  传此试卷的id   跳转到list.html
    window.android.toHomeworkResultPage();
 }
// 作业列表
function homeWork(data) {
    //  var date =JSON.parse(data)
    var swiperWrapper = document.getElementsByClassName("swiper-wrapper")[0];
    var num1 = data.length;
    if (num1 <= 9) {
        var swiperContainer = document.getElementsByClassName('swiper-container')[0];
        swiperContainer.style.width = "7.5rem"
    } else {
        var swiper = new Swiper('.swiper-container', {
            slidesPerView: num1 * 1.4,
            freeMode: true,
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
        });
    }
    var str = "";
    for (var i = 0; i < data.length; i++) {
        if(data[i].status == "2") {
        str += `
        <div id="slide" class="swiper-slide" style="background:#c6c6c6;color:gray">${i + 1}</div>
    `;
        }
    else {
    str += `
        <div id="slide" class="swiper-slide" style="background:#00c7b5;color:white">${i + 1}</div>
        `;
        }
    }
    swiperWrapper.innerHTML = str;
    var button = document.getElementsByClassName("swiper-slide");
    var this_;
    for(var i=0; i<button.length; i++) {
      (function(i) {
      button[i].onclick = function() {
        console.log(i)
        this.style.width = "0.66rem";
        this.style.height = "0.66rem";
        this.style.fontSize = "0.4rem";
        this.style.marginTop = "0.14rem";
        this.style.lineHeight = "0.61rem";
        this.style.marginLeft = ".22rem";
        console.log(data[i].optionName)
        console.log(data[i].content)
        rende(data,i)
        this_ =this;
         for (var j = 0; j < button.length; j++) {
          if(this_ != button[j]) {
            button[j].style.width = "0.5rem";
            button[j].style.height = "0.5rem";
            button[j].style.fontSize = "0.3rem";
            button[j].style.marginTop = "0.2rem";
            button[j].style.lineHeight = ""
            button[j].style.marginLeft = ".3rem";
          }
        }
      }
    })(i)
    }
}
function rende(data,index) {
                // 主观题
                console.log(index)
                if (data[index].optionName == "subjective") {
                    typee.innerHTML = `${data[index].content} <div id="analyze">
                    <ul>
                    <li><span>${data[index].Answer}</span></li>
                    <li>你的答案：${data[index].stuAnswer}</li>
                    <li>${data[index].analysis} </li>
                    </ul>
                    </div>`;
                }
                if (data[index].optionName == "judge") {
                    typee.innerHTML = `<div class="judge">
                        <h3>${data[index].content}</ul></h3>
                        <span class="img1" style="margin-top:25px"></span> 
                        <span class="img2" style="margin-top:25px"></span>
                        </div>
                        <div id="analyze">
                        <ul>
                        <li>正确答案：<span>${data[index].Answer}</span></li>
                        <li>你的答案：${data[index].stuAnswer}</li>
                        <li>${data[index].analysis} </li>
                        </ul>
                        </div>`;
                    var imgs = document.getElementsByTagName('span');     
                        if(data[index].Answer == "对") {
                            img[0].className = "img1"
                            img[1].className = "img4"
                       }else {
                            img[0].className = "img2"
                            img[1].className = "img3"
                       }
                };
            // 填空题
                if (data[index].optionName == "fill") {
                    typee.innerHTML = `<ul class="list">${data[index].content}</ul> 
                    <div id="analyze">
                    <ul>
                    <li><span>${data[index].Answer}</span></li>
                    <li>你的答案：${data[index].stuAnswer}</li>
                    <li>${data[index].analysis} </li>
                    </ul>
                    </div>`;
                        var tkbox = document.getElementsByClassName('tkbox')[0];
                        var button = document.getElementsByClassName('span');
                        var answ = document.getElementsByClassName("lxitem_ans_frm")[0]
                        tkbox.style.display = "none"
                        for(var pro of button) {
                            if(data[index].Answer == pro.innerHTML) {
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
                if (data[index].optionName == "radio") {
                 typee.innerHTML = `<ul class="list">${data[index].content}</ul> <div id="xuan"> 
                <span class="span">A</span>
                <span class="span">B</span>
                <span class="span">C</span>
                <span class="span">D</span>
                 </div>
                <div id="analyze">
                <ul>
                <li>正确答案：<span>${data[index].Answer}</span></li>
                <li>你的答案：${data[index].stuAnswer}</li>
                <li>${data[index].analysis} </li>
                </ul>
                </div>`;
                    var button = document.getElementsByClassName('span');
                    for(var pro of button) {
                        if(data[index].Answer == pro.innerHTML) {
                            pro.style.background = "#07cab9";
                            pro.style.color = "white";
                        }
                    }
                }
                // 多选题
                if (data[index].optionName == "check") {
                    typee.innerHTML = `<ul class="list">${data[index].content}</ul> <div id="xuan"> 
                    <span class="span">A</span>
                    <span class="span">B</span>
                    <span class="span">C</span>
                    <span class="span">D</span>
                     </div>
                    <div id="analyze">
                    <ul>
                    <li>正确答案：<span>${data[index].Answer}</span></li>
                    <li>你的答案：${data[index].stuAnswer}</li>
                    <li>${data[index].analysis} </li>
                    </ul>
                    </div>`;
                        var button = document.getElementsByClassName('span');
                        for(var pro of button) {
                            for(var j=0; i<data[index].Answer.length; j++) {
                                if(data[index].Answer[j] == pro.innerHTML) {
                                    pro.style.background = "#07cab9";
                                    pro.style.color = "white";
                                }
                            }
                       }
                }
}
// 第一题刷新
function rende2(datas){
if(datas==undefined||datas.length==0){
return;
}
     // 主观题
     if (datas[0].optionName == "subjective") {
        typee.innerHTML = `${datas[0].content} <div id="analyze">
        <ul>
        <li><span>${datas[0].Answer}</span></li>
        <li>你的答案：${datas[0].stuAnswer}</li>
        <li>${datas[0].analysis} </li>
        </ul>
        </div>`;
    }
    if (datas[0].optionName == "judge") {
        typee.innerHTML = `<div class="judge">
            <h3>${datas[0].content}</ul></h3>
            <span class="img1" style="margin-top:25px"></span> 
            <span class="img2" style="margin-top:25px"></span>
            </div>
            <div id="analyze">
            <ul>
            <li>正确答案：<span>${datas[0].Answer}</span></li>
            <li>你的答案：${datas[0].stuAnswer}</li>
            <li>${datas[0].analysis} </li>
            <li>${datas[0].analysis} </li>
            <li>${datas[0].analysis} </li>
            </ul>
            </div>`;
        var imgs = document.getElementsByTagName('span');     
            if(datas[0].Answer == "对") {
                img[0].className = "img1"
                img[1].className = "img4"
           }else {
                img[0].className = "img2"
                img[1].className = "img3"
           }
    };
// 填空题
    if (datas[0].optionName == "fill") {
        typee.innerHTML = `<ul class="list">${datas[0].content}</ul> 
        <div id="analyze">
        <ul>
        <li><span>${datas[0].Answer}</span></li>
        <li>你的答案：${datas[0].stuAnswer}</li>
        <li>${datas[0].analysis} </li>
        </ul>
        </div>`;
            var tkbox = document.getElementsByClassName('tkbox')[0];
            var button = document.getElementsByClassName('span');
            var answ = document.getElementsByClassName("lxitem_ans_frm")[0]
            tkbox.style.display = "none"
            for(var pro of button) {
                if(datas[0].Answer == pro.innerHTML) {
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
    if (datas[0].optionName == "radio") {
     typee.innerHTML = `<ul class="list">${datas[0].content}</ul> <div id="xuan"> 
    <span class="span">A</span>
    <span class="span">B</span>
    <span class="span">C</span>
    <span class="span">D</span>
     </div>
    <div id="analyze">
    <ul>
    <li>正确答案：<span>${datas[0].Answer}</span></li>
    <li>你的答案：${datas[0].stuAnswer}</li>
    <li>${datas[0].analysis} </li>
    </ul>
    </div>`;
        var button = document.getElementsByClassName('span');
        for(var pro of button) {
            if(datas[0].Answer == pro.innerHTML) {
                pro.style.background = "#07cab9";
                pro.style.color = "white";
            }
        }
    }
    // 多选题
    if (datas[0].optionName == "check") {
        typee.innerHTML = `<ul class="list">${datas[0].content}</ul> <div id="xuan"> 
        <span class="span">A</span>
        <span class="span">B</span>
        <span class="span">C</span>
        <span class="span">D</span>
         </div>
        <div id="analyze">
        <ul>
        <li>正确答案：<span>${datas[0].Answer}</span></li>
        <li>你的答案：${datas[0].stuAnswer}</li>
        <li>${datas[0].analysis} </li>
        </ul>
        </div>`;
            var button = document.getElementsByClassName('span');
            for(var pro of button) {
                for(var j=0; i<datas[0].Answer.length; j++) {
                    if(datas[0].Answer[j] == pro.innerHTML) {
                        pro.style.background = "#07cab9";
                        pro.style.color = "white";
                    }
                }
           }
    }
}
// 第一张卷子
function firstone (data) {
    date =  JSON.parse(data)
    rendes();
}

// 页面加载时
function rendes() {
    for (var k = 0; date!=undefined && k < date.length; k++) {
        for (var j = 0; j < date[k].next.length; j++) {
            res[0].push(date[k].next[j])
        }
    }
    homeWork(res[0])
    rende2(res[0])
 }
window.onload = function () {

    for (var i = 0; i < ques.length; i++) {
        ques[i].style.width = "0.5rem";
        ques[i].style.height = "0.5rem";
        // ques[i].style.background = "#00c7b5";
        // ques[i].style.color = "white";
    }
}
