var done = document.getElementsByClassName("complate")[0];
var details = document.getElementsByClassName("details")[0];
var filte = document.getElementById("filte");
// 完成页
function complatelist(data) {
     if(data==undefined||data==null||data=='null'||data=='[]'||data.length==0||data==""){
                return;
            }
     var res2 = JSON.parse(data)
    var str2 = "";
    for (var i = 0; i < res2.length; i++) {
        str2 += `<li class="li">${res2[i].name}</li>`;
    }
    filte.innerHTML = str2;
    var lis = document.getElementsByClassName('li');
    window.android.changeSubjectId(res2[0].id);
    for (var j = 0; j < lis.length; j++) {
        (function (j) {
            lis[j].onclick = function () {
                $("#filte").hide();
                //获取对应科目完成列表
                //console.log(res2[j].id)
                 window.android.changeSubjectId(res2[j].id)
                // 渲染完成页
                //complately(res)
            }
        })(j)
    }
}

function complately(data) {
                         $('.complate').html('');
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
     var res3 = JSON.parse(data)
     console.log(res3)
    var str3 = "";
    for (var i = 0; i < res3.length; i++) {
        var addtime = new Date(res3[i].addTime * 1000).toLocaleDateString();
        str3 += `
                <div class="listMsg">
                <dl>
                    <dt>
                        <h3>${res3[i].theme}</h3>
                        <p>${res3[i].descInfo}</p>
                    </dt>
                    <dd>
                    <span class="read"></span>
                    <span class="looks"></span>
                    </dd>
                </dl>
                <p class="time">${addtime}</p>
            </div>
                `;
    }
    done.innerHTML = str3;
     $('.listoo').css('height',$('.done').height()+100);
        myIscroll.refresh();
    var look = document.getElementsByClassName("looks");
    // 未完成页点击跳转到每个单元对应的试题页
    for (var i = 0; i < look.length; i++) {
        (function (i) {
            look[i].onclick = function () {
                 window.android.toNextPage(res3[i].workId, res3[i].prepareId)
            }
        })(i)
    }
}

// 点击筛选页完成课表
details.onclick = function () {
    // filte.style.display = "block";
    $("#filte").fadeToggle()
}
window.onload = function() {
    //complatelist(res)
    $("#filte").hide()
}
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
           if ( y<-300 && this.y === this.maxScrollY) {
               console.log("上拉");
               window.android.getMoreData(false);
           } else if (this.y === 0 && y>300) {
               console.log("下拉");
               window.android.getMoreData(true);
           }
           })
