$(function () {

    //BEGIN TOOTLIP
    $("[data-toggle='tooltip'], [data-hover='tooltip']").tooltip();
    //END TOOLTIP

    //BEGIN POPOVER
    $("[data-toggle='popover'], [data-hover='popover']").popover();
    //END POPOVER

    //BEGIN JQUERY ICHECK
    if ($('#demo-checkbox-radio').length <= 0) {

        $('input[type="checkbox"]:not(".switch")').iCheck({
            checkboxClass: 'icheckbox_square-blue ',
            increaseArea: '20%' // optional
        });
        $('input[type="radio"]:not(".switch")').iCheck({
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
        $('input.form-control-shadow[type="checkbox"]').iCheck({
            checkboxClass: 'icheckbox_square-blue form-control-shadow',
            increaseArea: '20%' // optional
        });
        $('input.form-control-shadow[type="radio"]').iCheck({
            radioClass: 'iradio_square-blue form-control-shadow border-circle',
            increaseArea: '20%' // optional
        });
    }
    //END JQUERY ICHECK

    //BEGIN PANEL TOOLS
    $(".panel").each(function (index, element) {
        var me = $(this);
        $(">.panel-heading>.tools>a", me).click(function (e) {
            if ($(this).hasClass('collapse')) {
                $(">.panel-body", me).slideUp('fast');
                $('> i', this).hide('.collapse-icon').show('.expand-icon');
            }
            else if ($(this).hasClass('icon-arrow-down')) {
                $(">.panel-body", me).slideDown('fast');
                $(this).removeClass('icon-arrow-down').addClass('icon-arrow-up');
            }
            else if ($(this).hasClass('fa-cog')) {
                //Show modal
            }
            else if ($(this).hasClass('fa-refresh')) {
                //$(">.portlet-body", me).hide();
                $(">.panel-body", me).addClass('wait');

                setTimeout(function () {
                    //$(">.portlet-body>div", me).show();
                    $(">.panel-body", me).removeClass('wait');
                }, 1000);
            }
            else if ($(this).hasClass('icon-close')) {
                me.remove();
            }
        });
    });
    //END PANEL TOOLS

    //BEGIN FILE BROWSE INPUT GROUP
    $('.btn-browse').click(function () {
        var inputfile = $(this).closest('.input-group').prev();
        inputfile.click();
        inputfile.change(function () {
            $(this).next().find('input[type=text]').val($(this).val());
        });
    });
    //END FILE BROWSE INPUT GROUP

    //BEGIN CHECKBOX FOR TAB PANE
    $('.checkall-email').on('ifChecked ifUnchecked', function (event) {
        if (event.type == 'ifChecked') {
            $(this).closest('.tab-pane').find('input[type=checkbox]').iCheck('check');
        } else {
            $(this).closest('.tab-pane').find('input[type=checkbox]').iCheck('uncheck');
        }
    });
    //END CHECKBOX FOR TAB PANE


});



