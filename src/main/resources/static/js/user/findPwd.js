
// 이메일 초기화
		localStorage.removeItem("email");
		
		// 이메일 인증
		const inputUid = document.getElementById('uid');
		const inputEmail = document.getElementById('email');
		const authNumber = document.getElementById('auth-number');
		const btnEmail = document.getElementsByClassName('auth-email')[0];
		const btnNum = document.getElementsByClassName('btn-complete')[0];
		const divNum = document.getElementsByClassName('log-form-group')[2];
		const resultEmail = document.getElementsByClassName('result-email')[0];
		const emailTime = document.getElementsByClassName('p-timer')[0];
		let authValus = "";
		
		
		// 시간 변수
		let countTime = 0;
		let intervalCall;
	
		// 이메일 인증
		function authEmail() {
			const email = inputEmail.value;
			const uid = inputUid.value;
			
			
			fetch('/findPwd/sendEmail',{
				method: "POST",
				headers: {
					"Content-Type": "application/json;charset=UTF-8",
				},
				body: JSON.stringify({
				    email: email,
				    username: uid,
				}),
			}).then((response) => response.json())
			.then((data) => {
				
				const result = data.result;
				const username = data.username;
				
				if (result === 1) {
					authValus = username;
					alert('이메일이 발송되었습니다.');
					divNum.style.display = 'block';
				} else {
					alert('아이디 혹은 이메일을 찾을 수 없습니다.');
				}

			})
			.catch((error) => {
				alert('이메일 인증을 실패했습니다.');
			});
			
		}
		
		// 인증 코드 입력
		function EnterNumber() {
			const num = authNumber.value;
			let username = authValus.trim();
	
			
			if(emailTime.textContent === '0:00') {
				alert('입력 시간이 지났습니다. 다시 코드를 발급해주세요.');
				return;
			}
			
			fetch('/findPwd/authNumber?num=' + num + '&username=' + username,{
				method: "GET",
				headers: {
					"Content-Type": "application/json;charset=UTF-8",
				},
			}).then((response) => response.text())
			.then((data) => {
				if(data <= 0){
					alert("인증 코드를 다시 입력해주세요.");
				} else {
					alert("인증되었습니다.");
					closeTime();
					window.location.href='/user/findPwdChange';
				}
			})
			.catch((error) => {
				alert('인증 번호에 문제가 발생했습니다.');
			});
		}
		
		// 타이머 시작
		function time(time) {
		    countTime = time;
		    intervalCall = setInterval(alertFunc, 1000);
		}
		
		// 시간 끝내기
		function closeTime() {
		    clearInterval(intervalCall);
		}

		// 타이머 보이기
		function alertFunc() {
			let min = Math.floor(Math.max(0, countTime) / 60); // 음수인 경우 0으로 처리
		    let sec = Math.max(0, countTime) - (60 * min); // 음수인 경우 0으로 처리
		    if (sec > 9) {
		    	emailTime.textContent = min + ':' + sec;
		    } else {
		    	emailTime.textContent = min + ':0' + sec;
		    }
		    
		    if (countTime <= 0) {
		        clearInterval(intervalCall);
		    }
		    countTime--;
		}
		
		// 이메일 전송 버튼 클릭 후 time
		btnEmail.addEventListener("click", function() {
			time(180);
		});