var res;
var num = 0;
$(".navs").delegate("i", "click", function () {
    $(".list").html(' ');
    if ($(this).html() == "课前作业") {
        num = 0;
        window.android.changeItem(1);
    } else {
        num = 1; 
        window.android.changeItem(3);
    }
    $(this).addClass("active");
    $(this).parent().siblings().children().removeClass("active");

})
// 作业提醒
function worknotice(data) {
    res = JSON.parse(data);
    for (var i = 0; i < res.length; i++) {
        var str2 = "";
        var str = `
        <div class="work">
        <h2>
            <span>${res[i].beginTime} </span> -
            <span>${res[i].endTime}</span>
        </h2>
        <table>
            <caption>${res[i].title}</caption>
            <thead>
                <tr>
                <th>班级</th>
                <th>已完成</th>
                <th>未完成</th>
                <th>完成率</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <h3 class="notice">提醒</h3>
        </div>
        `;
        $(".list").append(str)
        for (var j = 0; j < res[i].classArr.length; j++) {
            str2 += `
             <tr>
            <td>${res[i].classArr[j].className}</td>
            <td>${res[i].classArr[j].finishCnt}</td>
            <td>${res[i].classArr[j].unfinishCnt}</td>
            <td>${res[i].classArr[j].rate}%</td>
            </tr>`;
        }
        $('tbody').html(str2)
        // 提醒按钮
        var notice = document.getElementsByClassName("notice");
        for (var i = 0; i < notice.length; i++) {
            (function (i) {
                notice[i].onclick = function () {
                    window.android.addWorkRemind(res[i].workId)
                }
            })(i)
        }
    }
}
// 作业提醒函数调用
//worknotice(data)


