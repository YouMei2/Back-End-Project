const VERIFY_API = 'http://localhost:8080/user/verify';

// 1. Ждем загрузки DOM, чтобы найти все элементы
document.addEventListener('DOMContentLoaded', () => {
    const displayEmail = document.getElementById('displayEmail');
    const verifyForm = document.getElementById('verifyForm'); // Ищем саму форму
    const verifyBtn = document.getElementById('verifyBtn');
    const codeInput = document.getElementById('codeInput');

    // Выводим email из памяти
    if (displayEmail) {
        displayEmail.innerText = localStorage.getItem('emailForVerification') || "вашу почту";
    }

    // 2. Вешаем событие именно на SUBMIT формы (так надежнее всего)
    if (verifyForm) {
        verifyForm.addEventListener('submit', async (e) => {
            e.preventDefault(); // ГЛАВНОЕ: Отменяем перезагрузку страницы!

            console.log("Попытка верификации..."); // Увидишь в консоли (F12)

            const email = localStorage.getItem('emailForVerification');
            if (!email) {
                alert("Ошибка: email не найден в памяти. Зарегистрируйтесь заново.");
                window.location.href = 'register.html';
                return;
            }

            // Визуал: блокируем кнопку
            verifyBtn.disabled = true;
            verifyBtn.innerText = "Проверка...";

            try {
                const response = await fetch(VERIFY_API, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        email: email,
                        code: codeInput.value.trim()
                    })
                });

                if (response.ok) {
                    alert('Аккаунт подтвержден! Добро пожаловать.');
                    localStorage.setItem('isLoggedIn', 'true');
                    window.location.href = 'login.html'; // Или сразу в index.html
                } else {
                    const errorMsg = await response.text();
                    alert('Ошибка: ' + errorMsg);
                    verifyBtn.disabled = false;
                    verifyBtn.innerText = "Verify Account";
                }
            } catch (err) {
                console.error("Ошибка сети:", err);
                alert('Нет связи с сервером. Проверь, запущен ли Spring Boot!');
                verifyBtn.disabled = false;
                verifyBtn.innerText = "Verify Account";
            }
        });
    }
});