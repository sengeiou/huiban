var time = document.getElementsByClassName('time')[0];
var title = document.getElementsByClassName('total')[0];
var totals = document.getElementsByClassName('totals')[0];
var ul =  document.getElementsByClassName('answerList')[0];
var num =  document.getElementsByClassName('num')[0];
var but =  document.getElementsByClassName("button")[0];
var totalcount=0;
var count=0;
var obj;
function answer(data) {
  var list = JSON.parse(data);
  obj=list;
  time.innerHTML = list.time;
  title.innerHTML = list.title;
  totalcount = list.homework.length;
  totals.innerHTML = "共"+totalcount+"题";
  var str ="";

  for(let i=0; i<list.homework.length; i++) {
      if(list.homework[i].complete == true) {
        str+=`<li class="li" style=""><span  style="background:#d8d8d8">${i+1}</span></li>`;

      } else {
        str+=`<li class="li"><span>${i+1}</span></li>`
        console.log(count)
        count++;
      }
  }
  ul.innerHTML=str;


var save = document.getElementsByTagName('button')[0];
var submit = document.getElementsByTagName('button')[1];
var continu = document.getElementsByClassName("span")[0];
var moren = document.getElementsByClassName("span")[1];
var hint = document.getElementsByClassName("hint")[0];
// 保存作业
    save.onclick = function () {
    window.android.commitHomework(count,false);
//      window.android.commitHomework(false)
//        hint.style.display = "none"
    }
// 提交作业
submit.onclick = function() {
    console.log(count)
    window.android.commitHomework(count,true);
//    if( count != 0) {
//        hint.style.display = "block";
//        num.innerHTML = count;
//    }else{
//        window.android.commitHomework(true);
//        $(".backh").attr("href","javascript:void(0)")
//    }
}
// 继续作答
    continu.onclick = function () {
    hint.style.display = "none";
}
// 默认提交
moren.onclick = function() {
    // 页面跳转
    window.android.commitHomework(true);
    $(".backh").attr("href","javascript:void(0)")
    hint.style.display = "none";
    //judge(list)
  }
}

function judge() {
        var list=obj;
       var str="";
    for(var i=0; i<list.homework.length; i++) {
        if(list.homework[i].result == '0') {
            str+=`<li class="wrongs" ><span class="wrongss" style="background:#d8d8d8">${i+1}</span></li>`
          }
          if(list.homework[i].result == '1'){
            str+=`<li class="trues"><span class="truess">${i+1}</span></li>`
          }
          if(list.homework[i].result == '2') {
            str+=`<li><span>${i+1}</span></li>`
          }
    }
    ul.innerHTML = str;
    but.innerHTML = `<p class="backhomelist">返回作业列表</p>`;
    var backlist = document.getElementsByClassName('backhomelist')[0];
    // 返回作业列表
    backlist.onclick = function() {
      window.android.backHomeworkInf()
    }
    $(".backh").click(function() {
      window.android.backHomeworkInf()
    })
}
