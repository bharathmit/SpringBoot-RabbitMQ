var JQueryResponsive = function () {

    var handleMenuToggle = function () {
        $('#menu-toggle.show-collapsed').live('click', function () {
            if ($(window).width() > 992) { // Error with 768
                $('#setting-sidebar-collapsed').bootstrapSwitch('toggleState');
            } else {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').animate({'left': '250px'}, 50);
                    $('.sidebar').animate({'left': 0}, 10);
                    $('.page-content').animate({'left': '250px'}, 50);
                    $('#footer').css({'padding-left': 250});
                }
                else {
                    $(this).removeClass('active').animate({'left': 0}, 50);
                    $('.sidebar').animate({'left': -250}, 50);
                    $('.page-content').animate({'left': 0}, 50);
                    $('#footer').css({'padding-left': 0});
                }
            }
        });

        $('#menu-toggle.show-hide').live('click', function () {
            $('#setting-sidebar-hide').bootstrapSwitch('toggleState');
        });

        $('.page-content,#footer').click(function () {
            if ($(window).width() <= 992) {
                $('#menu-toggle').removeClass('active').animate({'left': 0}, 50);
                $('.sidebar').animate({'left': -250}, 50);
                $('.page-content').animate({'left': 0}, 50);
                $('#footer').css({'padding-left': 0});
            }
        });
    }

    return {
        init: function () {
            handleMenuToggle();
        }
    }
}(jQuery);
