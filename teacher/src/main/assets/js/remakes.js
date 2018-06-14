var res;
var count=0;
function remakes(data) {
    res = JSON.parse(data);
    $(".total").html(res.title);
    $(".names").html(res.stuName);
    $(".totals").html(`共${res.title.length}道`);
    var str="";
    for(var i=0;i<res.homework.length;i++) {
        if(res.homework[i].correct == true) {
            if(res.homework[i].result == 0) {
                str+=`
                <li  class="type2"><span  class="types2">${i+1}</span></li>
                `;
            } else if(res.homework[i].result == 1) {
                str+=`
                <li  class="type1"><span  class="types1">${i+1}</span></li>
                `;
            } else if(res.homework[i].result == 3) {
                str+=`
                <li  class="type3"><span  class="types3">${i+1}</span></li>
                `;
            } else {
                str+=`
                <li><span>${i+1}</span></li>
                `;
            }
        } else {
            str+=`
            <li><span>${i+1}</span></li>
            `;
            count++;
        }
    }
    $(".answerList").html(str)
    $(".num").html(count)
}
// 批阅函数调用

// 批改完成啊按钮
$(".button").click(function(){
  if (count != 0) {
      $(".hint").css({
      "display": "block"
      })
  }else{
      window.android.commit();
  }
})
// 继续作答
$(".span2").click(function(){
    $(".hint").css({
        "display":"none"
    })
})
// 默认提交
$(".span1").click(function(){
    $(".hint").css({
        "display":"none"
    });
     window.android.commit();
})