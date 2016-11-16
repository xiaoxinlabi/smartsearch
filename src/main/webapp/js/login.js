
$(function(){
    $("#ss-login-btn").on("click",function(){
        submit();
    });
});

function submit() {
    var param = {
        username: $("#username").val(),
        password: $("#password").val()
    };
    $.ajax({
        type: "post",
        url: "rest/user/login",
        data: param,
        dataType: "json",
        success: function (data) {
            if (data.status == "success") {
                window.location.href = "index.html";
            } else if (data.status == "error") {
                alert("用户名或密码错误!");
            }
        },
        error: function () {
            alert("调用失败....");
        }
    });
}