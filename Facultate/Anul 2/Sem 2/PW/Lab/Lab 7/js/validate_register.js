let wrongFieldCounter = 0;
$(function () {

    const $form = $("#formularCont");
    const data = CalisthenicsRegisterData?.judete || {};
    const $username = $form.find("[name='username']");
    const $email = $form.find("[name='email']");
    const $password = $form.find("[name='parola']");
    const $confirmPassword = $form.find("[name='confirmare']");
    const $age = $form.find("[name='varsta']");
    const $birthDate = $form.find("[name='data_nasterii']");
    const $county = $form.find("[name='judet']");
    const $city = $form.find("[name='oras']");
    const $program = $form.find("[name='program']");
    const $description = $form.find("[name='descriere']");

    const $goals = ["forta", "rezistenta", "mobilitate"]
        .map(n => $form.find(`[name='${n}']`))
        .filter($el => $el.length);

    const allFields = [
        $username, $email, $password, $confirmPassword,
        $age, $birthDate, $county, $city, $program,
        $description, ...$goals
    ];

    const clearField = ($field) => $field.removeClass("input-invalid");

    const setFieldInvalid = ($field) => {
        $field.addClass("input-invalid");
        wrongFieldCounter++;
    };

    const formatDate = (date) => {
        const y = date.getFullYear();
        const m = String(date.getMonth() + 1).padStart(2, '0');
        const d = String(date.getDate()).padStart(2, '0');
        return `${y}-${m}-${d}`;
    };

    const getBirthRange = (a) => {
        const today = new Date();
        const max = new Date(today);
        max.setFullYear(today.getFullYear() - a);

        const min = new Date(today);
        min.setFullYear(today.getFullYear() - a - 1);
        min.setDate(min.getDate() + 1);

        return { min: formatDate(min), max: formatDate(max) };
    };

    $birthDate.prop("disabled", true);

    $age.on("input", function () {

        clearField($birthDate);

        const a = parseInt($age.val(), 10);

        if (!Number.isInteger(a) || a < 1 || a > 100) {
            $birthDate.removeAttr("min max").prop("disabled", true);
            return;
        }

        const range = getBirthRange(a);
        $birthDate.attr("min", range.min)
            .attr("max", range.max)
            .prop("disabled", false);
    });

    $county.on("change", function () {

        clearField($city);
        $city.empty();

        const info = data[$county.val()];

        if (!info) {
            $city.prop("disabled", true);
            return;
        }

        $.each(info.cities, function (_, c) {
            $("<option>").val(c).text(c).appendTo($city);
        });

        $city.prop("disabled", false).val("");
    });

    $form.find("input, select, textarea").on("input change", function () {
        clearField($(this));
    });

    $form.on("submit", function (e) {

        wrongFieldCounter = 0;
        $.each(allFields, function (_, $field) {
            clearField($field);
        });

        const a = parseInt($age.val(), 10);
        const range = Number.isInteger(a) ? getBirthRange(a) : null;

        if ($username.val().trim().length < 3) setFieldInvalid($username);
        if (!$email[0].checkValidity()) setFieldInvalid($email);
        if ($password.val().trim().length < 8) setFieldInvalid($password);
        if ($confirmPassword.val() !== $password.val()) setFieldInvalid($confirmPassword);
        if (!Number.isInteger(a) || a < 1 || a > 100) setFieldInvalid($age);
        if (!$birthDate.val() ||
            ($birthDate.val() < range?.min || $birthDate.val() > range?.max)) {
            setFieldInvalid($birthDate);
        }
        if (!$county.val() || !data[$county.val()]) setFieldInvalid($county);
        if (!$city.val() || !data[$county.val()]?.cities.includes($city.val())) {
            setFieldInvalid($city);
        }
        if ($description.val().trim().length < 5) setFieldInvalid($description);
        if ($goals.every($g => !$g.is(":checked"))) {
            $.each($goals, (_, $g) => setFieldInvalid($g));
        }
        if (!$program.find(":selected").length) setFieldInvalid($program);
        if ($form.find(".input-invalid").length) {
            e.preventDefault();
            $form.find(".input-invalid").first().focus();
        }

    });

});