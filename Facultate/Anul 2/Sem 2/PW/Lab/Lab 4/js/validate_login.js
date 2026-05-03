const form = document.getElementById('loginForm');

const username = form.elements.username;
const password = form.elements.password;

const clearField = (field) => field.classList.remove('input-invalid');
const setFieldInvalid = (field) => field.classList.add('input-invalid');

[username, password].forEach((field) => {
	field.addEventListener('input', () => clearField(field));
});

form.addEventListener('submit', (event) => {
	clearField(username);
	clearField(password);

	if (username.value.trim().length < 3) setFieldInvalid(username);
	if (password.value.trim().length < 6) setFieldInvalid(password);

	if (form.querySelector('.input-invalid')) event.preventDefault();

});