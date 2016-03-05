$(function () {

    //BEGIN PAGE LOADER
    $(window).load(function() {
        $("#page-loader").delay(100).fadeOut("slow");
    });
    //END PAGE LOADER

    //jQuery to collapse the navbar on scroll
    $(window).bind('scroll load', function() {
        if($(window).width() > 911) {
            if ($(".navbar").offset().top > 200) {
                $(".navbar-fixed-top").addClass("top-nav-collapse");
            } else {
                $(".navbar-fixed-top").removeClass("top-nav-collapse");
            }
        }
        else{
            $(".navbar-fixed-top").addClass("top-nav-collapse");
        }
    });

    $('.page-scroll a').bind('click', function(event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top
        }, 1000, 'swing');
        event.preventDefault();
    });

    //BEGIN JQUERY DATA HOVER TOOLTIP
    $("[data-hover='tooltip']").tooltip();
    //END JQUERY DATA HOVER TOOLTIP

    //BEGIN BACK TO TOP
    $(window).scroll(function(){
        if ($(this).scrollTop() < 200) {
            $('#totop') .fadeOut();
        } else {
            $('#totop') .fadeIn();
        }
    });
    $('#totop').on('click', function(){
        $('html, body').animate({scrollTop:0}, 'fast');
        return false;
    });
    //END BACK TO TOP

    //BEGIN OWL CAROUSEL OUR CLIENT
    $("#owl-our-clients").owlCarousel({
        autoPlay: 4000,
        items : 6,
        itemsDesktop : [1199,2],
        itemsDesktopSmall : [979,1],
        pagination: false
    });$
    ("#owl-our-clients-say").owlCarousel({
        autoPlay: 10000,
        items : 3,
        itemsDesktop : [1199,2],
        itemsDesktopSmall : [979,1],
        pagination: false,
        autoplay: false
    });
    //END OWL CAROUSEL OUR CLIENT

    //BEGIN THEME SETTING
    $('#theme-setting > a.btn-theme-setting').click(function(){
        if($('#theme-setting').css('right') < '0'){
            $('#theme-setting').css('right', '0');
        } else {
            $('#theme-setting').css('right', '-250px');
        }
    });

    // Begin Change Theme Color
    var list_color = $('#theme-setting > .theme-setting-content > ul.color-skins > li');

    var setTheme = function (color) {
        $('#color-skins').attr('href', 'assets/css/themes/'+ color + '.css');
    }
    list_color.on('click', function() {
        list_color.removeClass("active");
        $(this).addClass("active");
        setTheme($(this).attr('data-color'));
    });
    //END THEME SETTING

});

