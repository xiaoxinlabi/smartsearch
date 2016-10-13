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
    $("#ss-upload").addClass("active");
    $("#ss-input").removeClass("active");
    $("#ss-upload-div").show();
    $("#ss-input-div").hide();
}

function inputAddress(){
    $("#ss-input").addClass("active");
    $("#ss-upload").removeClass("active");
    $("#ss-input-div").show();
    $("#ss-upload-div").hide();

}
