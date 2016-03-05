var ui_typography = function () {

    var handleWordRotate = function () {
        $('.word-rotate-items').each(function () {
            var me = $(this);
            var count = $(' > span', me).length;
            var i = 1;

            setInterval(function () {
                if (i < count) {
                    me.css({
                        '-webkit-transform': 'translate3d(0,-' + i + '00%,0)',
                        '-moz-transform': 'translate3d(0,-' + i + '00%,0)',
                        '-o-transform': 'translate3d(0,-' + i + '00%,0)',
                        '-ms-transform': 'translate3d(0,-' + i + '00%,0)',
                        'transform': 'translate3d(0,-' + i + '00%,0)'
                    });
                    i++;
                }
                else {
                    i = 0;
                }
            }, 2000);
        });
    }

    var handleFloatDownText = function () {
        $('.float-down-text').each(function () {
            var me = $(this);
            var a = 0,
                para = me.text(),
                pArray = para.split('');
            me.text('');
            var animate = function () {
                var timeout = setTimeout(function () {
                    clearTimeout(timeout);
                    if (a === pArray.length) {
                        return false;
                    } else {
                        me.append('<span>' + pArray[a] + '</span>');
                        a++;
                        animate();
                    }
                }, 150);
            };
            animate();
        });
    }

    return {
        init: function () {
            handleWordRotate();
            handleFloatDownText();
            setInterval(function () {
                handleFloatDownText();
            }, 8000);
        }
    }

}(jQuery);