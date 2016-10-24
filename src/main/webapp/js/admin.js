/**
 * Created by Pauline on 16/10/13.
 */

$(function(){
    $(".nav.nav-sidebar").on("click","li",function(){
        $(this).parent().children().removeClass("active");
        $(this).addClass("active");
    })
});

function submitFile() {
    //if (document.getElementById('file').value == '') {
    //    alert("请选择文件!");
    //} else {
    //    document.forms["form"].submit();
    //}
}

function uploadFile(){
    $("#ss-file-panel").show();
    $("#ss-address-panel").hide();
    $("#ss-website-panel").hide();
}

function inputAddress(){
    $("#ss-address-panel").show();
    $("#ss-file-panel").hide();
    $("#ss-website-panel").hide();
}

function addWebsite(){
    $("#ss-website-panel").show();
    $("#ss-address-panel").hide();
    $("#ss-file-panel").hide();
}

function indexManagement(){
    $("#ss-index-panel").show();
    $("#ss-address-panel").hide();
    $("#ss-file-panel").hide();
}
