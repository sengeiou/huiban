// (function(){  
//     function w() {  
//     var r = document.documentElement;  
//     var a = r.getBoundingClientRect().width;//获取当前设备的宽度
//         if (a > 750 ){
//             a = 750;  
//         }   
//         rem = a / 7.5;
//         r.style.fontSize = rem + "px";
//     }  
//     w();  
//     window.addEventListener("resize", function() {//监听横竖屏切换  
//         w()  
//     }, false);
// })(); 
(function (win, doc) {
    if (!win.addEventListener) return;
    var html = document.documentElement; 
    function setFont() {
       var cliWidth = html.clientWidth;
       html.style.fontSize = 100 * (cliWidth / 750) + 'px';
    }
    setFont();
    win.addEventListener('resize', setFont, false);
    doc.addEventListener('DOMContentLoaded', setFont, false)
 })(window, document);