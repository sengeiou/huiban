var type = 1, subjectId=-1, versionId=-1, fasId=-1, chapterId=-1, knowId=-1, stageId=-1;
function subject (data) {    
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
            $('.lists .list').eq(3).hide();          
            type = 2;
            for (var j=0; j<data.length; j++) {
                if(data[j].name === $(this).text()){
                    if (data[j].subjectId == undefined) {
                        subjectId =-1;
                    }else {
                        subjectId = data[j].subjectId;
                    }                        
                }
            }
            $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});            
            var msg = JSON.stringify({"subjectId":subjectId});
            window.android.dealWithJson("getFilterVersionL",msg); 
        });
    } else if (type == 2) {
        $('.typeList').eq(2).html('');
        for (var i=0;i<data.length;i++) {
            var nowSub = $('.typeList').eq(2).html();
            $('.typeList').eq(2).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(2) span').bind('click', function () {
                $('.lists .list').eq(2).show();
                $('.lists .list').eq(3).hide();  
                type = 3;
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].versionId == undefined) {
                            versionId = -1;
                        }else {
                            versionId = data[j].versionId;
                        }                        
                    }
                }
                var msg = JSON.stringify({"subjectId": subjectId,"versionId": versionId})
                window.android.dealWithJson("getFilterFasL",msg);              
                $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});      
            });
        }
        
    } else if (type == 3) {
        $('.typeList').eq(3).html('');
        for (var i=0;i<data.length;i++) {
            var nowSub = $('.typeList').eq(3).html();
            $('.typeList').eq(3).html(nowSub+`
                <span>${data[i].name}</span>
            `);
            $('.typeList:eq(3) span').bind('click', function () {
                $('.lists .list').eq(3).show();
                type = 4;
                for (var j=0; j<data.length; j++) {
                    if(data[j].name === $(this).text()){
                        if (data[j].fasId == undefined) {
                            fasId = -1;
                        }else {
                            fasId = data[j].fasId;
                        }                        
                    }
                }
                var msg = JSON.stringify({"subjectId": subjectId,"versionId": versionId,"fasId":fasId});
                window.android.dealWithJson("getFilterChapterL",msg);   
            });
        }
    } else if (type == 4) {
        if(data==undefined||data==null||data=='null'){
            return;
        }
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
    if (type != 3) {
         var data = JSON.parse(data);
    }else {
        var data = data;
    }
    
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
                    if (data[j].subjectId == undefined) {
                        subjectId = -1;
                    }else {
                        subjectId = data[j].subjectId;
                    }                        
                }
            }
            $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});            
            var msg = JSON.stringify({"subjectId":subjectId});
            window.android.dealWithJson("getFilterVersionL",msg);
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
                            stageId = -1;
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
        console.log(data);
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
//初始化章节
function setSubject () {
    type = 1;
    $('.lists').html(`
        <div class="list">
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
            <p class="title">章节</p>
            <ul class="navs"></ul>
        </div>
    `);
}
// 初始化知识点
function setKnows () {
    type = 1;
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
    window.android.loadFilterData(subjectId,versionId,fasId,chapterId,knowId);
});
$('.typeList span').bind('click', function () {
    $(this).css({'background': '#e0f8f6', 'color': '#00c7b5'}).siblings().css({'background': '#fff', 'color': '#9b9b9b'});
})