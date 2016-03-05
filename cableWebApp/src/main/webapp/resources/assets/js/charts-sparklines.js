var charts_sparklines = function () {
    /** handle all the example sparklines on the index page **/
    var handleDocSparklines = function () {

        // Bar + line composite charts
        $('#compositebar').sparkline('html', {type: 'bar', barColor: '#aaf'});
        $('#compositebar').sparkline([4, 1, 5, 7, 9, 9, 8, 7, 6, 6, 4, 7, 8, 4, 3, 2, 2, 5, 6, 7],
            {composite: true, fillColor: false, lineColor: 'red'});


        // Line charts taking their values from the tag
        $('.sparkline').sparkline();

        // Larger line charts for the docs
        $('.largeline').sparkline('html',
            {type: 'line', height: '2.5em', width: '4em'});

        // Customized line chart
        $('#linecustom').sparkline('html',
            {
                height: '1.5em', width: '8em', lineColor: '#f00', fillColor: '#ffa',
                minSpotColor: false, maxSpotColor: false, spotColor: '#77f', spotRadius: 3
            });

        // Bar charts using inline values
        $('.sparkbar').sparkline('html', {type: 'bar'});

        $('.barformat').sparkline([1, 3, 5, 3, 8], {
            type: 'bar',
            tooltipFormat: '{{value:levels}} - {{value}}',
            tooltipValueLookups: {
                levels: $.range_map({':2': 'Low', '3:6': 'Medium', '7:': 'High'})
            }
        });

        // Tri-state charts using inline values
        $('.sparktristate').sparkline('html', {type: 'tristate'});
        $('.sparktristatecols').sparkline('html',
            {type: 'tristate', colorMap: {'-2': '#fa7', '2': '#44f'}});

        // Composite line charts, the second using values supplied via javascript
        $('#compositeline').sparkline('html', {fillColor: false, changeRangeMin: 0, chartRangeMax: 10});
        $('#compositeline').sparkline([4, 1, 5, 7, 9, 9, 8, 7, 6, 6, 4, 7, 8, 4, 3, 2, 2, 5, 6, 7],
            {composite: true, fillColor: false, lineColor: 'red', changeRangeMin: 0, chartRangeMax: 10});

        // Line charts with normal range marker
        $('#normalline').sparkline('html',
            {fillColor: false, normalRangeMin: -1, normalRangeMax: 8});
        $('#normalExample').sparkline('html',
            {fillColor: false, normalRangeMin: 80, normalRangeMax: 95, normalRangeColor: '#4f4'});

        // Discrete charts
        $('.discrete1').sparkline('html',
            {type: 'discrete', lineColor: 'blue', xwidth: 18});
        $('#discrete2').sparkline('html',
            {type: 'discrete', lineColor: 'blue', thresholdColor: 'red', thresholdValue: 4});

        // Bullet charts
        $('.sparkbullet').sparkline('html', {type: 'bullet'});

        // Pie charts
        $('.sparkpie').sparkline('html', {type: 'pie', height: '1.0em'});

        // Box plots
        $('.sparkboxplot').sparkline('html', {type: 'box'});
        $('.sparkboxplotraw').sparkline([1, 3, 5, 8, 10, 15, 18],
            {type: 'box', raw: true, showOutliers: true, target: 6});

        // Box plot with specific field order
        $('.boxfieldorder').sparkline('html', {
            type: 'box',
            tooltipFormatFieldlist: ['med', 'lq', 'uq'],
            tooltipFormatFieldlistKey: 'field'
        });

        // click event demo sparkline
        $('.clickdemo').sparkline();
        $('.clickdemo').bind('sparklineClick', function (ev) {
            var sparkline = ev.sparklines[0],
                region = sparkline.getCurrentRegionFields();
            value = region.y;
            alert("Clicked on x=" + region.x + " y=" + region.y);
        });

        // mouseover event demo sparkline
        $('.mouseoverdemo').sparkline();
        $('.mouseoverdemo').bind('sparklineRegionChange', function (ev) {
            var sparkline = ev.sparklines[0],
                region = sparkline.getCurrentRegionFields();
            value = region.y;
            $('.mouseoverregion').text("x=" + region.x + " y=" + region.y);
        }).bind('mouseleave', function () {
            $('.mouseoverregion').text('');
        });
        ;
        //big demo
        $("#bigpie").sparkline([2, 1, 8, 1], {type: 'pie', height: '200px'});

        $("#bigline").sparkline([5, 4, 4, 7, 6, 9, 5, 8, 2, 1, 4, 2, 0, 3, 6, 2, 6, 4, 6, 7, 4, 2, 1, 5, 7, 3], {
            type: 'line',
            width: '100%',
            height: '200px',
            lineColor: '#ea2e49',
            fillColor: 'rgba(0, 0, 0, 0.02)',
            highlightSpotColor: '#ea2e49',
            highlightLineColor: '#ea2e49',
            spotRadius: 4
        });
        $("#bigline").sparkline([4, 3, 0, 6, 6, 8, 5, 9, 3, 8, 7, 8, 7, 6, 6, 4, 5, 6, 3, 3, 4, 3, 3, 5, 5, 6], {
            type: 'line',
            width: '100%',
            height: '200px',
            lineColor: '#07bf29',
            fillColor: false,
            highlightSpotColor: '#07bf29',
            highlightLineColor: '#07bf29',
            composite: true,
            spotRadius: 4
        });
        $('#bigcomposite').sparkline([5, 4, 5, 7, 2, 3, 5, 3, 7, 9, 7, 6, -9, -5, -8, -2, 6, 4, 6, 7, 6, 4, 2, 1, 4, 6, 2], {
            type: 'bar',
            barColor: '#2196f3',
            negBarColor: '#ea2e49',
            height: '200px',
            width: '100%',
            barWidth: 10,
            barSpacing: 5
        });
        $('#bigcomposite').sparkline([4, 1, 5, 7, 9, 9, 8, 7, -6, -6, -4, 7, 8, 4, 3, 2, 2, 5, 6, 7, 8], {
            composite: true, fillColor: false, lineColor: 'red', height: '200px', width: '100%'
        });

        //event click
        $("#eventclick").sparkline([5, 6, 7, 9, 5, 3, 2, 4, 6, 7, 5, 6, 7, 9, 5, 6, 7, 5, 6, 7, 9, 5, 3, 2, 7, 2, 2, 4, 6, 7, 9], {
            type: 'line',
            height: '90px',
            width: '100%'
        });
        $('#eventclick').bind('sparklineClick', function (ev) {
            var sparkline = ev.sparklines[0],
                region = sparkline.getCurrentRegionFields();
            alert("Clicked on x=" + region.x + " y=" + region.y);
        });
    }

    var handleMouseSpeedDemo = function () {
        var mrefreshinterval = 500; // update display every 500ms
        var lastmousex = -1;
        var lastmousey = -1;
        var lastmousetime;
        var mousetravel = 0;
        var mpoints = [];
        var mpoints_max = 30;
        $('html').mousemove(function (e) {
            var mousex = e.pageX;
            var mousey = e.pageY;
            if (lastmousex > -1) {
                mousetravel += Math.max(Math.abs(mousex - lastmousex), Math.abs(mousey - lastmousey));
            }
            lastmousex = mousex;
            lastmousey = mousey;
        });
        var mhandle = function () {
            var md = new Date();
            var timenow = md.getTime();
            if (lastmousetime && lastmousetime != timenow) {
                var pps = Math.round(mousetravel / (timenow - lastmousetime) * 1000);
                mpoints.push(pps);
                if (mpoints.length > mpoints_max)
                    mpoints.splice(0, 1);
                mousetravel = 0;
                $('#mousespeed').sparkline(mpoints, {width: mpoints.length * 2, tooltipSuffix: ' pixels per second'});
            }
            lastmousetime = timenow;
            setTimeout(mhandle, mrefreshinterval);
        }
        // We could use setInterval instead, but I prefer to do it this way
        setTimeout(mhandle, mrefreshinterval);
    }

    return {
        init: function () {
            handleDocSparklines();
            handleMouseSpeedDemo();
        }
    }
}(jQuery);