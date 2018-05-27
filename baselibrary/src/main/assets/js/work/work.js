var mySwiper = new Swiper('.swiper-container',{
    effect : 'coverflow',
    slidesPerView: 1.4,
    centeredSlides: true,
    pagination: {
        el: '.swiper-pagination',
    },
    coverflowEffect: {
        rotate: 0,
        stretch: 0,
        depth: 0,
        modifier: 0,
        slideShadows : false
    }
})