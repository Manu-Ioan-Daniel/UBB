$(function () {

	const $form = $("#loginForm");
	const $username = $form.find("[name='username']");
	const $password = $form.find("[name='password']");
	const clearField = ($field) => $field.removeClass("input-invalid");
	const setFieldInvalid = ($field) => $field.addClass("input-invalid");

	$username.add($password).on("input", function () {
		clearField($(this));
	});

	$form.on("submit", function (e) {

		clearField($username);
		clearField($password);

		if ($username.val().trim().length < 3) setFieldInvalid($username);
		if ($password.val().trim().length < 4) setFieldInvalid($password);
		if ($form.find(".input-invalid").length) {
			e.preventDefault();
		}

	});

});