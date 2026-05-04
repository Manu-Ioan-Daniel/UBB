const form = document.getElementById('formular');
const fields = {
    nume: form.elements.nume,
    email: form.elements.email,
    varsta: form.elements.varsta,
    nivel: form.elements.nivel,
    obiectiv: form.elements.obiectiv,
    dataStart: form.elements['data-start'],
    observatii: form.elements.observatii
};
let wrongFieldCounter = 0;

const clearField = (field) => field.classList.remove('input-invalid');
const setFieldInvalid = (field) => {
    field.classList.add('input-invalid');
    wrongFieldCounter++;
}

fields.dataStart.min = new Date().toISOString().split('T')[0];

Object.values(fields).forEach((field) => {
    field.addEventListener('input', () => clearField(field));
    field.addEventListener('change', () => clearField(field));
});

form.addEventListener('submit', (event) => {
    Object.values(fields).forEach(clearField);

    const age = parseInt(fields.varsta.value, 10);
    if (fields.nume.value.trim().length < 3) setFieldInvalid(fields.nume);
    if (!fields.email.checkValidity()) setFieldInvalid(fields.email);
    if (!Number.isInteger(age) || age < 1 || age > 100) setFieldInvalid(fields.varsta);
    if (!fields.nivel.value) setFieldInvalid(fields.nivel);
    if (!fields.obiectiv.value) setFieldInvalid(fields.obiectiv);
    if (!fields.dataStart.value) setFieldInvalid(fields.dataStart);
    if (fields.observatii.value.trim().length < 5) setFieldInvalid(fields.observatii);

    if (form.querySelector('.input-invalid')) event.preventDefault();

    if(wrongFieldCounter>0) {
        const widget = document.getElementById("widget-tema");
        const paragraf = document.getElementById("widget-paragraf");
        widget.style.display = "block";
        paragraf.textContent = `${wrongFieldCounter} field uri nevalide din 7`
        wrongFieldCounter = 0;
        setTimeout(() => {
            widget.style.display = "none";
        }, 3000);
    }
});