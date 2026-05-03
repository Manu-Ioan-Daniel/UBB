const form = document.getElementById('formularCont');
const data = CalisthenicsRegisterData?.judete || {};

const username = form.elements.username;
const email = form.elements.email;
const password = form.elements.parola;
const confirmPassword = form.elements.confirmare;
const age = form.elements.varsta;
const birthDate = form.elements.data_nasterii;
const county = form.elements.judet;
const city = form.elements.oras;
const program = form.elements.program;
const description = form.elements.descriere;
const goals = ['forta', 'rezistenta', 'mobilitate'].map((n) => form.elements[n]).filter(Boolean);

const allFields = [username, email, password, confirmPassword, age, birthDate, county, city, program, description, ...goals];

const clearField = (field) => field?.classList.remove('input-invalid');
const setFieldInvalid = (field) => {
    field?.classList.add('input-invalid');
    wrongFieldCounter+=1;
};


const formatDate = (date) => {
    const y = date.getFullYear();
    const m = String(date.getMonth() + 1).padStart(2, '0');
    const d = String(date.getDate()).padStart(2, '0');
    return `${y}-${m}-${d}`;
};

const getBirthRange = (a) => {
    const today = new Date();
    const max = new Date(today); max.setFullYear(today.getFullYear() - a);
    const min = new Date(today); min.setFullYear(today.getFullYear() - a - 1); min.setDate(min.getDate() + 1);
    return { min: formatDate(min), max: formatDate(max) };
};
birthDate.disabled = true;
age.addEventListener('input', () => {
    clearField(birthDate);
    const a = parseInt(age.value, 10);
    if (!Number.isInteger(a) || a < 1 || a > 100) {
        birthDate.removeAttribute('min');
        birthDate.removeAttribute('max');
        birthDate.disabled = true;
        return;
    }
    const range = getBirthRange(a);
    birthDate.min = range.min;
    birthDate.max = range.max;
    birthDate.disabled = false;
});

county.addEventListener('change', () => {
    clearField(city);
    city.innerHTML = '';
    const info = data[county.value];
    if (!info) { city.disabled = true; return; }
    info.cities.forEach((c) => {
        const opt = document.createElement('option');
        opt.value = c; opt.textContent = c;
        city.appendChild(opt);
    });
    city.disabled = false;
    city.value = '';
});

[username, email, password, confirmPassword, birthDate, city, program, description].forEach((field) => {
    field.addEventListener('input', () => clearField(field));
    field.addEventListener('change', () => clearField(field));
});

goals.forEach((g) => g.addEventListener('change', () => goals.forEach(clearField)));

form.addEventListener('submit', (event) => {
    allFields.forEach(clearField);

    const a = parseInt(age.value, 10);
    const { min, max } = Number.isInteger(a) ? getBirthRange(a) : { min: null, max: null };

    if (username.value.trim().length < 3) setFieldInvalid(username);
    if (!email.checkValidity()) setFieldInvalid(email);
    if (password.value.trim().length < 8) setFieldInvalid(password);
    if (!confirmPassword.value || confirmPassword.value !== password.value) setFieldInvalid(confirmPassword);
    if (!Number.isInteger(a) || a < 1 || a > 100) setFieldInvalid(age);
    if (!birthDate.value || birthDate.value < min || birthDate.value > max) setFieldInvalid(birthDate);
    if (!county.value || !data[county.value]) setFieldInvalid(county);
    if (!city.value || !data[county.value]?.cities.includes(city.value)) setFieldInvalid(city);
    if (description.value.trim().length < 5) setFieldInvalid(description);
    if (!goals.some((g) => g.checked)) goals.forEach(setFieldInvalid);
    if (!Array.from(program.selectedOptions).some((o) => o.value)) setFieldInvalid(program);

    if (form.querySelector('.input-invalid')) {
        event.preventDefault();
        form.querySelector('.input-invalid').focus();
    }

});