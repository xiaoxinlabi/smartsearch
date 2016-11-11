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

    $(".ss-cache-clear-list").on("click",".ss-cache-clear-btn",function(){
        deleteCache($(this).data("type"));
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
                    alert("操作成功!");
                } else {
                    alert("操作失败!");
                }
            },
            error: function () {
                alert("调用失败....");
            }
        });
    }
}

function deleteCache(type){
    if(confirm("确定删除吗？（警告：这将影响首次下载/预览速度）")){
        url = "rest/cache/" + type;
        $.ajax({
            type: "delete",
            url: url,
            dataType: "json",
            success: function (data) {
                if (data.success == true) {
                    alert("操作成功!");
                } else {
                    alert("操作失败!");
                }
            },
            error: function () {
                alert("调用失败....");
            }
        });
    }
}

