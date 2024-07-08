 async function solve() {
    const response = await fetch('/rates');

     const euroRate = await response.json();

     const priceElements = document.querySelectorAll('.price-usd');

     priceElements.forEach(priceElement => {
         const usdPrice = parseFloat(priceElement.textContent.replace('$', '').trim());
        console.log(usdPrice)
         const eurPrice = (usdPrice * euroRate.value).toFixed(2); // Форматиране до две десетични цифри
         console.log(eurPrice)
         const eurElement = priceElement.parentElement.querySelector('.price-eur');
         eurElement.textContent = eurPrice + ' €';
     });
}
solve()