const VERIFY_API = 'http://localhost:8080/user/verify';

document.addEventListener('DOMContentLoaded', () => {
    const displayEmail = document.getElementById('displayEmail');
    const verifyForm = document.getElementById('verifyForm');
    const verifyBtn = document.getElementById('verifyBtn');
    const codeInput = document.getElementById('codeInput');


    if (displayEmail) {
        displayEmail.innerText = localStorage.getItem('emailForVerification') || "your email";
    }

    if (verifyForm) {
        verifyForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            console.log("verification...");

            const email = localStorage.getItem('emailForVerification');
            if (!email) {
                alert("Error: email has not found. register again.");
                window.location.href = 'register.html';
                return;
            }

            verifyBtn.disabled = true;
            verifyBtn.innerText = "verification...";

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
                    alert('account has been verified! Welcome!.');
                    localStorage.setItem('isLoggedIn', 'true');
                    window.location.href = 'login.html'; // Или сразу в index.html
                } else {
                    const errorMsg = await response.text();
                    alert('Error: ' + errorMsg);
                    verifyBtn.disabled = false;
                    verifyBtn.innerText = "Verify Account";
                }
            } catch (err) {
                console.error("Error network:", err);
                alert('Server connection failed. Make sure Spring Boot is active.');
                verifyBtn.disabled = false;
                verifyBtn.innerText = "Verify Account";
            }
        });
    }
});