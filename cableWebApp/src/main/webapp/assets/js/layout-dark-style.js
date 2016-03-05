var layout_dark_style = function () {

    var handleDarkStyle = function () {
        $('body').removeClass('sidebar-color-' + $.cookie('sidebar-color')).addClass('layout-header-dark');
        $('#chat-form').toggle('slide', {
            direction: 'right'
        }, 200);
        $('#chat-form > .chat-inner').slimScroll({
            "height": $(window).height(),
            'width': '280px',
            "wheelStep": 30
        });
    }

    return {
        init: function () {
            handleDarkStyle();
        }
    }

}(jQuery);
