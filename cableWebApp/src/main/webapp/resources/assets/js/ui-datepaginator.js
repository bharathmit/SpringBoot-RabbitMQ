var ui_datepaginator = function () {
    var handleDP = function () {
        $('.paginator').datepaginator();
        $('.paginator-select').datepaginator({
            onSelectedDateChanged: function (event, date) {
                alert("Selected date: " + moment(date).format("Do, MMM YYYY"));
            }
        });
    }
    return {
        init: function () {
            handleDP();
        }
    }
}(jQuery);