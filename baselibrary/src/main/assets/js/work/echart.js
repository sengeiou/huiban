
function count(data) {
     var data =  JSON.parse(data);
    var title = document.getElementsByClassName("title")[0];
    var detail = document.getElementsByClassName("detail")[0];
    var num = document.getElementsByClassName("num")[0];
    var pie = document.getElementsByClassName("pie")[0];
    var table = document.getElementsByClassName("table")[0];
    var deta = document.getElementById("deta");
    // 跳转到describe.html页面
    detail.onclick = function() {
        window.android.toNextPage("describe")
    }
     // 跳转到details.html页面
    deta.onclick = function() {
        window.android.toNextPage("homeworkInf")
    }
    var str =  `<caption>知识掌握详情</caption>`;
   for(var i=0; i<data.knowArr.length; i++) {
    console.log(parseInt(data.knowArr[i].rate))
           if(parseInt(data.knowArr[i].rate)<0) {
           data.knowArr[i].rate = "--";
            var  rate = (data.knowArr[i].rate)*(2)/50 +'rem';
                  console.log(rate)
                  str+=` <tr>
                           <td style="text-align:center">${data.knowArr[i].branName}</td>
                           <td>
                               <p style="width:${rate}"></p>
                           </td>
                           <td>
                               <p>完成${data.knowArr[i].cnt}题</p>
                               <p>正确率${data.knowArr[i].rate}</p>
                           </td>
                       </tr>`;
           } else {
       var  rate = (data.knowArr[i].rate)*(2)/50 +'rem';
       console.log(rate)
       str+=` <tr>
                <td style="text-align:center">${data.knowArr[i].branName}</td>
                <td>
                    <p style="width:${rate}"></p>
                </td>
                <td>
                    <p>完成${data.knowArr[i].cnt}题</p>
                    <p>正确率${data.knowArr[i].rate}%</p>
                </td>
            </tr>`;
       }
   }
   table.innerHTML = str;
   	var m,f;
   		f = parseInt(data.times/60);
   		m = (data.times)%60;
   		if(f<10) {
   			f = "0"+f;
   		}
   		if(m<10) {
   			m = "0"+m;
   		}
   		data.times = f+":"+m;
   		var aaa,bbb;
   if(parseInt(data.userRate)<0) {
        aaa = "--"
    } else {
        aaa = data.userRate+"%"
    }
    if(parseInt(data.classRate)<0) {

            bbb = "--"
        } else {
            bbb = data.classRate+"%"
        }
    title.innerHTML = `<p>${data.title}</p>
    <p>
        <span>${data.addtime}</span> --
        <span>${data.endtime}</span> </p>`;
    num.innerHTML = data.examCnt;
    pie.innerHTML = `<li>提交名次：${data.rank.commitSeo}</li>
        <li>本次作业所用时间：${data.times} </li>
        <li>正确率：${aaa}</li>
        <li>班级平均正确率：${bbb}</li>`;
var myChart = echarts.init(document.getElementById('main'));
var a = `答对${data.rw.right}题` ;  
var b = `答错${data.rw.wrong}题` ;    
var c = `半对${data.rw.half}题` ; 
var d = `未判${data.rw.no}题` ; 
// 指定图表的配置项和数据
option = {
    // title : {
    // text: '某站点用户访问来源',
    // subtext: '纯属虚构',
    // x:'center'
    // },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        bottom: '20',
        data: ['直接访问', a, b, c, d]
    },
    series: [
        {
            name: '访问来源',
            type: 'pie',
            radius: '55%',
            center: ['50%', '40%'],
            data: [
                { value: data.rw.right, name: a },
                { value:data.rw.wrong, name: b },
                { value: data.rw.half, name: c },
                { value:data.rw.no, name: d},
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ],
    color: ['#35d1c4','#ff7777','#78bcfd','#f69664']
};


// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
myChart.on('legendselectchanged', function (params) {
    console.log(params);
    alert(1);
});
}