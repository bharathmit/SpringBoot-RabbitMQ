var table_treegrid = function () {

    //BEGIN JQUERY TREEGRID
    var handleTreeGrid = function () {
        $('.tree-projects').treegrid({
            expanderExpandedClass: 'fa fa-caret-down',
            expanderCollapsedClass: 'fa fa-caret-right'
        });
    }
    //END JQUERY TREEGRID

    return {
        init: function () {
            handleTreeGrid();
        }
    }
}(jQuery);



