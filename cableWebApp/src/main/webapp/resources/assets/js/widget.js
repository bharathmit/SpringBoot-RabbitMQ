var widget = function () {

    var handleCircliful = function () {
        $('.widget-circliful').circliful();
    }

    var handleSparkline = function () {
        $(".sparkline1").sparkline([5, 6, 7, 2, 0, 4, 2, 4, 5, 6, 7, 2, 3, 4, 2, 4, 2, 1, 3, 6, 3, 5, 2, 7, 4, 2, 1, 3, 6, 3, 5, 2, 7, 4, 6], {
            type: 'bar',
            height: '80px',
            barWidth: 8,
            zeroAxis: false,
            barColor: '#07bf29'
        });
    }

    return {
        init: function () {
            handleCircliful();
            handleSparkline();
        }
    }
}(jQuery);



