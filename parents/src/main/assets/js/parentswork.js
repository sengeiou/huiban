var res;
var str="";
// 作业提醒
function worknotice(data) {
    res = JSON.parse(data)
    $(".subjects").html(" ")
    for (var i = 0; i < res.length; i++) {
        if (res[i].status == 0) {
            str+=`  <div class="subconf">
        <ul class="rate">
            <li class="rate1">${res[i].addTime} -- ${res[i].endTime}</li>
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
        <li class="rate1">${res[i].addTime} -- ${res[i].endTime}</li>
        <li class="rate2">${res[i].title}</li>
            <span class="img2">未完成</span>
        </ul>
        <ul class="table2">
            <li>您的孩子已完成</li>
            <li class="img">优秀的孩子，棒棒的家长</li>
        </ul>
    </div>`;
    }
}
$(".subjects").html(str)
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





