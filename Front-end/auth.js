/* --- AUTHENTICATION LOGIC (auth.js) --- */

const USER_API = 'http://localhost:8080/user';

// 1. Handling User Registration
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const inputs = registerForm.querySelectorAll('input');
        const userData = {
            user:     inputs[0].value,
            email:    inputs[1].value,
            password: inputs[2].value
        };

        try {
            const response = await fetch(USER_API, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(userData)
            });

            if (response.ok) {
                alert('Account created! Redirecting to login...');
                window.location.href = 'login.html';
            } else {
                alert('Registration failed. Status: ' + response.status);
            }
        } catch (err) {
            console.error("Auth error:", err);
            alert('Connection error. Is Spring Boot running?');
        }
    });
}

// 2. Handling User Login
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const inputs = loginForm.querySelectorAll('input');
        const email = inputs[0].value;
        const password = inputs[1].value;

        try {
            const response = await fetch(USER_API);
            const users = await response.json();
            const foundUser = users.find(u => u.email === email && u.password === password);

            if (foundUser) {
                localStorage.setItem('userId', foundUser.id);
                localStorage.setItem('userName', foundUser.user);
                window.location.href = 'index.html';
            } else {
                alert('Invalid email or password!');
            }
        } catch (err) {
            console.error("Login error:", err);
        }
    });
}