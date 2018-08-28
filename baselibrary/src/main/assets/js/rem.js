
(function(){
    function b64DecodeUnicodeJson(str) {
         var s= decodeURIComponent(atob(str).split('').map(function(c) {
             return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
         }).join(''));
         return JSON.parse(s);
     }
    function w() {
        var r = document.documentElement;
        var a = r.getBoundingClientRect().width;//获取当前设备的宽度
        console.log(a);
        console.log("------------------------");
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

