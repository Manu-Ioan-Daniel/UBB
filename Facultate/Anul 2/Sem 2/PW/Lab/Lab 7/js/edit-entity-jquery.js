$(function () {
    const GET_URL = '../backend/programe/get_record.php';
    const SAVE_URL = '../backend/programe/update_record.php';

    const $form = $('#edit-entity-form-jq');
    const $select = $('#edit-entity-id-jq');
    const $btnSave = $('#edit-entity-save-jq');
    const $message = $('#edit-entity-message-jq');

    if ($form.length === 0) {
        return;
    }

    const $fields = {
        ziua: $('#edit-ziua-jq'),
        exercitiu: $('#edit-exercitiu-jq'),
        seturi: $('#edit-seturi-jq'),
        repetari: $('#edit-repetari-jq'),
        focus: $('#edit-focus-jq'),
    };

    let currentId = '';
    let isDirty = false;
    let isLoading = false;

    function setMessage(text, isError) {
        $message.text(text || '');
        $message.attr('class', isError ? 'edit-message edit-message-error' : 'edit-message');
    }

    function setSaveEnabled(enabled) {
        $btnSave.prop('disabled', !enabled);
    }

    function setFieldsEnabled(enabled) {
        $.each($fields, function (_, $field) {
            $field.prop('disabled', !enabled);
        });
    }

    function clearForm() {
        $.each($fields, function (_, $field) {
            $field.val('');
        });
        isDirty = false;
        setSaveEnabled(false);
    }

    function fillForm(record) {
        $fields.ziua.val(record.ziua);
        $fields.exercitiu.val(record.exercitiu);
        $fields.seturi.val(record.seturi);
        $fields.repetari.val(record.repetari);
        $fields.focus.val(record.focus);
        isDirty = false;
        setSaveEnabled(false);
    }

    function getFormData() {
        return {
            id: $select.val(),
            ziua: $fields.ziua.val().trim(),
            exercitiu: $fields.exercitiu.val().trim(),
            seturi: $fields.seturi.val(),
            repetari: $fields.repetari.val(),
            focus: $fields.focus.val().trim(),
        };
    }

    function markDirty() {
        if (!isLoading && $select.val() !== '') {
            isDirty = true;
            setSaveEnabled(true);
        }
    }

    function loadRecord(id, onDone) {
        isLoading = true;
        setFieldsEnabled(false);
        setSaveEnabled(false);
        setMessage('Se incarca...', false);

        $.ajax({
            url: GET_URL,
            method: 'GET',
            dataType: 'json',
            data: { id: id },
            success: function (data) {
                isLoading = false;

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
            },
            error: function () {
                isLoading = false;
                setMessage('Eroare la incarcarea inregistrarii.', true);
                clearForm();
                setFieldsEnabled(false);
                if (onDone) {
                    onDone(false);
                }
            },
        });
    }

    function saveRecord(onDone) {
        const payload = getFormData();

        setSaveEnabled(false);
        setMessage('Se salveaza...', false);

        $.ajax({
            url: SAVE_URL,
            method: 'POST',
            dataType: 'json',
            data: payload,
            success: function (data) {
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
            },
            error: function () {
                setMessage('Eroare la salvare.', true);
                setSaveEnabled(true);
                if (onDone) {
                    onDone(false);
                }
            },
        });
    }

    function revertSelect() {
        $select.val(currentId);
    }

    function handleSelectChange() {
        const newId = $select.val();
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
                    $select.val(newId);
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

    $select.on('change', handleSelectChange);

    $.each($fields, function (_, $field) {
        $field.on('input change', markDirty);
    });

    $form.on('submit', function (event) {
        event.preventDefault();

        if (!isDirty || $btnSave.prop('disabled')) {
            return;
        }

        saveRecord();
    });

    clearForm();
    setFieldsEnabled(false);
});
