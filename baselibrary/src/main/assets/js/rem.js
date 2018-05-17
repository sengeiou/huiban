(function(){  
    function w() {  
    var r = document.documentElement;  
    var a = r.getBoundingClientRect().width;//获取当前设备的宽度
        if (a > 750 ){
            a = 750;  
        }   
        rem = a / 7.5;
        r.style.fontSize = rem + "px";
    }  
    w();  
    window.addEventListener("resize", function() {//监听横竖屏切换  
        w()  
    }, false);  
})(); 