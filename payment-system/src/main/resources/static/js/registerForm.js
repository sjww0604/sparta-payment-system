
const API_BASE_URL = "http://localhost:8080";  //api 공통 시작부


const email = document.getElementById('email');
const password = document.getElementById('password');
const userName = document.getElementById('name');
const registerButton = document.getElementById('registerButton');
// 회원가입 처리
registerButton.addEventListener('click',async function () {
    const email = email('email').value;
    const password = password('password').value;
    const userName = userName('name').value;

    if (!email || !password || !userName) {
        showResult('error', '모든 필드를 입력해주세요.');
        return;
    }

    if (password.length < 6) {
        showResult('error', '비밀번호는 최소 6자 이상이어야 합니다.');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password, userName })
        });

        const data = await response.json();

        if (!response.ok) {
            console.log("회원가입에 실패했습니다")
        }

    } catch (error) {
        console.error('회원가입 오류:', error);
        showResult('error', `오류: ${error.message}`);
        registerButton.disabled = false;
        registerButton.textContent = '회원가입';
    }
});