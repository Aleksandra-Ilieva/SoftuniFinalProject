document.addEventListener('DOMContentLoaded', function() {
    // Намираме бутона за смяна на езика
    var languageToggle = document.getElementById('language-toggle');

    // Проверяваме дали URL има параметър 'lang'
    var urlParams = new URLSearchParams(window.location.search);
    var initialLang = urlParams.get('lang');

    // Ако параметърът 'lang' е 'bg', задаваме чекнато състояние на бутона
    if (initialLang === 'bg') {
        languageToggle.checked = true;
    }

    // Слушаме събитие за промяна в състоянието на бутона
    languageToggle.addEventListener('change', function() {
        if (this.checked) {
            // Ако бутона е чекнат, променяме езика към 'bg'
            window.location.href = updateUrlParameter(window.location.href, 'lang', 'bg');
        } else {
            // Ако бутона не е чекнат, променяме езика към 'en'
            window.location.href = updateUrlParameter(window.location.href, 'lang', 'en');
        }
    });

    // Функция за обновяване на URL параметър
    function updateUrlParameter(url, param, value) {
        var re = new RegExp('([?&])' + param + '=.*?(&|$)', 'i');
        var separator = url.indexOf('?') !== -1 ? '&' : '?';
        if (url.match(re)) {
            return url.replace(re, '$1' + param + '=' + value + '$2');
        } else {
            return url + separator + param + '=' + value;
        }
    }
});