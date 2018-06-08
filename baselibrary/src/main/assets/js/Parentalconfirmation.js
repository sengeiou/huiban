var res,str;
function pconfirm(data) {
    res = JSON.parse(data);
    str = "";
    for (var i = 0; i < res.length; i++) {
        if (res[i].isClick == 0) {
            str += `
            <ul class="subj">
                <li>${res[i].subName}  /  周确认报告</li>
                <li>${res[i].beginDay} -- ${res[i].endDay}</li>
                <span class="see1"></span>
            </ul>
         `;
        } else {
            str += `
            <ul class="subj">
                <li>数学作业/周确认报告</li>
                <li>2017312312312</li>
            <span class="see2"></span>
            </ul>
        `;
        }
    }
    $(".confirm").html(str);
    // 可点击的
    var see = document.getElementsByClassName("see1");
    console.log("itemClick11="+see);
    for (var i = 0; i < see.length; i++) {
        (function (i) {
            see[i].onclick=function () {
                console.log("itemClick="+res[i].subjectId);
                window.android.itemClick(res[i].subjectId)
            }
        })(i)
    }
}
// 页面渲染函数


