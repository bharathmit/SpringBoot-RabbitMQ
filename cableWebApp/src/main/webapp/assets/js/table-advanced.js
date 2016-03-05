var table_advanced = function () {

    var handleDatatable = function () {
        var spinner = $(".spinner").spinner();
        var table = $('#table_id').dataTable({
            "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
            "order": [[1, "asc"]],
            "aoColumnDefs": [{
                'bSortable': false,
                'aTargets': [0]
            }]
        });

        var tableTools = new $.fn.dataTable.TableTools(table, {
            "sSwfPath": "../assets/vendors/DataTables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
            "buttons": [
                "copy",
                "csv",
                "xls",
                "pdf",
                {"type": "print", "buttonText": "Print me!"}
            ]
        });
        /* $(".DTTT_container").css("float", "right"); */
        $(".DTTT_container").css("display", "none");
    }
    return {
        init: function () {
            handleDatatable();
        }
    }
}(jQuery);


