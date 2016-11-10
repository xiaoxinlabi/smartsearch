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


    $(".ss-index-init-list").on("click",".ss-index-init-btn",function(){
        initIndex($(this).data("type"));
    });

});

function initIndex(type){

    if(confirm("确定删除吗？（警告：这将删除所有相关类型索引）")){
        url = "rest/" + type + "/init";
        $.ajax({
            type: "post",
            url: url,
            dataType: "json",
            success: function (data) {
                if (data.success == true) {
                    alert("初始化成功!");
                } else {
                    alert("初始化失败!");
                }
            },
            error: function () {
                alert("调用失败....");
            }
        });
    }
}

