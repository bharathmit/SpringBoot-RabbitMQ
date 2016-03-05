var ui_treeview = function () {
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    var zNodes = [
        {id: 1, pId: 0, name: "can check 1", open: true},
        {id: 11, pId: 1, name: "can check 1-1", open: true},
        {id: 111, pId: 11, name: "can check 1-1-1"},
        {id: 112, pId: 11, name: "can check 1-1-2"},
        {id: 12, pId: 1, name: "can check 1-2", open: true},
        {id: 121, pId: 12, name: "can check 1-2-1"},
        {id: 122, pId: 12, name: "can check 1-2-2"},
        {id: 2, pId: 0, name: "can check 2", checked: true, open: true},
        {id: 21, pId: 2, name: "can check 2-1"},
        {id: 22, pId: 2, name: "can check 2-2", open: true},
        {id: 221, pId: 22, name: "can check 2-2-1", checked: true},
        {id: 222, pId: 22, name: "can check 2-2-2"},
        {id: 23, pId: 2, name: "can check 2-3"}
    ];


//BEGIN BASIC
    var settingBasic = {
        data: {
            key: {
                title: "title"
            },
            simpleData: {
                enable: true
            }
        }
    };

    var zNodesBasic = [
        {id: 1, pId: 0, name: "pNode 1", title: "", open: true},
        {id: 11, pId: 1, name: "pNode 11", title: ""},
        {id: 111, pId: 11, name: "leaf node  111", title: "", isHidden: true},
        {id: 112, pId: 11, name: "leaf node  112", title: ""},
        {id: 113, pId: 11, name: "leaf node  113", title: ""},
        {id: 12, pId: 1, name: "pNode 12", title: "", isHidden: true},
        {id: 121, pId: 12, name: "leaf node  121", title: ""},
        {id: 122, pId: 12, name: "leaf node  122", title: "", isHidden: true},
        {id: 123, pId: 12, name: "leaf node  123", title: ""},
        {id: 2, pId: 0, name: "pNode 2", title: "", open: true},
        {id: 21, pId: 2, name: "pNode 21", title: "", open: true},
        {id: 211, pId: 21, name: "leaf node  211", title: ""},
        {id: 212, pId: 21, name: "leaf node  212", title: ""},
        {id: 213, pId: 21, name: "leaf node  213", title: ""},
        {id: 22, pId: 2, name: "pNode 22", title: ""},
        {id: 221, pId: 22, name: "leaf node  221", title: ""},
        {id: 222, pId: 22, name: "leaf node  222", title: ""},
        {id: 223, pId: 22, name: "leaf node  223", title: ""},
        {id: 3, pId: 0, name: "pNode 3", title: "", open: true},
        {id: 31, pId: 3, name: "pNode 31", title: ""},
        {id: 311, pId: 31, name: "leaf node 311", title: "", isHidden: true},
        {id: 312, pId: 31, name: "leaf node  312", title: ""},
        {id: 313, pId: 31, name: "leaf node  313", title: ""},
        {id: 32, pId: 4, name: "pNode 32", title: "", isHidden: true},
        {id: 321, pId: 32, name: "leaf node  321", title: ""},
        {id: 322, pId: 32, name: "leaf node  322", title: "", isHidden: true},
        {id: 323, pId: 32, name: "leaf node  323", title: ""},
        {id: 4, pId: 0, name: "pNode 4", title: "", open: true},
        {id: 41, pId: 4, name: "pNode 41", title: "", isHidden: true},
        {id: 411, pId: 41, name: "leaf node  411", title: ""},
        {id: 412, pId: 41, name: "leaf node  412", title: ""},
        {id: 413, pId: 41, name: "leaf node  413", title: ""},
        {id: 42, pId: 4, name: "pNode 42", title: ""},
        {id: 421, pId: 42, name: "leaf node  421", title: ""},
        {id: 422, pId: 42, name: "leaf node 422", title: ""},
        {id: 423, pId: 42, name: "leaf node 423", title: ""}
    ];
//END BASIC

//BEGIN DRANG TREE
    var settingDrag = {
        edit: {
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: beforeDrag,
            beforeDrop: beforeDrop
        }
    };

    var zNodesDrag = [
        {id: 1, pId: 0, name: "can drag 1", open: true},
        {id: 11, pId: 1, name: "can drag 1-1"},
        {id: 12, pId: 1, name: "can drag 1-2", open: true},
        {id: 121, pId: 12, name: "can drag 1-2-1"},
        {id: 122, pId: 12, name: "can drag 1-2-2"},
        {id: 123, pId: 12, name: "can drag 1-2-3"},
        {id: 13, pId: 1, name: "can't drag 1-3", open: false, drag: false},
        {id: 131, pId: 13, name: "can't drag 1-3-1", drag: false},
        {id: 132, pId: 13, name: "can't drag 1-3-2", drag: false},
        {id: 133, pId: 13, name: "can drag 1-3-3"},
        {id: 2, pId: 0, name: "can drag 2", open: true},
        {id: 21, pId: 2, name: "can drag 2-1"},
        {id: 22, pId: 2, name: "can't drop 2-2", open: true, drop: false},
        {id: 221, pId: 22, name: "can drag 2-2-1"},
        {id: 222, pId: 22, name: "can drag 2-2-2"},
        {id: 223, pId: 22, name: "can drag 2-2-3"},
        {id: 23, pId: 2, name: "can drag 2-3"}
    ];
//END DRANG TREE
//BEGIN RADIO
    var settingRadio = {
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "level"
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };

    var zNodesRadio = [
        {id: 1, pId: 0, name: "can check 1", open: true},
        {id: 11, pId: 1, name: "can check 1-1", open: true},
        {id: 111, pId: 11, name: "can check 1-1-1"},
        {id: 112, pId: 11, name: "can check 1-1-2"},
        {id: 12, pId: 1, name: "can check 1-2", open: true},
        {id: 121, pId: 12, name: "can check 1-2-1"},
        {id: 122, pId: 12, name: "can check 1-2-2"},
        {id: 2, pId: 0, name: "can check 2", checked: true, open: true},
        {id: 21, pId: 2, name: "can check 2-1"},
        {id: 22, pId: 2, name: "can check 2-2", open: true},
        {id: 221, pId: 22, name: "can check 2-2-1", checked: true},
        {id: 222, pId: 22, name: "can check 2-2-2"},
        {id: 23, pId: 2, name: "can check 2-3"}
    ];

//END RADIO

    function beforeDrag(treeId, treeNodes) {
        for (var i = 0, l = treeNodes.length; i < l; i++) {
            if (treeNodes[i].drag === false) {
                return false;
            }
        }
        return true;
    }

    function beforeDrop(treeId, treeNodes, targetNode, moveType) {
        return targetNode ? targetNode.drop !== false : true;
    }


//BEGIN EDIT
    var settingEdit = {
        edit: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: beforeDrag
        }
    };

    var zNodesEdit = [
        {id: 1, pId: 0, name: "parent node 1", open: true},
        {id: 11, pId: 1, name: "leaf node 1-1"},
        {id: 12, pId: 1, name: "leaf node 1-2"},
        {id: 13, pId: 1, name: "leaf node 1-3"},
        {id: 2, pId: 0, name: "parent node 2", open: true},
        {id: 21, pId: 2, name: "leaf node 2-1"},
        {id: 22, pId: 2, name: "leaf node 2-2"},
        {id: 23, pId: 2, name: "leaf node 2-3"},
        {id: 3, pId: 0, name: "parent node 3", open: true},
        {id: 31, pId: 3, name: "leaf node 3-1"},
        {id: 32, pId: 3, name: "leaf node 3-2"},
        {id: 33, pId: 3, name: "leaf node 3-3"}
    ];

//BEGIN EDIT


//BEGIN SEARCH DATA
    var settingSearch = {
        data: {
            key: {
                title: "t"
            },
            simpleData: {
                enable: true
            }
        },
        view: {
            fontCss: getFontCss
        }
    };

    var zNodesSearch = [
        {id: 1, pId: 0, name: "search node demo 1", t: "id=1", open: true},
        {id: 11, pId: 1, name: "can search 'name'", t: "id=11"},
        {id: 12, pId: 1, name: "can search 'level'", t: "id=12"},
        {id: 13, pId: 1, name: "can search 'id'", t: "id=13"},
        {id: 14, pId: 1, name: "can search other attr", t: "id=14"},
        {id: 2, pId: 0, name: "search node demo 2", t: "id=2", open: true},
        {id: 21, pId: 2, name: "can search single node", t: "id=21"},
        {id: 22, pId: 2, name: "can search nodes array", t: "id=22"},
        {id: 23, pId: 2, name: "search me", t: "id=23"},
        {id: 3, pId: 0, name: "search node demo 3", t: "id=3", open: true},
        {id: 31, pId: 3, name: "My id is : 31", t: "id=31"},
        {id: 32, pId: 31, name: "My id is : 32", t: "id=32"},
        {id: 33, pId: 32, name: "My id is : 33", t: "id=33"}
    ];

    function focusKey(e) {
        if (key.hasClass("empty")) {
            key.removeClass("empty");
        }
    }

    function blurKey(e) {
        if (key.get(0).value === "") {
            key.addClass("empty");
        }
    }

    var lastValue = "", nodeList = [], fontCss = {};

    function clickRadio(e) {
        lastValue = "";
        searchNode(e);
    }

    function searchNode(e) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");

        var value = $.trim(key.get(0).value);
        var keyType = "name";

        if (key.hasClass("empty")) {
            value = "";
        }
        if (lastValue === value) return;
        lastValue = value;
        if (value === "") return;
        updateNodes(false);


        nodeList = zTree.getNodesByParamFuzzy(keyType, value);
        updateNodes(true);

    }

    function updateNodes(highlight) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        for (var i = 0, l = nodeList.length; i < l; i++) {
            nodeList[i].highlight = highlight;
            zTree.updateNode(nodeList[i]);
        }
    }

    function getFontCss(treeId, treeNode) {
        return (!!treeNode.highlight) ? {color: "#A60000", "font-weight": "bold"} : {
            color: "#333",
            "font-weight": "normal"
        };
    }

    function filter(node) {
        return !node.isParent && node.isFirstNode;
    }

//END SEARCH DATA


    var handleZTree = function () {

        $.fn.zTree.init($("#demoBasic"), settingBasic, zNodesBasic);
        $.fn.zTree.init($("#demoCheck"), setting, zNodes);

        $.fn.zTree.init($("#demoRadio"), settingRadio, zNodesRadio);

        $.fn.zTree.init($("#demoDrag"), settingDrag, zNodesDrag);

        $.fn.zTree.init($("#demoEdit"), settingEdit, zNodesEdit);
        $.fn.zTree.init($("#treeDemo"), settingSearch, zNodesSearch);
        key = $("#key");
        key.bind("focus", focusKey)
            .bind("blur", blurKey)
            .bind("propertychange", searchNode)
            .bind("input", searchNode);


    }
    return {
        init: function () {
            handleZTree();
        }
    }
}(jQuery);