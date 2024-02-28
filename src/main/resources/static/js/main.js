function validateForm() {
    // 입력 필드 값 가져오기
    var userId = document.forms["registerForm"]["userId"].value;
    var password = document.forms["registerForm"]["password"].value;
    var re_pass = document.forms["registerForm"]["confirmPassword"].value;
    var email = document.forms["registerForm"]["email"].value;
    var nickname = document.forms["registerForm"]["nickname"].value;

    // 입력 필드 존재 여부 검증
    if (!userId || !password || !re_pass || !email || !nickname) {
        alert("모든 필드를 채워주세요.");
        return false;
    }

    // 이메일 형식 검증
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("유효한 이메일 주소를 입력하세요.");
        return false;
    }

    // 비밀번호 정책 검증
    var passwordPolicyRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/; // 최소 8자, 대소문자 및 숫자 1개 이상
    if (!passwordPolicyRegex.test(password)) {
        alert("비밀번호는 최소 8자 이상이며, 대문자, 소문자, 숫자를 각각 1개 이상 포함해야 합니다.");
        return false;
    }

    // 비밀번호 일치 검증
    if (password !== re_pass) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    // 닉네임 검증 (예: 최소 3자, 최대 10자)
    if (nickname.length < 3 || nickname.length > 10) {
        alert("닉네임은 3자 이상 10자 이하로 설정해주세요.");
        return false;
    }

    // 모든 검증 통과
    return true;
}
