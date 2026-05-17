$(function () {

    const $form = $("#formular");

    const fields = {
        nume: $form.find("[name='nume']"),
        email: $form.find("[name='email']"),
        varsta: $form.find("[name='varsta']"),
        nivel: $form.find("[name='nivel']"),
        obiectiv: $form.find("[name='obiectiv']"),
        dataStart: $form.find("[name='data-start']"),
        observatii: $form.find("[name='observatii']")
    };

    let wrongFieldCounter = 0;

    const clearField = ($field) => $field.removeClass("input-invalid");
    const setFieldInvalid = ($field) => {
        $field.addClass("input-invalid");
        wrongFieldCounter++;
    };

    fields.dataStart.attr("min", new Date().toISOString().split("T")[0]);
    $.each(fields, function (_, $field) {
        $field.on("input change", function () {
            clearField($field);
        });
    });

    $form.on("submit", function (e) {

        wrongFieldCounter = 0;
        $.each(fields, function (_, $field) {
            clearField($field);
        });

        const age = parseInt(fields.varsta.val(), 10);

        if (fields.nume.val().trim().length < 3) setFieldInvalid(fields.nume);
        if (!fields.email[0].checkValidity()) setFieldInvalid(fields.email);
        if (!Number.isInteger(age) || age < 1 || age > 100) setFieldInvalid(fields.varsta);
        if (!fields.nivel.val()) setFieldInvalid(fields.nivel);
        if (!fields.obiectiv.val()) setFieldInvalid(fields.obiectiv);
        if (!fields.dataStart.val()) setFieldInvalid(fields.dataStart);
        if (fields.observatii.val().trim().length < 5) setFieldInvalid(fields.observatii);

        if ($form.find(".input-invalid").length) {
            e.preventDefault();
        }

        if (wrongFieldCounter > 0) {

            const $widget = $("#widget-tema");
            const $paragraf = $("#widget-paragraf");

            $widget.show();
            $paragraf.text(`${wrongFieldCounter} field-uri nevalide din 7`);
            setTimeout(() => {
                $widget.fadeOut();
            }, 3000);
        }

    });

});