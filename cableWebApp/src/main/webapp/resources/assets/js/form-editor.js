var form_editor = function () {

    var handleCKeditor = function () {
        CKEDITOR.replace('content');
    }
    return {
        init: function () {
            handleCKeditor();
        }
    }
}(jQuery);

