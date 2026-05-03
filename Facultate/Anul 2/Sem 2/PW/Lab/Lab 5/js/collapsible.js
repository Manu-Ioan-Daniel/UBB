$(function () {

    $("ol.collapsible > li").on("click", function (e) {

        e.preventDefault();
        const $sublist = $(this).children("ol");
        if (!$sublist.length) return;
        $(this).toggleClass("open");
        $sublist.toggle();

    });

});