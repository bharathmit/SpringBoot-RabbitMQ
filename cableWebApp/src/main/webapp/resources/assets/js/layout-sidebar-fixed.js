var layout_sidebar_fixed = function () {

    var handleSlimscrollSidebar = function () {
        $('#sidebar-main').slimScroll({
            'width': '100%',
            'height': $(window).height() - 80,
            "wheelStep": 5
        });
    }

    return {
        init: function () {
            handleSlimscrollSidebar();
        }
    }

}(jQuery);
