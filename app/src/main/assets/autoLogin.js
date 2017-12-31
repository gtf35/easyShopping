setTimeout(function(){

    var username = document.getElementById('TPL_username_1');
    username.focus();
    username.value = "oShine";
    var password =   document.getElementById('TPL_password_1');
    password.focus();
    password.value = "oShine";
    var submitStatic = document.getElementById("J_SubmitStatic");
    submitStatic.focus();
    setTimeout(function(){
        //检测是否需要安全验证
        var noCaptcha = document.getElementById("nocaptcha");
        if(noCaptcha && noCaptcha.className == "nc-container tb-login"
            && noCaptcha.style.display !="block") {
            var submitStatic = document.getElementById("J_SubmitStatic");
            if(submitStatic) submitStatic.click();
        }
    },2000);


},3000);
