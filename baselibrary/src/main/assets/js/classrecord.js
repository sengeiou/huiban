var done = document.getElementsByClassName("complate")[0];
var details = document.getElementsByClassName("details")[0];
var filte = document.getElementById("filte");
// 完成页
function complatelist(data) {
     var res2 = JSON.parse(data)
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
                 window.android.changeSubjectId(res2[j].id)
                // 渲染完成页
                //complately(res)
            }
        })(j)
    }
}

function complately(data) {
    // 解开注释
     var res3 = JSON.parse(data)
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
                    <span class="looks"></span>
                    </dd>
                </dl>
                <p class="time">${addtime}--${endtime}</p>
            </div>
                `;
    }
    done.innerHTML = str3;
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
