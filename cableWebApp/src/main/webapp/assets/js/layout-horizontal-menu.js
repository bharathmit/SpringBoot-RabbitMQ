var layout_horizontal_menu = function () {

    var handleHorizontalMenu = function () {
        //if you don't use jQuery cookie, you don't need to use this js
        $('.logo-wrapper').removeClass('in-sidebar');
        $('#footer').css('padding-left', '0px');
    }

    return {
        init: function () {
            handleHorizontalMenu();
        }
    }

}(jQuery);
