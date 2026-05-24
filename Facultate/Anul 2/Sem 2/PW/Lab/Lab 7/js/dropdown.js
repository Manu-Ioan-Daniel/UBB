$(function () {

    $(".menu li").on("click", function () {

        if ($(window).width() <= 600) {
            const $dropdown = $(this).children(".dropdown");
            if (!$dropdown.length) return;
            $dropdown.toggle();
        }

    });

});