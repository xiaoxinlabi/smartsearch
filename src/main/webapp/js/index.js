/**
 * Created by taoyang on 11/6/15.
 */

// 分页功能
var currentPage = 1, pageSize = 10;

$(function(){
    init();
});

function init(){

    $("#search-button").on('click',function(){

        if($("#search-wd").val()!=null && $("#search-wd").val()!=""){
            window.location.href='./result.html?' +
                'wd=' + $("#search-wd").val() + '&' +
                'tp=' + $("#ss-file-type-selector").val() + '&' +
                '';
        }else{
            var options = {
                content : '搜索内容不允许为空!',
                placement : 'bottom'
            }
            $("#search-wd").popover(options).popover('show');
        }

    });


    $('.ss-file-type-item').on('click', function () {
        $("#ss-file-type-selector").html($(this).text()+' <span class="caret"></span>')
            .val($(this).data('id'));
    });

    $(document).keyup(function(event){
        if(event.keyCode ==13){
            $("#search-button").trigger("click");
        }
    });

}