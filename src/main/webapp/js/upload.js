/**
 * Created by Pauline on 16/10/13.
 */

function submitFile() {
    //if (document.getElementById('file').value == '') {
    //    alert("请选择文件!");
    //} else {
    //    document.forms["form"].submit();
    //}
}

function uploadFile(){
    $("#ss-file").addClass("active");
    $("#ss-address").removeClass("active");
    $("#ss-website").removeClass("active");
    $("#ss-file-div").show();
    $("#ss-address-div").hide();
    $("#ss-website-div").hide();
}

function inputAddress(){
    $("#ss-address").addClass("active");
    $("#ss-file").removeClass("active");
    $("#ss-website").removeClass("active");
    $("#ss-address-div").show();
    $("#ss-file-div").hide();
    $("#ss-website-div").hide();

}

function addWebsite(){
    $("#ss-website").addClass("active");
    $("#ss-file").removeClass("active");
    $("#ss-address").removeClass("active");
    $("#ss-website-div").show();
    $("#ss-address-div").hide();
    $("#ss-file-div").hide();
}