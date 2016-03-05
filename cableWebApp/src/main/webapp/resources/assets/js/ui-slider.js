var ui_slider = function () {
    var handleRange2DSlider = function () {
        //slider simple
        $('.slider-simple').range2DSlider({
            grid: false,
            axis: [[1, 2, 5, 7, 10], [1, 5, 10]],
            projections: true,
            showLegend: [1, 1],
            allowAxisMove: ['both'],
            className: 'slider-default range2dslider',
            printLabel: function (val) {
                this.projections && this.projections[0].find('.xdsoft_projection_value_x').text(val[1].toFixed(5));
                return val[0].toFixed(5);
            }
        }).range2DSlider('value', [[2, 4], [7, 5], [9, 6]]);

        $('.slider-simple-success').range2DSlider({
            grid: false,
            axis: [[1, 2, 5, 7, 10], [1, 5, 10]],
            projections: true,
            showLegend: [1, 1],
            allowAxisMove: ['both'],
            gridStyle: {
                color: '#07bf29'
            },
            className: 'slider-success range2dslider',

            printLabel: function (val) {
                this.projections && this.projections[0].find('.xdsoft_projection_value_x').text(val[1].toFixed(5));
                return val[0].toFixed(5);
            }
        }).range2DSlider('value', [[2, 4], [7, 5], [9, 6]]);

        $('.slider-simple-info').range2DSlider({
            grid: false,
            axis: [[1, 2, 5, 7, 10], [1, 5, 10]],
            projections: true,
            showLegend: [1, 1],
            allowAxisMove: ['both'],
            gridStyle: {
                color: '#2196f3'
            },
            className: 'slider-info range2dslider',

            printLabel: function (val) {
                this.projections && this.projections[0].find('.xdsoft_projection_value_x').text(val[1].toFixed(5));
                return val[0].toFixed(5);
            }
        }).range2DSlider('value', [[2, 4], [7, 5], [9, 6]]);

        $('.slider-simple-warning').range2DSlider({
            grid: false,
            axis: [[1, 2, 5, 7, 10], [1, 5, 10]],
            projections: true,
            showLegend: [1, 1],
            allowAxisMove: ['both'],
            gridStyle: {
                color: '#ffc107'
            },
            className: 'slider-warning range2dslider',

            printLabel: function (val) {
                this.projections && this.projections[0].find('.xdsoft_projection_value_x').text(val[1].toFixed(5));
                return val[0].toFixed(5);
            }
        }).range2DSlider('value', [[2, 4], [7, 5], [9, 6]]);

        $('.slider-simple-primary').range2DSlider({
            grid: false,
            axis: [[1, 2, 5, 7, 10], [1, 5, 10]],
            projections: true,
            showLegend: [1, 1],
            allowAxisMove: ['both'],
            gridStyle: {
                color: '#ff5722'
            },
            className: 'slider-primary range2dslider',

            printLabel: function (val) {
                this.projections && this.projections[0].find('.xdsoft_projection_value_x').text(val[1].toFixed(5));
                return val[0].toFixed(5);
            }
        }).range2DSlider('value', [[2, 4], [7, 5], [9, 6]]);
        $('.slider-simple-danger').range2DSlider({
            grid: false,
            axis: [[1, 2, 5, 7, 10], [1, 5, 10]],
            projections: true,
            showLegend: [1, 1],
            allowAxisMove: ['both'],
            gridStyle: {
                color: '#ea2e49'
            },
            className: 'slider-danger range2dslider',

            printLabel: function (val) {
                this.projections && this.projections[0].find('.xdsoft_projection_value_x').text(val[1].toFixed(5));
                return val[0].toFixed(5);
            }
        }).range2DSlider('value', [[2, 4], [7, 5], [9, 6]]);


        //slider horizontal
        $('.slider-horizontal').range2DSlider({
            className: 'slider-default xdsoft_horizontal',
            template: 'horizontal',
            value: [[1, 0], [3, 0]],
            onlyGridPoint: true,
            round: true,
            axis: [[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]]
        });
        $('.slider-horizontal-success').range2DSlider({
            className: 'slider-success xdsoft_horizontal',
            template: 'horizontal',
            value: [[4, 0], [6, 0]],
            onlyGridPoint: true,
            round: true,
            axis: [[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]]
        });
        $('.slider-horizontal-info').range2DSlider({
            className: 'slider-info xdsoft_horizontal',
            template: 'horizontal',
            value: [[7, 0], [9, 0]],
            onlyGridPoint: true,
            round: true,
            axis: [[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]]
        });
        $('.slider-horizontal-danger').range2DSlider({
            className: 'slider-danger xdsoft_horizontal',
            template: 'horizontal',
            value: [[7, 0], [9, 0]],
            onlyGridPoint: true,
            round: true,
            axis: [[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]]
        });
        $('.slider-horizontal-warning').range2DSlider({
            className: 'slider-warning xdsoft_horizontal',
            template: 'horizontal',
            value: [[4, 0], [6, 0]],
            onlyGridPoint: true,
            round: true,
            axis: [[, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]]
        });
        $('.slider-horizontal-primary').range2DSlider({
            className: 'slider-primary xdsoft_horizontal',
            template: 'horizontal',
            value: [[1, 0], [3, 0]],
            onlyGridPoint: true,
            round: true,
            axis: [[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10]]
        });


        //slider vertical
        $('.slider-vertical').range2DSlider({
            template: 'vertical',
            value: [[0, 1]],
            showRanges: [[0, 1]],
            axis: [[0, 1], [0, 10]],
            style: 'display:inline-block;',
            round: true,
            className: 'slider-default xdsoft_vertical',
            printLabel: function (val) {
                return val[1] + ' yo';
            }
        });
        $('.slider-vertical-success').range2DSlider({
            template: 'vertical',
            value: [[0, 4]],
            showRanges: [[0, 1]],
            axis: [[0, 1], [0, 10]],
            style: 'display:inline-block;',
            round: true,
            className: 'slider-success xdsoft_vertical',
            printLabel: function (val) {
                return val[1] + ' yo';
            }
        });
        $('.slider-vertical-info').range2DSlider({
            template: 'vertical',
            value: [[0, 7]],
            showRanges: [[0, 1]],
            axis: [[0, 1], [0, 10]],
            style: 'display:inline-block;',
            round: true,
            className: 'slider-info xdsoft_vertical',
            printLabel: function (val) {
                return val[1] + ' yo';
            }
        });
        $('.slider-vertical-danger').range2DSlider({
            template: 'vertical',
            value: [[0, 7]],
            showRanges: [[0, 1]],
            axis: [[0, 1], [0, 10]],
            style: 'display:inline-block;',
            round: true,
            className: 'slider-danger xdsoft_vertical',
            printLabel: function (val) {
                return val[1] + ' yo';
            }
        });
        $('.slider-vertical-primary').range2DSlider({
            template: 'vertical',
            value: [[0, 1]],
            showRanges: [[0, 1]],
            axis: [[0, 1], [0, 10]],
            style: 'display:inline-block;',
            round: true,
            className: 'slider-primary xdsoft_vertical',
            printLabel: function (val) {
                return val[1] + ' yo';
            }
        });
        $('.slider-vertical-warning').range2DSlider({
            template: 'vertical',
            value: [[0, 4]],
            showRanges: [[0, 1]],
            style: 'display:inline-block;',

            axis: [[0, 1], [0, 10]],
            round: true,
            className: 'slider-warning xdsoft_vertical',
            printLabel: function (val) {
                return val[1] + ' yo';
            }
        });


        //Harmony slider sin(x)
        var values = [];
        var ranges = [];
        var cities = [
            'Apia',
            'Ashgabat',
            'Asmara',
            'Astana',
            'Asuncion',
            'Athens',
            'Avarua',
            'Baghdad',
            'Baku',
            'Bamako',
            'Bandar',
            'Bangkok',
            'Bangui'

        ];
        for (var i = -6, k = 0; i <= 6; i++, k++) {
            values.push([i, Math.sin(i), cities[i + 6]]);
            if (i < 6)
                ranges.push([k, k + 1]);
        }
        $('.slider-harmony-x').range2DSlider({
            x: 'right',
            y: 'top',
            showLegend: [0, 0],
            showRanges: ranges,
            gridStyle: {
                color: '#07bf29'
            },
            className: 'slider-success range2dslider',
            axis: [[-6, 6], [-1.5, 1.5]],
            allowAxisMove: ['y'],
            printLabel: function (val) {
                return val[1].toFixed(2) + '<br>' + val[2];
            }
            //className:'slider-success'
        }).range2DSlider('value', values);

        //tooltip slider
        $('.slider-tooltip').range2DSlider({
            grid: false,
            tooltip: ['top', 'right', 'bottom', 'left'],
            value: [[2, 5], [4, 5], [7, 2], [9, 9]],
            alwShowTooltip: [true, true, true, false],
            allowAxisMove: ['both'],
            className: 'slider-success range2dslider',
            projections: true,
            showLegend: [1, 1]
        });


    }

    return {
        init: function () {
            handleRange2DSlider();
        }
    }
}(jQuery);