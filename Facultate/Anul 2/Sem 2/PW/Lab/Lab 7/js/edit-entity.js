(function () {
    const GET_URL = '../backend/programe/get_record.php';
    const SAVE_URL = '../backend/programe/update_record.php';

    const form = document.getElementById('edit-entity-form');
    const select = document.getElementById('edit-entity-id');
    const btnSave = document.getElementById('edit-entity-save');
    const messageEl = document.getElementById('edit-entity-message');

    if (!form || !select || !btnSave) {
        return;
    }

    const fields = {
        ziua: document.getElementById('edit-ziua'),
        exercitiu: document.getElementById('edit-exercitiu'),
        seturi: document.getElementById('edit-seturi'),
        repetari: document.getElementById('edit-repetari'),
        focus: document.getElementById('edit-focus'),
    };

    let currentId = '';
    let isDirty = false;
    let isLoading = false;

    function setMessage(text, isError) {
        if (!messageEl) {
            return;
        }
        messageEl.textContent = text || '';
        messageEl.className = isError ? 'edit-message edit-message-error' : 'edit-message';
    }

    function setSaveEnabled(enabled) {
        btnSave.disabled = !enabled;
    }

    function setFieldsEnabled(enabled) {
        Object.keys(fields).forEach(function (key) {
            if (fields[key]) {
                fields[key].disabled = !enabled;
            }
        });
    }

    function clearForm() {
        Object.keys(fields).forEach(function (key) {
            if (fields[key]) {
                fields[key].value = '';
            }
        });
        isDirty = false;
        setSaveEnabled(false);
    }

    function fillForm(record) {
        fields.ziua.value = record.ziua;
        fields.exercitiu.value = record.exercitiu;
        fields.seturi.value = record.seturi;
        fields.repetari.value = record.repetari;
        fields.focus.value = record.focus;
        isDirty = false;
        setSaveEnabled(false);
    }

    function getFormData() {
        return {
            id: select.value,
            ziua: fields.ziua.value.trim(),
            exercitiu: fields.exercitiu.value.trim(),
            seturi: fields.seturi.value,
            repetari: fields.repetari.value,
            focus: fields.focus.value.trim(),
        };
    }

    function markDirty() {
        if (!isLoading && select.value !== '') {
            isDirty = true;
            setSaveEnabled(true);
        }
    }

    function loadRecord(id, onDone) {
        isLoading = true;
        setFieldsEnabled(false);
        setSaveEnabled(false);
        setMessage('Se incarca...', false);

        const xhr = new XMLHttpRequest();
        xhr.open('GET', GET_URL + '?id=' + encodeURIComponent(id), true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState !== XMLHttpRequest.DONE) {
                return;
            }

            isLoading = false;

            if (xhr.status !== 200) {
                setMessage('Eroare la incarcarea inregistrarii.', true);
                clearForm();
                setFieldsEnabled(false);
                if (onDone) {
                    onDone(false);
                }
                return;
            }

            const data = JSON.parse(xhr.responseText);

            if (data.error) {
                setMessage(data.error, true);
                clearForm();
                setFieldsEnabled(false);
                if (onDone) {
                    onDone(false);
                }
                return;
            }

            currentId = String(data.record.id);
            fillForm(data.record);
            setFieldsEnabled(true);
            setMessage('', false);

            if (onDone) {
                onDone(true);
            }
        };
        xhr.send();
    }

    function saveRecord(onDone) {
        const payload = getFormData();
        const body = new URLSearchParams();
        body.append('id', payload.id);
        body.append('ziua', payload.ziua);
        body.append('exercitiu', payload.exercitiu);
        body.append('seturi', payload.seturi);
        body.append('repetari', payload.repetari);
        body.append('focus', payload.focus);

        setSaveEnabled(false);
        setMessage('Se salveaza...', false);

        const xhr = new XMLHttpRequest();
        xhr.open('POST', SAVE_URL, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function () {
            if (xhr.readyState !== XMLHttpRequest.DONE) {
                return;
            }

            if (xhr.status !== 200) {
                setMessage('Eroare la salvare.', true);
                setSaveEnabled(true);
                if (onDone) {
                    onDone(false);
                }
                return;
            }

            const data = JSON.parse(xhr.responseText);

            if (data.error) {
                setMessage(data.error, true);
                setSaveEnabled(true);
                if (onDone) {
                    onDone(false);
                }
                return;
            }

            fillForm(data.record);
            currentId = String(data.record.id);
            setMessage('Salvat cu succes.', false);

            if (onDone) {
                onDone(true);
            }
        };
        xhr.send(body.toString());
    }

    function revertSelect() {
        select.value = currentId;
    }

    function handleSelectChange() {
        const newId = select.value;
        const previousId = currentId;

        if (newId === '') {
            clearForm();
            currentId = '';
            setFieldsEnabled(false);
            setMessage('', false);
            return;
        }

        if (newId === previousId) {
            return;
        }

        if (!isDirty) {
            loadRecord(newId);
            return;
        }

        const saveFirst = window.confirm(
            'Exista modificari nesalvate. Doriti sa le salvati inainte de continuare?'
        );

        if (saveFirst) {
            revertSelect();
            saveRecord(function (success) {
                if (success) {
                    select.value = newId;
                    loadRecord(newId);
                }
            });
            return;
        }

        const continueWithoutSave = window.confirm(
            'Continuati fara salvare? Modificarile curente vor fi pierdute.'
        );

        if (continueWithoutSave) {
            loadRecord(newId);
        } else {
            revertSelect();
        }
    }

    select.addEventListener('change', handleSelectChange);

    Object.keys(fields).forEach(function (key) {
        if (fields[key]) {
            fields[key].addEventListener('input', markDirty);
            fields[key].addEventListener('change', markDirty);
        }
    });

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        if (!isDirty || btnSave.disabled) {
            return;
        }

        saveRecord();
    });

    clearForm();
    setFieldsEnabled(false);
})();
