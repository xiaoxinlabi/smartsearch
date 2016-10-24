/**
 * Created by Pauline on 16/10/13.
 */

$(function(){

    $(".nav.nav-sidebar").on("click","li",function(){
        $(this).parent().children().removeClass("active");
        $(this).addClass("active");

        $(".ss-panel").hide();
        $("#" + this.id +"-panel").show();

    })



});

function submitFile() {
    //if (document.getElementById('file').value == '') {
    //    alert("请选择文件!");
    //} else {
    //    document.forms["form"].submit();
    //}
}