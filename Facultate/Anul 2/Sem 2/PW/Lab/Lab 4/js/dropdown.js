
document.querySelectorAll('.menu li').forEach(function(li) {
    li.addEventListener('click', function() {
        if (window.innerWidth <= 600) {
            const dropdown = this.querySelector('.dropdown');
            if (dropdown) {
                dropdown.style.display === 'block' ? dropdown.style.display = 'none' : dropdown.style.display = 'block';
            }
        }
    });
});