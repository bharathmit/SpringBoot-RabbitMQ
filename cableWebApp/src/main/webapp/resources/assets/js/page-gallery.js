var page_gallery = function () {
    var handleMixitup = function () {
        $('.mix-grid').mixItUp({
            animation: {
                duration: 400,
                effects: 'fade stagger(34ms) translateZ(-700px) scale(0.01)',
                easing: 'cubic-bezier(0.175, 0.885, 0.32, 1.275)'
            }
        });
    }
    var handleHoverEffect = function () {
        $("[rel='tooltip']").tooltip();

        $('.thumbnail').hover(
            function () {
                $(this).find('.caption').slideDown(250); //.fadeIn(250)
            },
            function () {
                $(this).find('.caption').slideUp(250); //.fadeOut(205)
            }
        );
    }

    return {
        init: function () {
            handleMixitup();
            handleHoverEffect();
        }
    }
}(jQuery);