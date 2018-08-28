var details = document.getElementsByClassName("details")[0];
var active = document.getElementsByClassName("active")[0];
var active2 = document.getElementsByClassName("active2")[0];
var done = document.getElementsByClassName("done")[0];
var nodo = document.getElementsByClassName("navs")[0];
var todo = document.getElementsByClassName("navs")[1];
var filte = document.getElementById("filte");
var res=[];
var ind=0;
var num=0;
 var res2;
 $(".details").hide();
  $("#filte").hide();
todo.onclick = function () {
    // 渲染已完成页面列表
    //complatelist(res2)
     $(".done").html(" ")
    window.android.changeDataType(true)
      active.style.borderBottom = "none";
       active.style.color = "black";
       active2.style.borderBottom = "solid 0.08rem #00CFBF";
       active2.style.color = "#00CFBF";
       details.style.display = "block";
       num = 1;
    if(res2 != undefined) {
        window.android.changeSubjectId(res2[ind].id)
    }
}
nodo.onclick = function () {
     $(".done").html('');
    active2.style.borderBottom = "none";
    window.android.changeDataType(false)
       active2.style.color = "black";
       active.style.borderBottom = "solid 0.08rem #00CFBF";
       active.style.color = "#00CFBF";
       details.style.display = "none";
    num = 0;
    window.android.changeSubjectId(0)
}
// 点击筛选页完成课表
details.onclick = function () {
    // filte.style.display = "block";
    if(res2==undefined){
        window.android.toastLog("暂无科目");
    }else{
        $("#filte").fadeToggle()
    }
}
var back = document.getElementsByClassName("back")[0];
// 点击返回
back.onclick = function () {
    console.log("返回")
}
// 未完成页
function nocomplate(data) {
             $('.list').html('');
             var main = document.getElementById("main");
             main.style.background = '';
            if(data==undefined||data==null||data=='null'||data=='[]'||data.length==0||data==""){
            main.style.background = 'url("./images/no_data.png")';
            main.style.backgroundRepeat = "no-repeat";
            main.style.backgroundSize = "3.48rem 5.1rem";
            main.style.backgroundPosition = "center 3rem"
             return;
            }
    res = JSON.parse(data)
    var str = "";
    var str2 = "";
    $(".done").html(" ");
     var usertype = window.android.getUserType();
     var classNameNoComplete;
     if(usertype == 4) {
        classNameNoComplete='looka';
     }else{
        classNameNoComplete='jump';
     }
    for (var i = 0; i < res.length; i++) {
        var addtime = new Date(res[i].addTime * 1000).toLocaleDateString();
        var endtime = new Date(res[i].endTime * 1000).toLocaleDateString();
        str += `
       <div class="listMsg">
                <dl>
                    <dt>
                        <h3>${res[i].theme}</h3>
                        <p>${res[i].descInfo}</p>
                    </dt>
                    <dd>
                        <a href="javascript:void(0)" class=${classNameNoComplete}></a>
                    </dd>
                </dl>
                <p class="time">${addtime}--${endtime}</p>
            </div>
       `;
    }
    done.innerHTML = str;
    $('.listoo').css('height',$('.done').height()+100);
        myIscroll.refresh();
    //var usertype = window.android.getUserType();
    if(usertype != 4) {
        var jump = document.getElementsByClassName("jump");
        // 未完成页点击跳转到每个单元对应的试题页
        //window.android.log("点击"+jump.length);
        for (var i = 0; i < jump.length; i++) {
            (function (i) {
                jump[i].parentNode.parentNode.onclick = function () {
                    //window.android.log("点击");
                    window.android.toNextPage(res[i].workId, res[i].prepareId);
                }
            })(i)
        }
    } else {
        var jump = document.getElementsByClassName("looka");
        // 未完成页点击跳转到每个单元对应的试题页
        //window.android.log("点击"+jump.length);
        for (var i = 0; i < jump.length; i++) {
            (function (i) {
                jump[i].parentNode.parentNode.onclick = function () {
                    //window.android.log("点击");
                    window.android.toNextPage(res[i].workId, res[i].prepareId);
                }
            })(i)
        }
    }

}
//toastLog
// 完成页
function complatelist(data) {
    if(data==undefined||data==null||data=="null"){
        res2=undefined;
        filte.innerHTML="";
        return;
    }
    res2 = JSON.parse(data);
    var str2 = "";
    for (var i = 0; i < res2.length; i++) {
        str2 += `<li class="li">${res2[i].name}</li>`;
    }
    filte.innerHTML = str2;
    var lis = document.getElementsByClassName('li');
    for (var j = 0; j < lis.length; j++) {
        (function (j) {
            lis[j].onclick = function () {
                $("#filte").hide();
                //获取对应科目完成列表
                ind = j;
                 //window.android.changeDataType(res2[j].id)
                 window.android.changeSubjectId(res2[j].id)
                // 渲染完成页
            }
        })(j)
    }
}

function complately(data) {
                         $('.list').html('');
                      var main = document.getElementById("main");
                      main.style.background = '';
                     if(data==undefined||data==null||data=='null'||data=='[]'||data.length==0||data==""){
                     main.style.background = 'url("./images/no_data.png")';
                     main.style.backgroundRepeat = "no-repeat";
                     main.style.backgroundSize = "3.48rem 5.1rem";
                     main.style.backgroundPosition = "center 3rem"
                      return;
                     }
    // 解开注释
    var res3 = JSON.parse(data);
    var str3 = "";
    for (var i = 0; i < res3.length; i++) {
        var addtime = new Date(res3[i].addTime * 1000).toLocaleDateString();
        var endtime = new Date(res3[i].endTime * 1000).toLocaleDateString();
        if(res3[i].status == 3) {
        str3 += `
                <div class="listMsg">
                <dl>
                    <dt>
                        <h3>${res3[i].theme}</h3>
                        <p>${res3[i].descInfo}</p>
                    </dt>
                    <dd>
                    <span class="read"></span>
                    <span class="look"></span>
                    </dd>
                </dl>
                <p class="time">${addtime}--${endtime}</p>
            </div>
                `;
                } else {
                str3 += `
                        <div class="listMsg">
                        <dl>
                            <dt>
                                <h3>${res3[i].theme}</h3>
                                <p>${res3[i].descInfo}</p>
                            </dt>
                            <dd>
                            <span class="look" style="margin-right:15%"></span>
                            </dd>
                        </dl>
                        <p class="time">${addtime}--${endtime}</p>
                    </div>
                        `;
                }

    }
    done.innerHTML = str3;
    $('.listoo').css('height',$('.done').height()+100);
        myIscroll.refresh();
    var look = document.getElementsByClassName("look");
    // 未完成页点击跳转到每个单元对应的试题页
    for (var i = 0; i < look.length; i++) {
        (function (i) {
            look[i].parentNode.parentNode.onclick = function () {
                window.android.toNextPage(res3[i].workId, res3[i].prepareId)
            }
        })(i)
    }
}

// 页面加载
window.onload = function () {
    $("#filte").hide();
    $(".details").hide()
    details.style.display = "none";
    // 数据渲染
    // 未完成
    //nocomplate(res)
}

// 下拉刷新上拉加载
var list = document.querySelector('.listoo');
var myIscroll = new IScroll('#main', {
     scrollbars: false,
    probeType: 3,
    click:true
});
var flag = true;
var y,y2,y3;
        var listoo = document.getElementsByClassName("listoo")[0];
        listoo.ontouchstart = function(e) {
                         y = 0;
                e=e || window.event;
                        y2=e.touches[0].pageY;
        listoo.ontouchmove = function(e) {
                e = e|| window.event
                }
            }
        listoo.ontouchend = function(e) {
                e=e || window.event;
                y3=e.changedTouches[0].pageY;
                y = y3-y2;
        }
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
     if ( y<-300 && this.y === this.maxScrollY){
        if(num == 0) {
            console.log("上拉1");
            window.android.getMoreData(false);
        } else {
            console.log("上拉2");
            window.android.getMoreData(false);
        }
       
    } else if (this.y === 0 && y>300) {
        if(num == 0) {
            console.log("下拉1");
            window.android.getMoreData(true);
        } else {
            console.log("下拉2");
            window.android.getMoreData(true);
        }
    }
})



