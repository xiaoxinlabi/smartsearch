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

    var status = $_GET['status'];
    if(status=='ok'){
        alert("操作成功!");
    } else if (status=='error'){
        alert("操作失败!");
    }

});