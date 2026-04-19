
const columns = Object.keys(programData[0]);
const headers = columns.map(column => column.charAt(0).toUpperCase() + column.slice(1));
let sortAsc;
let sortCol;
let sortColV;
let sortAscV;

function buildHorizontalTable(data) {
    const table = document.getElementById("program-table");
    table.innerHTML = "";

    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");

    columns.forEach((col, i) => {
        const th = document.createElement("th");
        th.textContent = headers[i];
        th.classList.add("sortable");

        if (sortCol === col) {
            th.classList.add("sorted");
            th.textContent += sortAsc ? " ▲" : " ▼";
        }

        th.addEventListener("click", () => sortBy(col));
        headerRow.appendChild(th);
    });

    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement("tbody");

    data.forEach(row => {
        const tr = document.createElement("tr");
        columns.forEach(col => {
            const td = document.createElement("td");
            td.textContent = row[col];
            if (col === sortCol) td.classList.add("sorted-col");
            tr.appendChild(td);
        });
        tbody.appendChild(tr);
    });

    table.appendChild(tbody);
}

function buildVerticalTable(data) {
    const table = document.getElementById("program-table-vertical");
    table.innerHTML = "";

    const tbody = document.createElement("tbody");

    columns.forEach((col, i) => {
        const tr = document.createElement("tr");

        const th = document.createElement("th");
        th.textContent = headers[i];
        th.classList.add("sortable");

        if (sortColV === col) {
            th.classList.add("sorted");
            th.textContent += sortAscV ? " ▲" : " ▼";
        }

        th.addEventListener("click", () => sortByVertical(col));
        tr.appendChild(th);

        data.forEach(row => {
            const td = document.createElement("td");
            td.textContent = row[col];
            if (col === sortColV) td.classList.add("sorted-col");
            tr.appendChild(td);
        });

        tbody.appendChild(tr);
    });

    table.appendChild(tbody);
}



function sortBy(col) {

    if (sortCol === col) {
        sortAsc = !sortAsc;
    } else {
        sortCol = col;
        sortAsc = true;
    }

    const sorted = [...programData].sort((a, b) => {
        if (a[col] < b[col]) return sortAsc ? -1 : 1;
        if (a[col] > b[col]) return sortAsc ? 1 : -1;
        return 0;
    });

    buildHorizontalTable(sorted);
}

function sortByVertical(col) {
    if (sortColV === col) {
        sortAscV = !sortAscV;
    } else {
        sortColV = col;
        sortAscV = true;
    }

    const sorted = [...programData].sort((a, b) => {
        if (a[col] < b[col]) return sortAscV ? -1 : 1;
        if (a[col] > b[col]) return sortAscV ? 1 : -1;
        return 0;
    });

    buildVerticalTable(sorted);
}

buildHorizontalTable(programData);
buildVerticalTable(programData);


