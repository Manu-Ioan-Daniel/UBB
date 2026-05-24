$(function () {
    const API_URL = '../backend/programe/get_programe.php';
    const COLUMNS = ['ziua', 'exercitiu', 'seturi', 'repetari', 'focus'];

    let currentOffset = 0;
    const $wrapper = $('#pagination-wrapper-jq');
    let pageSize = parseInt($wrapper.data('k'), 10);

    if (isNaN(pageSize) || pageSize < 1) {
        pageSize = 3;
    }

    const $table = $('#program-table-paginated-jq');
    const $btnNext = $('#btn-next-k-jq');
    const $btnPrev = $('#btn-prev-k-jq');
    const $status = $('#pagination-status-jq');

    if ($table.length === 0) {
        return;
    }

    function capitalize(text) {
        return text.charAt(0).toUpperCase() + text.slice(1);
    }

    function renderTable(records) {
        $table.empty();

        const $thead = $('<thead>').appendTo($table);
        const $headerRow = $('<tr>').appendTo($thead);

        $.each(COLUMNS, function (_, col) {
            $('<th>').text(capitalize(col)).appendTo($headerRow);
        });

        const $tbody = $('<tbody>').appendTo($table);

        $.each(records, function (_, row) {
            const $tr = $('<tr>').appendTo($tbody);

            $.each(COLUMNS, function (_, col) {
                $('<td>').text(row[col]).appendTo($tr);
            });
        });
    }

    function updateStatus(data) {
        const from = data.total === 0 ? 0 : data.offset + 1;
        const to = data.offset + data.records.length;

        $status.text(
            'Inregistrari ' + from + '-' + to + ' din ' + data.total +
            ' (cate ' + data.k + ' pe pagina, jQuery JSON)'
        );
    }

    function loadPage(offset) {
        $.ajax({
            url: API_URL,
            method: 'GET',
            dataType: 'json',
            data: {
                k: pageSize,
                offset: offset
            },
            success: function (data) {
                if (data.error) {
                    $table.html('<caption>' + data.error + '</caption>');
                    $btnPrev.prop('disabled', true);
                    $btnNext.prop('disabled', true);
                    return;
                }

                currentOffset = data.offset;
                renderTable(data.records);
                $btnPrev.prop('disabled', data.isFirstPage);
                $btnNext.prop('disabled', data.isLastPage);
                updateStatus(data);
            },
            error: function () {
                $table.html('<caption>Eroare la incarcarea datelor.</caption>');
                $btnPrev.prop('disabled', true);
                $btnNext.prop('disabled', true);
            }
        });
    }

    $btnNext.on('click', function () {
        if (!$btnNext.prop('disabled')) {
            loadPage(currentOffset + pageSize);
        }
    });

    $btnPrev.on('click', function () {
        if (!$btnPrev.prop('disabled')) {
            loadPage(Math.max(0, currentOffset - pageSize));
        }
    });

    loadPage(0);
});
