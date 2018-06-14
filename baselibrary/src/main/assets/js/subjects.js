var res;
var num = 0;

$(".navs").delegate("i", "click", function () {
    if ($(this).html() == "课前作业") {
        num = 0;
          window.android.changeItem(1)
    } else {
        num = 1;
          window.android.changeItem(3)
    }
    $(this).addClass("active");
    $(this).parent().siblings().children().removeClass("active");

})
// 作业提醒
function worknotice(data) {
    res = JSON.parse(data)
    console.log(res)
    var usertype = window.android.getUserType();
    if (usertype == 2 || usertype == 3) {
    $(".subject").html(res[0].subName)
    for (var i = 0; i < res.length; i++) {
    $(".subjects").append(`<div class="subconf">
    <ul class="rate">
    <li class="rate1">${res[i].className}${res[i].subName}作业 / 周报确认</li>
    <li class="rate2"><span class="large">${res[i].confirmCnt}</span>位家长已查看过确认报告</li>
    <li class="rate3">${res[i].beginDay}/${res[i].endDay}</li>
    </ul>
    <table class="table">
    <tr>
    <td>布置次数</td>
    <td>完成率</td>
    <td>确认率</td>
    </tr>
    <tr>
    <td>${res[i].workCnt}</td>
    <td>${res[i].finishRate}%</td>
    <td>${res[i].confirmRate}%</td>
    </tr>
    </table>
    </div>`)
    }
    } else {
    $(".subject").html(res[0].subName)
    for (var i = 0; i < res.length; i++) {
    $(".subjects").append(`<div class="subconf">
    <ul class="rate">
    <li class="rate1">${res[i].className}${res[i].subName}作业 / 周报确认</li>
    <li class="rate2"><span class="large">本周共布置作业${res[i].confirmCnt}次</span></li>
    <li class="rate3">${res[i].beginDay}/${res[i].endDay}</li>
    </ul>
    <table class="table">
    <tr>
    <td>布置次数</td>
    <td>完成率</td>
    <td>确认率</td>
    </tr>
    <tr>
    <td>${res[i].workCnt}</td>
    <td>${res[i].finishRate}%</td>
    <td>${res[i].confirmRate}%</td>
    </tr>
    </table>
    </div>`)
    }
    }
}
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
        if (num == 0) {
            console.log("上拉1");
             // window.android.getMoreData(false)
        } else {
            console.log("上拉2");
             // window.android.getMoreData(false)
        }

    } else if (this.y === 0) {
        if (num == 0) {
            console.log("下拉1");
            // window.android.getMoreData(true)
        } else {
            console.log("下拉2");
            // window.android.getMoreData(true)
        }
    }
})


