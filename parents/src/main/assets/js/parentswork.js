var res;
var str="";
// 作业提醒
function worknotice(data) {

    $(".subjects").html("");
     if(data==undefined||data==null||data=='null'||data=='[]'||data.length==0||data==""){
                 var lisbox = document.getElementsByClassName("subjects")[0];
                     lisbox.innerHTML = `<img src="./images/no_data.png" style="width:3.48rem;height:5.1rem;display:block;margin:2.5rem auto">`
                        return;
                    }
    res = JSON.parse(data)
    //加判断
    for (var i = 0; i < res.length; i++) {

        if(res[i].endTime == undefined) {
                    res[i].endTime = ""
                } else {

          }
         if(res[i].title == undefined) {
                      res[i].title = ""
                         }
        if (res[i].status == 0) {
            str+=`  <div class="subconf">
        <ul class="rate">
            <li class="rate1">${res[i].endTime}</li>
            <li class="rate2">${res[i].title}</li>
            <span class="img1">已完成</span>
        </ul>
        <ul class="table">
            <li>您的孩子已完成</li>
            <li class="img">优秀的孩子，棒棒的家长</li>
            </ul>
    </div>`;
    } else {
        str+=` <div class="subconf">
        <ul class="rates">
        <li class="rate1">${res[i].endTime}</li>
        <li class="rate2">${res[i].title}</li>
            <span class="img2">未完成</span>
        </ul>
        <ul class="table2">
            <li>您的孩子未完成，快去提醒下吧</li>
            <li class="img">优异的成绩离不开家长的引导</li>
        </ul>
    </div>`;
    }
}
$(".subjects").html(str)
}





