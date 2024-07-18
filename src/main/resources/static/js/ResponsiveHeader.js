
function solve() {
    // scripts.js
    document.addEventListener('DOMContentLoaded', () => {
        const hamburger = document.getElementById('hamburger');
        const navMenu = document.getElementById('nav-menu');
        console.log(hamburger)
        console.log(navMenu.classList)

        hamburger.addEventListener('click', () => {
            navMenu.classList.toggle('active');
        });
    });


}
solve()