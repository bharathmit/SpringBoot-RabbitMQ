var ui_panels = function () {

    var handlePanelScroll = function () {
        $('.panel-scroll').slimScroll({
            "width": '100%',
            "height": '140px',
            "wheelStep": 5
        });
    }

    return {
        init: function () {
            handlePanelScroll();
        }
    }

}(jQuery);

