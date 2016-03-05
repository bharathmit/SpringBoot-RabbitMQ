var layout_light_style = function () {

    var handleLightStyle = function () {
        $('body').removeClass('sidebar-color-' + $.cookie('sidebar-color')).removeClass('layout-header-dark').addClass('sidebar-color-white');
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
            handleLightStyle();
        }
    }

}(jQuery);
