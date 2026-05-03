$(document).ready(function () {

    const slides = [
        {
            img: "../assets/image1.jpg",
            text: "Full Planche"
        },
        {
            img: "../assets/image2.jpg",
            text: "Front Lever"
        },
        {
            img: "../assets/image3.jpg",
            text: "One Arm Handstand"
        },
        {
            img: "../assets/image4.jpg",
            text: "Iron Cross"
        }
    ];

    let current = 0;
    let timer;

    const $image = $("#slide-image");
    const $text = $("#slide-text");
    const $link = $("#slide-link");

    function resetTimer() {
        clearInterval(timer);
        timer = setInterval(nextSlide, 3000);
    }

    function showSlide(index) {
        $image.attr("src", slides[index].img);
        $image.attr("alt", slides[index].text);
        $text.text(slides[index].text);
        $link.attr("href", slides[index].img);
    }

    function nextSlide() {
        current = (current + 1) % slides.length;
        showSlide(current);
        resetTimer();
    }

    function prevSlide() {
        current = current === 0 ? slides.length - 1 : current - 1;
        showSlide(current);
        resetTimer();
    }

    // Optional: bind buttons if you have them
    $("#next-slide-btn").on("click", nextSlide);
    $("#prev-slide-btn").on("click", prevSlide);

    // Init
    timer = setInterval(nextSlide, 3000);
    showSlide(current);

});