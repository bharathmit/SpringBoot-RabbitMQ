var page_mail = function () {
    //Init wysihtml5
    var handlePageMail = function () {
        $('.wysihtml5').wysihtml5();
    }

    return {
        init: function () {
            handlePageMail();
        }
    }
}(jQuery);



