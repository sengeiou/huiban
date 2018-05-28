var changeType = true;
//渲染科目
var listData;
var type = 1;
var knowType = 1;
var msg=[];
var sId,vId,fId,seriBrandId,chapBranId;
var subjectId, versionId, stageId;
function subject (data) {
    var data = JSON.parse(data);
    console.log(data);
    if(type == 1){
        $('.typeList').eq(2).html('');
        $('.typeList').eq(3).html('');
        console.log("aaa--"+data.length);
        for (var i=0;i<data.length;i++) {
            console.log("name--"+data[i].name);
            var nowSub = $('.typeList').eq(1).html();
            console.log(nowSub);
            console.log($('.typeList'));
            $('.typeList').eq(1).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(1) span').bind('click', function () {
                console.log("aaa1--"+data.length);
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].subjectId == undefined) {
                            subjectId ='';
                        }else {
                            subjectId = data[j].subjectId;
                        }                        
                    }
                }
                sId = subjectId;
                console.log("subjectId = "+subjectId);
                var msg = JSON.stringify(subjectId);
                window.android.dealWithJson("getFilterVersionL",msg);     
            });
        }
        type = 2;
    }else if (type == 2) {
        $('.typeList').eq(3).html('');
        for (var i=0;i<data.length;i++) {
            var nowSub = $('.typeList').eq(2).html();
            $('.typeList').eq(2).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(2) span').bind('click', function () {
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].versionId == undefined) {
                            var versionId = '';
                        }else {
                            var versionId = data[j].versionId;
                            vId = data[j].versionId;
                        }                        
                    }
                }
                vId = versionId;
                var msg = JSON.stringify({"subjectId": subjectId,"versionId": versionId})
                window.android.dealWithJson("getFilterFasL",msg);     
            });
        }
        type = 3;
    }else if (type == 3) {
        $('.list:eq(4) .navs').html('');
        for (var i=0;i<data.length;i++) {
            var nowSub = $('.typeList').eq(3).html();
            $('.typeList').eq(3).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(3) span').bind('click', function () {
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].fasId == undefined) {
                            var fasId = '';
                        }else {
                            var fasId = data[j].fasId;
                        }                        
                    }
                }
                fId = fasId;
                var msg = JSON.stringify({"subjectId": subjectId,"versionId": versionId,"fasId":fasId});
                window.android.dealWithJson("getFilterChapterL",msg);     
            });
        }
        type = 4;
    }else if (type == 4) {
        $('.navs').html(`<div id="treeDemo" class="ztree"></div>`);
        function zTreeOnClick (event,treeId,treeNode) {        
            chapBranId = treeNode.id;
        }
        var setting = {
                    view:{
                        showIcon: false
                    },
                    data:{
                        key:{
                            children: 'next1'
                        }
                    },
                    callback:{
                            onClick:zTreeOnClick
                    }
        };
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, data);
        });
    }
    $('.typeList span').bind('click', function () {
        $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
    })
}
//渲染知识点
function getKnows (data) {
    var data = JSON.parse(data);
    if (knowType == 1) {
        $('.typeList').eq(2).html('');        
        for (var i=0; i<data.length; i++) {
            var nowSub = $('.typeList').eq(1).html();
            $('.typeList').eq(1).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(1) span').bind('click', function () {
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].subjectId == undefined) {
                            subjectId = '';
                        }else {
                            subjectId = data[j].subjectId;
                            sId = data[j].subjectId;
                        }
                    }
                }
                window.android.dealWithJson("getFilterStageL",subjectId);
            });
        }
        knowType = 2;
    } else if (knowType == 2) {
        for (var i=0; i<data.length; i++) {
            var nowSub = $('.typeList').eq(2).html();
            $('.typeList').eq(2).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(2) span').bind('click', function () {
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].id == undefined) {
                            stageId = '';
                        }else {
                            stageId = data[j].id;
                        }
                    }
                }
                var msg = JSON.stringify({"subjectId":subjectId,"stageId":stageId});
                window.android.dealWithJson("getFilterKnowL",stageId);
            });
        }
    } else {
        $('.navs').html(`<div id="treeDemo" class="ztree"></div>`);
        function zTreeOnClick (event,treeId,treeNode) { 
            seriBrandId = treeNode.id;
        }
        var setting = {
                    view:{
                        showIcon: false
                    },
                    data:{
                        key:{
                            children: 'next1'
                        }
                    },
                    callback:{
                            onClick:zTreeOnClick
                    }
        };
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"), setting, data);
        });
    }
    $('.typeList span').bind('click', function () {
        $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
    })
}
//点击章节渲染
$('.listBtn').bind('click', function () {
    changeType = true;
    window.android.setStatue(changeType);
    $('.lists').html(`
        <div class="list">
            <p class="title">科目</p>
            <div class="typeList"></div>
        </div>
        <div class="list">
            <p class="title">版本</p>
            <div class="typeList"></div>
        </div>
        <div class="list">
            <p class="title">分册</p>
            <div class="typeList"></div>
        </div>
        <div class="list">
            <p class="title">目录</p>
            <ul class="navs">
                
            </ul>
        </div>
    `);
    $('.typeList span').bind('click', function () {
        $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
    })
});
//点击知识点渲染
$('.knows').bind('click', function () {
    changeType = false;
    window.android.setStatue(changeType);    
    $('.lists').html(`
        <div class="list">
            <p class="title">科目</p>
            <div class="typeList"></div>
        </div>
        <div class="list">
            <p class="title">学段</p>
            <div class="typeList"></div>
        </div>
        <div class="list">
            <p class="title">目录</p>
            <ul class="navs">
                
            </ul>
        </div>
    `);
    $('.typeList span').bind('click', function () {
        $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
    });       
});
//点击确定
$('.sureButton').bind('click', function () {    
    if (fId==undefined) {
        fId = "";
    }
    if (sId==undefined) {
        sId = "";
    }
    if (vId==undefined) {
        vId = "";
    }
    if (chapBranId==undefined) {
        chapBranId = ""
    }
    if (seriBrandId==undefined) {
        seriBrandId = ""
    }
    window.android.loadFilterData(sId,vId,fId,chapBranId,seriBrandId)
});