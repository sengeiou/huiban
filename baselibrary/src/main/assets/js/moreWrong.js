var type = 1, organs = '',subjectId=-100, versionId=-100, fasId=-100, chapterId=-100, knowId=-100, stageId=-100;
function subject (data) {
    var data = JSON.parse(data);
    if (type == 1) {//机构
        for (var i=0;i<data.length;i++) {
        $('.lists .list').eq(1).hide();
            var nowSub = $('.typeList').eq(1).html();
            $('.typeList').eq(1).html(nowSub+`
                <span>${data[i].name}</span>
            `);
        }
        $('.typeList:eq(1) span').bind('click', function () {
            $('.lists .list').eq(1).show();
            $('.lists .list').eq(2).hide();
            $('.lists .list').eq(3).hide();
            $('.lists .list').eq(4).hide();
            $('.lists .list').eq(5).hide();
            type = 2;
            for (var j=0; j<data.length; j++) {
                if(data[j].name === $(this).text()){
                    if (data[j].id == undefined) {
                        //不考虑
                    }else {
                         organs = data[j].id;
                    }
                }
            }
            $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
            var msg = JSON.stringify({"organs":organs,"chapKnow":1});//获取学段
            window.android.dealWithJson("getFilterStageL",msg);
        });
    } else if (type == 2) {//学段
        $('.typeList').eq(2).html('');
        for (var i=0;i<data.length;i++) {
            var nowSub = $('.typeList').eq(2).html();
            $('.typeList').eq(2).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(2) span').bind('click', function () {
                $('.lists .list').eq(2).show();
               $('.lists .list').eq(3).hide();
               $('.lists .list').eq(4).hide();
               $('.lists .list').eq(5).hide();
                type = 3;
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].id == undefined) {
                            stageId =-100;
                        }else {
                            stageId = data[j].id;
                        }
                    }
                }

                var msg = JSON.stringify({"organs":organs,"chapKnow":1});
                window.android.dealWithJson("getFilterSubjectL",msg);
                $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
            });
        }

    }else if (type == 3) {//科目
             $('.typeList').eq(3).html('');
             console.log(data)
             for (var i=0;i<data.length;i++) {
                 var nowSub = $('.typeList').eq(3).html();
                 $('.typeList').eq(3).html(nowSub+`
                     <span>${data[i].name}</span>
                 `);
                 $('.typeList:eq(3) span').bind('click', function () {
                     $('.lists .list').eq(3).show();

                     $('.lists .list').eq(4).hide();
                     $('.lists .list').eq(5).hide();

                     type = 4;
                     for (var j=0; j<data.length; j++) {
                         if(data[j].name === $(this).text()){
                             if (data[j].id == undefined) {
                                 subjectId =-100;
                             }else {
                                 subjectId = data[j].id;
                             }
                         }
                     }
                     var msg = JSON.stringify({"subjectId":subjectId,"organs":organs});
                     window.android.dealWithJson("getFilterVersionL",msg);
                     $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
                 });
             }

         } else if (type == 4) {//版本
        $('.typeList').eq(4).html('');
        for (var i=0;i<data.length;i++) {
            var nowSub = $('.typeList').eq(4).html();
            $('.typeList').eq(4).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(4) span').bind('click', function () {
                $('.lists .list').eq(4).show();
                  $('.lists .list').eq(5).hide();
                type = 5;
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].id == undefined) {
                            versionId = -100;
                        }else {
                            versionId = data[j].id;
                        }
                    }
                }
                var msg = JSON.stringify({"subjectId": subjectId,"versionId": versionId,"organs":organs})
                window.android.dealWithJson("getFilterFasL",msg);
                $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
            });
        }
    }else if(type == 5 ){//分册
                $('.typeList').eq(6).html('');
                for (var i=0;i<data.length;i++) {
                    var nowSub = $('.typeList').eq(5).html();
                    $('.typeList').eq(5).html(nowSub+`
                        <span>${data[i].name}</span>
                    `);
                    $('.typeList:eq(5) span').bind('click', function () {
                        for (var j=0; j<data.length; j++) {
                            if(data[j].name === $(this).text()){
                                if (data[j].id == undefined) {
                                    fasId = -100;
                                }else {
                                    fasId = data[j].id;
                                }
                            }
                        }
                          $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
                          var num = organs.indexOf(",");

                        if( num != -1 || subjectId== 0 || versionId== 0 || fasId== 0 ||  stageId== 0) {
                            return;
                        }else {
                        type = 6;
                        var msg = JSON.stringify({"subjectId": subjectId,"versionId": versionId,"fasId":fasId,"organs":organs,"stageId":stageId});
                        window.android.dealWithJson("getFilterChapterL",msg);
                        }
                    });
                }
    } else if (type == 6) {//目录
         $('.navs').html(' ');
         //console.log(data)
            if(data== undefined || data==null || data== "null" || data==""){
            $('.lists .list').eq(5).hide();
                return;
            }
       $('.lists .list').eq(5).show();
        $('.navs').html(`<div id="treeDemo" class="ztree"></div>`);
        function zTreeOnClick (event,treeId,treeNode) {
            chapterId = treeNode.id;
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
        $('.typeList span').bind('click', function () {
            $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
        })
    }
}
//渲染知识点
function getKnows(data) {
    var data = JSON.parse(data);
    if (type == 1) {
        for (var i=0;i<data.length;i++) {
            var nowSub = $('.typeList').eq(1).html();
            $('.typeList').eq(1).html(nowSub+`
                <span>${data[i].name}</span>
            `);
        }
        $('.typeList:eq(1) span').bind('click', function () {
            $('.lists .list').eq(1).show();
            $('.lists .list').eq(2).hide();
            type = 2;
            for (var j=0; j<data.length; j++) {
                if(data[j].name === $(this).text()){
                    if (data[j].id == undefined) {
                        subjectId = -100;
                    }else {
                        subjectId = data[j].id;
                    }
                }
            }
            $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
            var msg = JSON.stringify({"subjectId":subjectId,"chapKnow":2});
            //window.android.dealWithJson("getFilterVersionL",msg);
            window.android.dealWithJson("getFilterStageL",msg);
        });
    } else if (type == 2) {
        $('.typeList').eq(2).html('');
        for (var i=0; i<data.length; i++) {
            var nowSub = $('.typeList').eq(2).html();
            $('.typeList').eq(2).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(2) span').bind('click', function () {
                $('.lists .list').eq(2).show();
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].id == undefined) {
                            stageId = -100;
                        }else {
                            stageId = data[j].id;
                        }
                    }
                }
                var msg = JSON.stringify({"subjectId":subjectId,"stageId":stageId});
                window.android.dealWithJson("getFilterKnowL",msg);
                type = 3;
            });
        }
    } else if (type == 3) {
        if(data==undefined||data==null||data=='null'){
            return;
        }

        $('.typeList').eq(3).html('');
        $('.navs').html(`<div id="treeDemo" class="ztree"></div>`);
        function zTreeOnClick (event,treeId,treeNode) {
            knowId = treeNode.id;
        }
        var setting = {
            view:{
                showIcon: false,
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
//初始化章节
function setSubject () {
    type = 1, organs = '',subjectId=-100, versionId=-100, fasId=-100, chapterId=-100, knowId=-100, stageId=-100;
    $('.lists').html(`
        <div class="list">
            <p class="title">机构</p>
            <div class="typeList"></div>
        </div>
             <div class="list">
                    <p class="title">学段</p>
                    <div class="typeList"></div>
                </div>
        <div class="list hide">
            <p class="title">科目</p>
            <div class="typeList"></div>
        </div>
        <div class="list hide">
            <p class="title">版本</p>
            <div class="typeList"></div>
        </div>
        <div class="list hide">
            <p class="title">分册</p>
            <div class="typeList"></div>
        </div>
        <div class="list hide">
             <p class="title">目录</p>
            <ul class="navs"></ul>
        </div>
    `);
}
// 初始化知识点
function setKnows () {
    type = 1, organs = '',subjectId=-100, versionId=-100, fasId=-100, chapterId=-100, knowId=-100, stageId=-100;
    $('.lists').html(`
        <div class="list">
            <p class="title">科目</p>
            <div class="typeList"></div>
        </div>
        <div class="list hide">
            <p class="title">学段</p>
            <div class="typeList"></div>
        </div>
        <div class="list hide">
            <p class="title">目录</p>
            <ul class="navs">

            </ul>
        </div>
    `);
}
setSubject();
//点击章节
$('.listBtn').bind('click', function () {
    setSubject();
    window.android.setStatue(true);
});
//点击知识点
$('.knows').bind('click', function () {
    setKnows();
    window.android.setStatue(false);
});
//点击确定
$('.sureButton').bind ('click', function () {
    msg = JSON.stringify({"subjectId":subjectId,"versionId":versionId,"fasId":fasId,"chapterId":chapterId,"knowId":knowId});
    console.log(msg);
//    int mSubjectId,     //int, 科目id
//   int mVersionId, //int，版本id
//   int mFasId,        //int，分册id
//   int mChapBranId,  //int，章节id
//   int mSeriBrandId   //int，知识点id
    window.android.loadFilterData(subjectId,versionId,fasId,chapterId,knowId,stageId,organs);
});
$('.typeList span').bind('click', function () {
    $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
})