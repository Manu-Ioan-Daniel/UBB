import {programData} from "./data.js";

$(function () {

    const columns = Object.keys(programData[0]);
    const headers = columns.map(col => col.charAt(0).toUpperCase() + col.slice(1));

    let sortCol, sortAsc;
    let sortColV, sortAscV;

    function buildHorizontalTable(data) {
        const $table = $("#program-table").empty();

        const $thead = $("<thead>");
        const $headerRow = $("<tr>");

        $.each(columns, function (i, col) {

            let text = headers[i];

            if (sortCol === col) {
                text += sortAsc ? " ▲" : " ▼";
            }

            $("<th>")
                .text(text)
                .addClass("sortable")
                .toggleClass("sorted", sortCol === col)
                .on("click", function () {
                    sortBy(col);
                })
                .appendTo($headerRow);
        });

        $thead.append($headerRow).appendTo($table);

        const $tbody = $("<tbody>");

        $.each(data, function (_, row) {

            const $tr = $("<tr>");

            $.each(columns, function (_, col) {
                $("<td>")
                    .text(row[col])
                    .toggleClass("sorted-col", col === sortCol)
                    .appendTo($tr);
            });

            $tbody.append($tr);
        });

        $table.append($tbody);
    }

    function buildVerticalTable(data) {
        const $table = $("#program-table-vertical").empty();
        const $tbody = $("<tbody>");

        $.each(columns, function (i, col) {

            const $tr = $("<tr>");

            let text = headers[i];
            if (sortColV === col) {
                text += sortAscV ? " ▲" : " ▼";
            }

            $("<th>")
                .text(text)
                .addClass("sortable")
                .toggleClass("sorted", sortColV === col)
                .on("click", function () {
                    sortByVertical(col);
                })
                .appendTo($tr);

            $.each(data, function (_, row) {
                $("<td>")
                    .text(row[col])
                    .toggleClass("sorted-col", col === sortColV)
                    .appendTo($tr);
            });

            $tbody.append($tr);
        });

        $table.append($tbody);
    }

    function sortBy(col) {
        sortAsc = (sortCol === col) ? !sortAsc : true;
        sortCol = col;

        const sorted = [...programData].sort((a, b) => {
            if (a[col] < b[col]) return sortAsc ? -1 : 1;
            if (a[col] > b[col]) return sortAsc ? 1 : -1;
            return 0;
        });

        buildHorizontalTable(sorted);
    }

    function sortByVertical(col) {

        sortAscV = (sortColV === col) ? !sortAscV : true;
        sortColV = col;

        const sorted = [...programData].sort((a, b) => {
            if (a[col] < b[col]) return sortAscV ? -1 : 1;
            if (a[col] > b[col]) return sortAscV ? 1 : -1;
            return 0;
        });

        buildVerticalTable(sorted);
    }

    buildHorizontalTable(programData);
    buildVerticalTable(programData);

});