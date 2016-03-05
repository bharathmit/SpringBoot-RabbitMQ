var Layout = function () {
    //BEGIN MENU MAIN
    var handleSidebarMain = function () {
        $('#sidebar-main').metisMenu();
    }
    //END MENU MAIN

    var handleGaugeChart = function () {
        var gauge_chart = new JustGage({
            id: "gauge-chart",
            value: getRandomInt(0, 100),
            min: 0,
            max: 100,
            title: "Last Week",
            showInnerShadow: false,
            levelColorsGradient: false
        });
    }

    //BEGIN SLIMSCROLL TOPBAR
    var handleSlimscrollTopbar = function () {
        $('.dropdown-slimscroll').slimScroll({
            "width": '100%',
            "height": '250px',
            "wheelStep": 25
        });
    }
    //END SLIMSCROLL TOPBAR

    //BEGIN SIDEBAR SECOND TO DO'S LIST
    var handleSlimscrollTodo = function () {
        $('.list-todo-scroll').slimScroll({
            "width": '100%',
            "height": '250px',
            "wheelStep": 25
        });
    }
    //END SIDEBAR SECOND TO DO'S LIST

    //BEGIN TEMPLATE SETTING
    var handleTemplateSetting = function () {
        $('a.btn-template-setting').click(function () {
            if ($('#template-setting').css('right') < '0') {
                $('#template-setting').css('right', '0');
            } else {
                $('#template-setting').css('right', '-255px');
            }
        });

        $('ul.sidebar-color > li').click(function () {
            var color = $(this).attr('data-style');
            $('ul.sidebar-color > li').removeClass('active');
            $(this).addClass('active');
            switch (color) {
                case 'default':
                    $('body').removeClass(function (index, css) {
                        return (css.match(/(^|\s)sidebar-color-\S+/g) || []).join(' ');
                    });
                    break;
                case 'orange':
                    $('body').removeClass(function (index, css) {
                        return (css.match(/(^|\s)sidebar-color-\S+/g) || []).join(' ');
                    }).addClass('sidebar-color-orange');
                    break;
                case 'green':
                    $('body').removeClass(function (index, css) {
                        return (css.match(/(^|\s)sidebar-color-\S+/g) || []).join(' ');
                    }).addClass('sidebar-color-green');
                    break;
                case 'white':
                    $('body').removeClass(function (index, css) {
                        return (css.match(/(^|\s)sidebar-color-\S+/g) || []).join(' ');
                    }).addClass('sidebar-color-white');
                    break;
                case 'blue':
                    $('body').removeClass(function (index, css) {
                        return (css.match(/(^|\s)sidebar-color-\S+/g) || []).join(' ');
                    }).addClass('sidebar-color-blue');
                    break;
                case 'grey':
                    $('body').removeClass(function (index, css) {
                        return (css.match(/(^|\s)sidebar-color-\S+/g) || []).join(' ');
                    }).addClass('sidebar-color-grey');
                    break;
            }
        });

        $('#setting-sidebar-collapsed').on('switch-change', function () {
            $('body').toggleClass('layout-sidebar-collapsed');
            if ($('body').hasClass('layout-sidebar-fixed')) {
                alert('Please go on "Layout sidebar fixed & collapsed" menu');
            }
        });

        $('#setting-sidebar-fixed').on('switch-change', function () {
            if ($('body').hasClass('layout-sidebar-collapsed')) {
                alert('Please go on "Layout sidebar fixed & collapsed" menu to use this option');
                //return false;
            } else if ($('.fluid').hasClass('container')) {
                alert('Unfortunately, you have to edit litte to use this option');
            } else {
                $('body').toggleClass('layout-sidebar-fixed');
                if ($("#sidebar-main").parent().hasClass('slimScrollDiv') || $('body').hasClass('layout-sidebar-collapsed')) {
                    destroySlimscroll('sidebar-main');
                } else {
                    handleSidebarFixed();
                }
            }
        });

        $('#setting-sidebar-hide').on('switch-change', function () {
            $('body').toggleClass('layout-sidebar-hide');
        });


        $('#setting-header-fixed').on('switch-change', function () {
            $('body').toggleClass('layout-header-fixed');
        });

        $('#setting-header-dark').on('switch-change', function () {
            $('body').toggleClass('layout-header-dark');
        });

        $('#setting-horizontal-menu').on('switch-change', function () {
            $('body').toggleClass('layout-full-width');
            $('.logo-wrapper').removeClass('in-sidebar');
        });

        $('#setting-layout-boxed').on('switch-change', function () {
            if ($('body').hasClass('layout-sidebar-fixed')) {
                alert('Unfortunately, you have to edit litte to use this option');
            } else {
                $('.fluid').toggleClass('container');
            }
        });

        $('#setting-logo-status').on('switch-change', function () {
            $('#topbar .logo-wrapper').toggleClass('in-sidebar');
        });

        $('#setting-toggle-status').on('switch-change', function () {
            $('#menu-toggle').toggleClass('show-collapsed');
            $('#menu-toggle').toggleClass('show-hide');
        });

        $('#setting-theme-chat').on('switch-change', function () {
            $('.chat-form-wrapper').toggleClass('light');
        });

        $("select#font-select")
            .change(function () {
                var value;
                var $font_link = $('#font-layout');
                $("select#font-select option:selected").each(function () {
                    value = $(this).val();
                });

                switch (value) {
                    case 'open-sans':
                        handleRemoveClassFont();
                        break;
                    case 'roboto':
                        handleRemoveClassFont();
                        $font_link.attr('href', 'http://fonts.googleapis.com/css?family=Roboto');
                        $('body').addClass('font-roboto');
                        break;
                    case 'lato':
                        handleRemoveClassFont();
                        $font_link.attr('href', 'http://fonts.googleapis.com/css?family=Lato');
                        $('body').addClass('font-lato');
                        break;
                    case 'source-sans-pro':
                        handleRemoveClassFont();
                        $font_link.attr('href', 'http://fonts.googleapis.com/css?family=Source+Sans+Pro');
                        $('body').addClass('font-source-sans-pro');
                        break;
                    case 'helvetica':
                        handleRemoveClassFont();
                        $('body').addClass('font-helvetica');
                        break;
                    case 'lora':
                        handleRemoveClassFont();
                        $font_link.attr('href', 'http://fonts.googleapis.com/css?family=Lora');
                        $('body').addClass('font-lora');
                        break;

                }
            });

        //set cookie when click save
        $('#save-setting').live('click', function () {
            var cookie_sidebar_color = $('.sidebar-color li.active').attr('data-style');
            var cookie_font = $('select#font-select').val();
            var cookie_array = [];
            if ($('#setting-header-fixed > div').hasClass('switch-on')) {
                cookie_array.push('layout-header-fixed');
            }
            if ($('#setting-sidebar-collapsed > div').hasClass('switch-on')) {
                cookie_array.push('layout-sidebar-collapsed');
            }
            if ($('#setting-sidebar-hide > div').hasClass('switch-on')) {
                cookie_array.push('layout-sidebar-hide');
            }
            if ($('#setting-sidebar-fixed > div').hasClass('switch-on')) {
                cookie_array.push('layout-sidebar-fixed');
            }
            if ($('#setting-toggle-status > div').hasClass('switch-on')) {
                cookie_array.push('layout-toggle-status');
            }
            if ($('#setting-header-dark > div').hasClass('switch-on')) {
                cookie_array.push('layout-header-dark');
            }
            if ($('#setting-logo-status > div').hasClass('switch-on')) {
                cookie_array.push('layout-logo-status');
            }
            if ($('#setting-horizontal-menu > div').hasClass('switch-on')) {
                cookie_array.push('layout-full-width');
            }
            if ($('#setting-footer-light > div').hasClass('switch-on')) {
                cookie_array.push('layout-footer-light');
            }

            $.cookie('sidebar-color', cookie_sidebar_color);
            $.cookie('font-layout', cookie_font);
            $.cookie('setting', cookie_array.join(' '));
        });
        //load cookie on document ready
        if ($.cookie('setting')) {
            var cookie_load_array = $.cookie('setting').split(' ');
            if ($.inArray('layout-header-fixed', cookie_load_array) !== -1) {
                $('#setting-header-fixed').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-sidebar-collapsed', cookie_load_array) !== -1) {
                $('#setting-sidebar-collapsed').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-sidebar-fixed', cookie_load_array) !== -1) {
                $('#setting-sidebar-fixed').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-sidebar-hide', cookie_load_array) !== -1) {
                $('#setting-sidebar-hide').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-header-dark', cookie_load_array) !== -1) {
                $('#setting-header-dark').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-toggle-status', cookie_load_array) !== -1) {
                $('#setting-toggle-status').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-logo-status', cookie_load_array) !== -1) {
                $('#setting-logo-status').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-full-width', cookie_load_array) !== -1) {
                $('#setting-horizontal-menu').bootstrapSwitch('toggleState');
            }
            if ($.inArray('layout-footer-light', cookie_load_array) !== -1) {
                $('#setting-footer-light').bootstrapSwitch('toggleState');
            }
        }

        if ($.cookie('sidebar-color')) {
            $('body').addClass('sidebar-color-' + $.cookie('sidebar-color'));
            $('.sidebar-color li').removeClass('active');
            $('.sidebar-color li.' + $.cookie('sidebar-color')).addClass('active');
        }

        if ($.cookie('font-layout')) {
            $('select#font-select').val($.cookie('font-layout'));
            setFont($.cookie('font-layout'));
        }

        function setFont(value) {
            var $font_layout = $('#font-layout');
            switch (value) {
                case 'open-sans':
                    handleRemoveClassFont();
                    break;
                case 'roboto':
                    handleRemoveClassFont();
                    $font_layout.attr('href', 'http://fonts.googleapis.com/css?family=Roboto');
                    $('body').addClass('font-roboto');
                    break;
                case 'lato':
                    handleRemoveClassFont();
                    $font_layout.attr('href', 'http://fonts.googleapis.com/css?family=Lato');
                    $('body').addClass('font-lato');
                    break;
                case 'source-sans-pro':
                    handleRemoveClassFont();
                    $font_layout.attr('href', 'http://fonts.googleapis.com/css?family=Source+Sans+Pro');
                    $('body').addClass('font-source-sans-pro');
                    break;
                case 'helvetica':
                    handleRemoveClassFont();
                    $('body').addClass('font-helvetica');
                    break;
                case 'lora':
                    handleRemoveClassFont();
                    $font_layout.attr('href', 'http://fonts.googleapis.com/css?family=Lora');
                    $('body').addClass('font-lora');
                    break;
            }
        }
    }
    //END TEMPLATE SETTING

    // BEGIN FORM CHAT
    var handleFormChat = function () {

        //Open chat form
        $('.btn-chat').click(function () {
            if ($('#chat-box').is(':visible')) {
                $('#chat-form').toggle('slide', {
                    direction: 'right'
                }, 200);
                $('#chat-form').slimScroll();
                $('#chat-box').hide();
            } else {
                $('#chat-form').toggle('slide', {
                    direction: 'right'
                }, 200);
                $('#chat-form > .chat-inner').slimScroll({
                    "height": $(window).height(),
                    'width': '280px',
                    "wheelStep": 30
                });
            }
            $('.chat-box-wrapper').css({'right': '280px'});
        });

        //Close event
        $('.chat-box-close').click(function () {
            $('#chat-box').hide();
            $('.chat-box-minimize').hide();
            $('#chat-form .chat-group a').removeClass('active');
        });
        $('.chat-form-close').click(function () {
            $('#chat-form').toggle('slide', {
                direction: 'right'
            }, 200);
            $('#chat-box').hide();
            $('.chat-box-wrapper').css({'right': '0px'});
        });

        $('.chat-box-minimize-btn').click(function () {
            $('#chat-box').hide();
            $('.chat-box-minimize').show();
        });

        $('.chat-box-minimize img').click(function () {
            $('#chat-box').show();
        });

        //Open chat box
        $('#chat-form .chat-group a').unbind('*').click(function () {
            $('#chat-box').hide();
            $('#chat-form .chat-group a').removeClass('active');
            $(this).addClass('active');
            //get user information to set for chat box
            var strUserName = $('> small', this).text();
            $('.display-name', '#chat-box').html(strUserName);
            var userStatus = $(this).find('span.user-status').attr('class');
            $('#chat-box > .chat-box-header > span.user-status').removeClass().addClass(userStatus);
            var userAvatar = $(this).find('img').attr('src');
            $('.chat-box-minimize').find('img').attr('src', userAvatar);
            var chatBoxStatus = $('span.user-status', '#chat-box');
            var chatBoxStatusShow = $('#chat-box > .chat-box-header > small');
            if (chatBoxStatus.hasClass('is-online')) {
                chatBoxStatusShow.html('Online');
            } else if (chatBoxStatus.hasClass('is-offline')) {
                chatBoxStatusShow.html('Offline');
            } else if (chatBoxStatus.hasClass('is-busy')) {
                chatBoxStatusShow.html('Busy');
            } else if (chatBoxStatus.hasClass('is-idle')) {
                chatBoxStatusShow.html('Idle');
            }

            $('#chat-box').css({'top': 'auto'});

            if (!$('#chat-box').is(':visible')) {
                $('#chat-box').toggle();
                $('.chat-box-minimize').toggle();
            }
            // focus to input tag when click an user
            $("#chat-box .chat-textarea input").focus();
            $('.chat-content > .chat-box-body').slimScroll({
                "height": "250px",
                'width': '340px',
                "wheelStep": 30,
                "scrollTo": $(this).height()
            });
        });
        // Add content to form
        $('.chat-textarea input').on("keypress", function (e) {
            var $obj = $(this);
            var $me = $obj.parent().parent().find('ul.chat-box-body');
            var $my_avt = 'http://api.randomuser.me/portraits/men/27.jpg';
            var $your_avt = 'https://s3.amazonaws.com/uifaces/faces/twitter/claudioguglieri/48.jpg';
            if (e.which == 13) {
                var $content = $obj.val();

                if ($content !== "") {
                    var d = new Date();
                    var h = d.getHours();
                    var m = d.getMinutes();
                    if (m < 10) m = "0" + m;
                    $obj.val(""); // CLEAR TEXT IN INPUT

                    var $element = "";
                    $element += "<li>";
                    $element += "<p>";
                    $element += "<img class='avt' src='" + $my_avt + "'>";
                    $element += "<span class='user'>You</span>";
                    $element += "<span class='time'>" + h + ":" + m + "</span>";
                    $element += "</p>";
                    $element = $element + "<p>" + $content + "</p>";
                    $element += "</li>";

                    $me.append($element);
                    var height = 0;
                    $me.find('li').each(function (i, value) {
                        height += parseInt($(this).height());
                    });

                    height += '';
                    $me.scrollTop(height);

                    // RANDOM RESPOND CHAT
                    var $res = "";
                    $res += "<li class='odd'>";
                    $res += "<p>";
                    $res += "<img class='avt' src='" + $your_avt + "'>";
                    $res += "<span class='user'>Jake Rochleau</span>";
                    $res += "<span class='time'>" + h + ":" + m + "</span>";
                    $res += "</p>";
                    $res = $res + "<p>" + "This is a demo respond anwser" + "</p>";
                    $res += "</li>";
                    setTimeout(function () {
                        $me.append($res);
                        $me.scrollTop(height + 100);
                    }, 1000);
                }
            }
        });
    }
    //END FORM CHAT

    var handleRemoveClassFont = function () {
        $("body").removeClass(function (index, css) {
            return (css.match(/(^|\s)font-\S+/g) || []).join(' ');
        });
    }

    var handleSidebarFixed = function () {
        if ($('body.layout-sidebar-fixed').hasClass('layout-sidebar-collapsed')) {
            destroySlimscroll('sidebar-main');
        } else {
            $('#sidebar-main').slimScroll({
                'width': '100%',
                'height': $(window).height() - 80,
                "wheelStep": 25
            });
        }
    }

    var destroySlimscroll = function (objectId) {
        $("#" + objectId).parent().replaceWith($("#" + objectId));
        $("#" + objectId).css('height', 'auto');
        $("#" + objectId).css('width', 'auto');
    }

    //BEGIN JQUERY VIEW CODE
    var handleViewCode = function () {


        $('#setting-view-code').on('switch-change', function () {
            $(".btn-view-code").toggleClass('hide');
        });

        var $button = $("<a href='javascript:;' class='btn-view-code'><i class='fa fa-code'></i></a>").click(function () {

            var idName = $(this).parent().attr('id');

            $("#source-modal pre").text('');

            if (idName === undefined) {
                var source = $(this).parent().html();
                // alert(source);
                source = cleanSource(source);
                $("#source-modal pre").text(source);
                prettyPrint();
            }
            else {
                var scriptsArr = idName.split('-');
                var scrtpts = scriptsArr[scriptsArr.length - 1];
                if (scrtpts == 'script') {
                    var source1 = $(this).parent().html();
                    $.ajax({
                        url: '_modals_view_code.html', dataType: 'html', success: function (response) {
                            var source = jQuery(response).find('#' + idName).html();
                            //alert(source);
                            source = cleanSource(source);
                            source1 = cleanSource(source1);
                            $("#source-modal pre").text(source1 + '\n' + source);
                            prettyPrint();
                        }, error: function () {
                            $("#source-modal pre").text('View code not working in chrome and IE but is working in firefox and safari');
                        }
                    });
                }
                else {
                    $.ajax({
                        url: '_modals_view_code.html', dataType: 'html', success: function (response) {
                            var source = jQuery(response).find('#' + idName).html();
                            //alert(source);
                            source = cleanSource(source);
                            $("#source-modal pre").text(source);
                            prettyPrint();
                        }, error: function () {
                            $("#source-modal pre").text('View code not working in chrome and IE but is working in firefox and safari');
                        }
                    });
                }
            }
            //source = cleanSource(source);


            $("#source-modal").modal();
        });

        $(".viewcode-example").hover(function () {
            $(this).append($button);
            $button.show();
        }, function () {
            $button.hide();
        });

        function cleanSource(source) {
            var lines = source.split(/\n/);
            lines.shift();
            lines.splice(-1, 1);

            var indentSize = lines[0].length - lines[0].trim().length,
                re = new RegExp(" {" + indentSize + "}");

            lines = lines.map(function (line) {
                if (line.match(re)) {
                    line = line.substring(indentSize);
                }

                return line;
            });

            lines = lines.join("\n");

            return lines;
        }
    }
    //END JQUERY VIEW CODE

    //BEGIN PULSATE
    var handlePulsate = function () {
        $("[data-pulsate]").each(function () {
            var me = $(this);
            var data = eval("(" + $(this).attr('data-pulsate') + ")");
            if (data.onClick == "one" || data.onClick == "crazy") {
                me.click(function () {
                    me.pulsate(data);
                });
            }
            if (data.onClick == "toggle") {
                me.toggle(function () {
                    me.pulsate(data.repeat = false);
                }, function () {
                    me.pulsate(data);
                });
            }
            if (data.onClick == "stop") {

                me.pulsate(data);
                if (data.parent == true) {
                    me.parent().one('click', function () {
                        me.pulsate({repeat: false});
                    });
                }
                else {
                    me.one('click', function () {
                        me.pulsate({repeat: false});
                    });
                }
            }
            if (data.onClick == undefined)
                me.pulsate(data);
        });
    }
    //END PULSATE

    //BEGIN NOTE APP
    var handleNoteApp = function () {
        //open note app
        $("#note-app-toggle").live('click', function (e) {
            e.preventDefault();
            if ($(this).hasClass('open')) {
                $(this).removeClass('open');
                $("#note-app-wrapper").fadeOut(function () {
                    $(this).css({right: 0, top: window.innerHeight - 300, left: "auto"});
                    $(" .note-app-content", this).css({height: 200});
                });
            }
            else {
                $(this).addClass('open');
                $("#note-app-wrapper").css({right: 0, top: window.innerHeight - 300, left: "auto"}).fadeIn();
            }

        });
        //drag drop
        $("#note-app-wrapper").draggable();

        //save function

        function saveTextAsFile(textWrite, fileName) {
            var textFileAsBlob = new Blob([textWrite], {type: 'text/plain'});

            var downloadLink = document.createElement("a");
            downloadLink.download = fileName + ".txt";
            downloadLink.innerHTML = "Download File";

            if (window.webkitURL != null) {
                // Chrome allows the link to be clicked
                // without actually adding it to the DOM.
                downloadLink.href = window.webkitURL.createObjectURL(textFileAsBlob);
            }
            else {
                // Firefox requires the link to be added to the DOM
                // before it can be clicked.
                downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
                downloadLink.onclick = destroyClickedElement;
                downloadLink.style.display = "none";
                document.body.appendChild(downloadLink);
            }
            downloadLink.click();
        }

        function destroyClickedElement(event) {
            document.body.removeChild(event.target);
        }

        $('.note-app-tools > i').live('click', function () {
            var me = $(this);
            //close
            if (me.hasClass("icon-close")) {
                $("#note-app-toggle").removeClass('open');
                $("#note-app-wrapper").fadeOut();
            }
            //save
            if (me.hasClass("fa-save")) {
                var fileName = prompt("Enter file name: ", "note");
                if (fileName != null) {
                    var textToWrite = $(".note-app-title").val() + "</title>" + $(".note-app-content").val();
                    saveTextAsFile(textToWrite, fileName);
                }
            }
            //import
            if (me.hasClass("fa-sign-in")) {
                $('#note-app-file').val('');
                $('#note-app-file').click();
                var fileInput = document.getElementById('note-app-file');
                var fileDisplayArea = $('.note-app-content'),
                    fileDisplayTitle = $('.note-app-title');

                fileInput.addEventListener('change', function (e) {
                    var file = fileInput.files[0];
                    var textType = /text.*/;

                    fileDisplayArea.val("");
                    fileDisplayTitle.val("");

                    if (file.type.match(textType)) {
                        var reader = new FileReader();
                        reader.onload = function (e) {
                            var title = reader.result.toString().split('</title>')[0];
                            if (title == reader.result) {
                                fileDisplayArea.val(reader.result);
                            }
                            else {
                                fileDisplayTitle.val(title);
                                fileDisplayArea.val(reader.result.replace(title + '</title>', ""));
                            }

                        }
                        reader.readAsText(file);
                    } else {
                        fileDisplayArea.innerText = "File not supported!";
                    }
                });
            }
            //new note
            if (me.hasClass("icon-note")) {
                $(".note-app-title").val("");
                $(".note-app-content").val("");
                $('#note-app-file').val('');
            }
        });

    }
    //END NOTE APP
    return {
        init: function () {
            handleSidebarMain();
            handleSlimscrollTopbar();
            handleSlimscrollTodo();
            handleTemplateSetting();
            handleNoteApp();
            handleViewCode();
            handleFormChat();
            handlePulsate();
        }
    }

}(jQuery);


