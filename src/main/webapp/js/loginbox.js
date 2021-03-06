$(function () {
    $("#ss-login-btn").on("click", function () {
        submit();
    });

    $(document).keyup(function(event){
        if(event.keyCode ==13){
            if($("#login").css("display")!="none"){
                $("#ss-login-btn").trigger("click");
            }
        }
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
            if ($("#username").val() == "") {
                alert("用户名不能为空！");
            } else if ($("#password").val() == "") {
                alert("密码不能为空！");
            } else if (data.status == "success") {
                location.reload();
            } else if (data.status == "error") {
                alert("用户名或密码错误！");
            }
        },
        error: function () {
            alert("调用失败....");
        }
    });
}

function loginBox(){
    $("#headbar").contents().find("#headbar-login").on("click", function () {
        $("#login").modal({
            backdrop: 'static',
            keyboard: false
        });
    });

    $(".btn-close").on("click", function () {
        //$("#login").hide();
        $("#login").modal("toggle");
    });
}