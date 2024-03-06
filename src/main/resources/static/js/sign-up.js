function validateForm() {
    // 입력 필드 값 가져오기
    var userId = document.forms["signUp-form"]["userId"].value;
    var password = document.forms["signUp-form"]["userPassword"].value;
    var re_pass = document.forms["signUp-form"]["re_pass"].value;
    var email = document.forms["signUp-form"]["email"].value;
    var nickname = document.forms["signUp-form"]["nickname"].value;

    // 아이디가 영문으로 시작하는지 확인하는 정규 표현식
    var userICheckIng = /^[A-Za-z]/;

    // 영문, 숫자, 밑줄(_)만 사용할 수 있는지 확인하는 정규 표현식
    var userIdCheckAll = /^[A-Za-z0-9_]+$/;

    // 이메일 형식
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    // 비밀번호 형식 : 6자 이상 20자 이하, 숫자와 특수문자 필수 포함
    var passwordPolicyRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&]).{6,20}$/;

    // ===================== 아이디 검증 =====================

    if(!userId){
        alert("아이디를 입력하세요.")
        return false;
    }
    if (userId.search(/\s/) !== -1) {
        alert("아이디에 빈 칸을 포함 할 수 없습니다.");
        return false;
    }

    if (userId.length < 3 || userId.length > 11) {
        alert("아이디는 4자 이상 10자 이하로 설정해주세요.");
        return false;
    }
    if (!userICheckIng.test(userId)) {
        alert("아이디는 영문으로 시작해야 합니다.");
        return false;
    }
    if (!userIdCheckAll.test(userId)) {
        alert("영문, 숫자, 밑줄(_)만 사용할 수 있습니다.")
        return false;
    }

    // ===================== 이메일 형식 검증 =======================
    if(!emailRegex){
        alert("이메일을 입력하세요.")
        return false;
    }
    if (!emailRegex.test(email)) {
        alert("유효한 이메일 주소를 입력하세요.");
        return false;
    }

    // ===================== 비밀번호 정책 검증 =====================
    if(!password){
        alert("비밀번호를 입력하세요.")
        return false;
    }
    if (!passwordPolicyRegex.test(password)) {
        alert("비밀번호는 최소 6자 이상 20자 이하이며, 영문, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.");
        return false;
    }
    if (password.search(/\s/) !== -1) {
        alert("비밀번호에 빈 칸을 포함 할 수 없습니다.");
        return false;
    }

    if(!re_pass){
        alert("비밀번호를 확인을 위한 재입력을 해야 합니다..")
        return false;
    }

    // 비밀번호 일치 검증
    if (password !== re_pass) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    // ===================== 닉네임 검증 (예: 최소 3자, 최대 10자) =====================
    if(!nickname){
        alert("닉네임을 입력하세요.")
        return false;
    }
    if (nickname.search(/\s/) !== -1) {
        alert("닉네임에 빈 칸을 포함 할 수 없습니다.");
        return false;
    }
    if (nickname.length < 3 || nickname.length > 10) {
        alert("닉네임은 3자 이상 10자 이하로 설정해주세요.");
        return false;
    }

    // 모든 검증 통과
    return true;
}
