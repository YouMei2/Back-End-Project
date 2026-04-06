const API_URL = 'http://localhost:8080/user';

const registerForm = document.getElementById('registerForm');

if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const submitBtn = registerForm.querySelector('button');
        const inputs = registerForm.querySelectorAll('input');

        const userData = {
            user:     inputs[0].value,
            email:    inputs[1].value.trim().toLowerCase(),
            password: inputs[2].value
        };

        submitBtn.disabled = true;
        submitBtn.innerText = "Sending...";

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });

            if (response.ok) {
                // Сохраняем email, чтобы на странице верификации знать, кого подтверждать
                localStorage.setItem('emailForVerification', userData.email);
                alert('Account created! Check your email.');
                window.location.href = 'verify.html';
            } else {
                const error = await response.text();
                alert('Error: ' + error);
                submitBtn.disabled = false;
                submitBtn.innerText = "Sign Up";
            }
        } catch (err) {
            alert('Server is offline!');
            submitBtn.disabled = false;
        }
    });
}