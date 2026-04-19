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

const image = document.getElementById("slide-image");
const text = document.getElementById("slide-text");
const link = document.getElementById("slide-link");

function resetTimer(){
    clearInterval(timer);
    timer = setInterval(nextSlide, 3000);
}

function showSlide(index) {
    image.src = slides[index].img;
    image.alt = slides[index].text;
    text.textContent = slides[index].text;
    link.href = slides[index].img;
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


timer = setInterval(nextSlide, 3000);
showSlide(current);