document.querySelectorAll("ol.collapsible > li").forEach(li => {
    li.addEventListener("click", (e) => {

        const sublist = li.querySelector("ol");
        if (!sublist) return;

        li.classList.toggle("open");
        sublist.style.display = sublist.style.display === "block" ? "none" : "block";
    });
});