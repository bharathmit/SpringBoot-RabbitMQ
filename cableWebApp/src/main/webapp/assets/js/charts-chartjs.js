var charts_chartjs = function () {

    var handleLineChart = function () {
        var lineChartData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [
                {
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    data: [65, 59, 90, 81, 56, 55, 40]
                },
                {
                    fillColor: "rgba(151,187,205,0.5)",
                    strokeColor: "rgba(151,187,205,1)",
                    pointColor: "rgba(151,187,205,1)",
                    pointStrokeColor: "#fff",
                    data: [28, 48, 40, 19, 96, 27, 100]
                }
            ],
            responsive: true
        }

        var myLine = new Chart(document.getElementById("line-chart").getContext("2d")).Line(lineChartData);
    }

    var handleBarChart = function () {
        var barChartData = {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [
                {
                    fillColor: "rgba(220,220,220,0.5)",
                    strokeColor: "rgba(220,220,220,1)",
                    data: [65, 59, 90, 81, 56, 55, 40]
                },
                {
                    fillColor: "rgba(151,187,205,0.5)",
                    strokeColor: "rgba(151,187,205,1)",
                    data: [28, 48, 40, 19, 96, 27, 100]
                }
            ]

        }

        var myLine = new Chart(document.getElementById("bar-chart").getContext("2d")).Bar(barChartData);
    }

    var handleRadarChart = function () {
        var radarChartData = {
            labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Partying", "Running"],
            datasets: [
                {
                    fillColor: "#ff5722",
                    strokeColor: "#ea2e49",
                    pointColor: "#ff5722",
                    pointStrokeColor: "#fff",
                    data: [65, 59, 90, 81, 56, 55, 40]
                },
                {
                    fillColor: "rgba(151,187,205,0.5)",
                    strokeColor: "rgba(151,187,205,1)",
                    pointColor: "rgba(151,187,205,1)",
                    pointStrokeColor: "#fff",
                    data: [28, 48, 40, 19, 96, 27, 100]
                }
            ]

        }

        var myRadar = new Chart(document.getElementById("radar-chart").getContext("2d")).Radar(radarChartData, {
            scaleShowLabels: false,
            pointLabelFontSize: 10
        });
    }

    var handleAreaChart = function () {
        var chartData = [
            {
                value: Math.random(),
                color: "#07bf29"
            },
            {
                value: Math.random(),
                color: "#ff5722"
            },
            {
                value: Math.random(),
                color: "#2196f3"
            },
            {
                value: Math.random(),
                color: "#ffc107"
            },
            {
                value: Math.random(),
                color: "#ea2e49"
            },
            {
                value: Math.random(),
                color: "#584A5E"
            }
        ];
        var myPolarArea = new Chart(document.getElementById("polar-area-chart").getContext("2d")).PolarArea(chartData);
    }

    var handlePieChart = function () {
        var pieData = [
            {
                value: 30,
                color: "#ffc107"
            },
            {
                value: 50,
                color: "#ccc"
            },
            {
                value: 100,
                color: "#2196f3"
            }

        ];

        var myPie = new Chart(document.getElementById("pie-chart").getContext("2d")).Pie(pieData);
    }

    var handleDoughnutChart = function () {
        var doughnutData = [
            {
                value: 30,
                color: "#ea2e49"
            },
            {
                value: 50,
                color: "#2196f3"
            },
            {
                value: 100,
                color: "#ccc"
            },
            {
                value: 40,
                color: "#949FB1"
            },
            {
                value: 120,
                color: "#07bf29"
            }

        ];

        var myDoughnut = new Chart(document.getElementById("doughnut-chart").getContext("2d")).Doughnut(doughnutData);
    }

    return {
        init: function () {
            handleLineChart();
            handleBarChart();
            handleRadarChart();
            handleAreaChart();
            handlePieChart();
            handleDoughnutChart();
        }
    }
}(jQuery);

