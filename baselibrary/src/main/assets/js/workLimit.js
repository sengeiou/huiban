var details = document.getElementsByClassName("details")[0];
var notdone = document.getElementsByClassName("nodone")[0];
var active = document.getElementsByClassName("active")[0];
var active2 = document.getElementsByClassName("active2")[0];
var done = document.getElementsByClassName("done")[0];
var nodo = document.getElementsByClassName("navs")[0];
var todo = document.getElementsByClassName("navs")[1];
var filte = document.getElementById("filte");
var res=[];
var num=0;
todo.onclick = function () {
    // 渲染已完成页面列表
    //complatelist(res2)
    window.android.changeDataType(true)
    active.style.borderBottom = "none";
    active.style.color = "#E7E7E7";
    active2.style.borderBottom = "solid 0.08rem #00CFBF";
    active2.style.color = "#747474";
    notdone.style.display = "none";
    done.style.display = "block";
    details.style.display = "block";
    num = 1;
}
nodo.onclick = function () {
    active2.style.borderBottom = "none";
    window.android.changeDataType(false)
    active2.style.color = "#E7E7E7";
    active.style.borderBottom = "solid 0.08rem #00CFBF";
    active.style.color = "#747474";
    done.style.display = "none";
    notdone.style.display = "block";
    details.style.display = "none";
    num = 0;
}
// 点击筛选页完成课表
details.onclick = function () {
    // filte.style.display = "block";
    $("#filte").fadeToggle()
}
var back = document.getElementsByClassName("back")[0];
// 点击返回
back.onclick = function () {
    console.log("返回")
}
// 未完成页
function nocomplate(data) {
    res = JSON.parse(data)
    var str = "";
    var str2 = "";
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
                        <a href="javascript:void(0)" class="jump"></a>
                    </dd>
                </dl>
                <p class="time">${addtime}--${endtime}</p>
            </div>
       `;
    }
    notdone.innerHTML = str;
    var jump = document.getElementsByClassName("jump");
    // 未完成页点击跳转到每个单元对应的试题页
    window.android.log("点击"+jump.length);
    for (var i = 0; i < jump.length; i++) {
        (function (i) {
            jump[i].onclick = function () {
                window.android.log("点击");
                window.android.toNextPage(res[i].workId, res[i].prepareId);
            }
        })(i)
    }
}
// 完成页
function complatelist(data) {
     var res2 = JSON.parse(data);
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
                 //window.android.changeDataType(res2[j].id)
                 window.android.changeSubjectId(res2[j].id)
                // 渲染完成页
            }
        })(j)
    }
}

function complately(data) {
    // 解开注释
    var res3 = JSON.parse(data);
    var str3 = "";
    for (var i = 0; i < res3.length; i++) {
        var addtime = new Date(res3[i].addTime * 1000).toLocaleDateString();
        var endtime = new Date(res3[i].endTime * 1000).toLocaleDateString();
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
    }
    done.innerHTML = str3;
    var look = document.getElementsByClassName("look");
    // 未完成页点击跳转到每个单元对应的试题页
    for (var i = 0; i < look.length; i++) {
        (function (i) {
            look[i].onclick = function () {
                window.android.toNextPage(res3[i].workId, res3[i].prepareId)
            }
        })(i)
    }
}

// 页面加载
window.onload = function () {
    $("#filte").hide()
    done.style.display = "none";
    details.style.display = "none";
    // 数据渲染
    // 未完成
    //nocomplate(res)
}

// 下拉刷新上拉加载
var list = document.querySelector('.list');
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
        if(num == 0) {
            console.log("上拉1");
        } else {
            console.log("上拉2");
        }
       
    } else if (this.y === 0) {
        if(num == 0) {
            console.log("下拉1");
        } else {
            console.log("下拉2");
        }
    }
})



