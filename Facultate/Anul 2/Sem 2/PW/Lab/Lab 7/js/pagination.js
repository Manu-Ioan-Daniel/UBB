(function () {
    const API_URL = '../backend/programe/get_programe.php';
    const COLUMNS = ['ziua', 'exercitiu', 'seturi', 'repetari', 'focus'];

    let currentOffset = 0;
    let pageSize = 3;

    const wrapper = document.getElementById('pagination-wrapper');
    const table = document.getElementById('program-table-paginated');
    const btnNext = document.getElementById('btn-next-k');
    const btnPrev = document.getElementById('btn-prev-k');
    const statusEl = document.getElementById('pagination-status');

    if (!table || !btnNext || !btnPrev) {
        return;
    }

    if (wrapper && wrapper.dataset.k) {
        const parsed = parseInt(wrapper.dataset.k, 10);
        if (!isNaN(parsed) && parsed > 0) {
            pageSize = parsed;
        }
    }

    function capitalize(text) {
        return text.charAt(0).toUpperCase() + text.slice(1);
    }

    function renderTable(records) {
        table.innerHTML = '';

        const thead = document.createElement('thead');
        const headerRow = document.createElement('tr');

        COLUMNS.forEach(function (col) {
            const th = document.createElement('th');
            th.textContent = capitalize(col);
            headerRow.appendChild(th);
        });

        thead.appendChild(headerRow);
        table.appendChild(thead);

        const tbody = document.createElement('tbody');

        records.forEach(function (row) {
            const tr = document.createElement('tr');

            COLUMNS.forEach(function (col) {
                const td = document.createElement('td');
                td.textContent = row[col];
                tr.appendChild(td);
            });

            tbody.appendChild(tr);
        });

        table.appendChild(tbody);
    }

    function updateButtons(data) {
        btnPrev.disabled = data.isFirstPage;
        btnNext.disabled = data.isLastPage;
    }

    function updateStatus(data) {
        if (!statusEl) {
            return;
        }

        const from = data.total === 0 ? 0 : data.offset + 1;
        const to = data.offset + data.records.length;

        statusEl.textContent =
            'Inregistrari ' + from + '-' + to + ' din ' + data.total +
            ' (cate ' + data.k + ' pe pagina, format JSON)';
    }

    function loadPage(offset) {
        const url = API_URL + '?k=' + pageSize + '&offset=' + offset;
        const xhr = new XMLHttpRequest();

        xhr.open('GET', url, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState !== XMLHttpRequest.DONE) {
                return;
            }

            if (xhr.status !== 200) {
                table.innerHTML = '<caption>Eroare la incarcarea datelor.</caption>';
                btnPrev.disabled = true;
                btnNext.disabled = true;
                return;
            }

            const data = JSON.parse(xhr.responseText);

            if (data.error) {
                table.innerHTML = '<caption>' + data.error + '</caption>';
                btnPrev.disabled = true;
                btnNext.disabled = true;
                return;
            }

            currentOffset = data.offset;
            renderTable(data.records);
            updateButtons(data);
            updateStatus(data);
        };

        xhr.send();
    }

    btnNext.addEventListener('click', function () {
        if (!btnNext.disabled) {
            loadPage(currentOffset + pageSize);
        }
    });

    btnPrev.addEventListener('click', function () {
        if (!btnPrev.disabled) {
            loadPage(Math.max(0, currentOffset - pageSize));
        }
    });

    loadPage(0);
})();
