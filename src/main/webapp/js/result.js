/**
 * Created by taoyang on 11/6/15.
 */

// 分页功能
var currentPage = 1, pageSize = 5;

$(function(){

    init();

});

function init(){

    if($_GET['wd']!=null && $_GET['wd']!=""){
        $("#search-wd").val(decodeURIComponent($_GET['wd']));

        if($_GET['tp']!=null && $_GET['tp']!=""){
            $("#ss-file-type-selector").html(decodeURIComponent($_GET['tp'])+' <span class="caret"></span>')
                .val(decodeURIComponent($_GET['tp']));
        }
        refresh();
    }

    $("#search-button").on('click',function(){
        if($("#search-wd").val()!=null && $("#search-wd").val()!=""){
            restore();
        }else{
            var options = {
                content : '搜索内容不允许为空!',
                placement : 'bottom'
            }
            $("#search-wd").popover(options).popover('show');
        }
    });


    $('#ss-result-list').on('click','.ss-file-prev', function () {

        window.open ('./preview.html?id=' + $(this).data('id') + '&type=' + $(this).data('type'), 'newwindow', 'height=800, width=1200, top=100, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');

    });

    $('#ss-result-list').on('click','.ss-file-down', function () {
        window.open ('rest/file/download?id=' + $(this).data('id') + '&type=' + $(this).data('type'), 'newwindow');
    });

    $('.ss-file-type-item').on('click', function () {
        $("#ss-file-type-selector").html($(this).text()+' <span class="caret"></span>')
            .val($(this).data('id'));
    });


    $('#ss-file-facet-list').on('click','.ss-file-facet-item', function () {
        window.location.href='./result.html?' +
            'wd=' + $("#search-wd").val() + '&' +
            'tp=' + $(this).data('id') + '&' +
            '';
    });

    $(document).keyup(function(event){
        if(event.keyCode ==13){
            $("#search-button").trigger("click");
        }
    });

}

function restore(){
    window.location.href='./result.html?' +
        'wd=' + $("#search-wd").val() + '&' +
        'tp=' + $("#ss-file-type-selector").val() + '&' +
        '';
}

function refresh(){
    getResult($("#search-wd").val(), $("#ss-file-type-selector").val(), currentPage, pageSize);
}

function getResult(keyword, type, currentPage, pageSize){

    $("#ss-file-type-selector").prop("disabled","disabled");
    $("#search-button").button('loading');

    var settings = {
        "async": true,
        "url": "rest/query",
        "method": "GET",
        "headers": {
            "cache-control": "no-cache"
        },
        "data":{
            keyword:keyword,
            type:type,
            currentPage:currentPage,
            pageSize:pageSize,
        }
    };

    $.ajax(settings).done(function (response) {
        if(response.success==true){

            $("#ss-file-type-selector").removeProp("disabled");
            $("#search-button").button('reset');

            //alert(JSON.stringify(response.data));

            var ssResultCount = $('#ss-result-count');
            ssResultCount.children().remove();
            var count = response.data.count;
            inHtml='智搜为您找到' + count + '个相关结果';
            ssResultCount.html(inHtml);

            var tdsFileList = $('#ss-result-list');
            tdsFileList.children().remove();
            list = response.data.datas;
            inHtml='';
            for(var i =0; i<list.length; i++){
                var record = list[i];

                index = record.index;
                type = record.type;

                if(index == "filefulltext"){
                    //文件
                    modifyDate = new Date();
                    modifyDate.setTime(record.lastModified);
                    indexDate = new Date();
                    indexDate.setTime(record.timestamp);
                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                            //'<a class="ss-file-down" data-hdfspath="http://' + hdfsHost + ':50070/webhdfs/v1' + record.hdfsPath + '?op=OPEN"><h4><strong>'+ (record.highlightName ? record.highlightName:record.name) +'</strong></h4></a>' +
                        '<a class="ss-file-down" data-id="' + record.id + '" data-type = "' + record.type + '"><h4><strong>'+ record.fileName +'</strong></h4></a>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-2">' +
                            //'<a class="ss-file-prev" data-hdfspath="http://' + hdfsHost + ':50070/webhdfs/v1' + record.hdfsPath + '?op=OPEN">' +
                        '<a class="ss-file-prev" data-id="' + record.id + '" data-type = "' + record.type + '">' +
                        '<img src="./img/file_type_icon/' + typeToIcon(record.type) + '" class="ss-icon-md">' +
                        '</a>' +
                        '</div>' +
                        '<div class="col-md-10" class="ss-record-file-metadata">' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '文件大小：' + Math.ceil(record.size/1024) + 'KB' +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '文件归属：' + record.owner +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '修改时间：' + modifyDate.toLocaleString() +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '索引时间：' + indexDate.toLocaleString() +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '在线预览：' +
                        '<a class="ss-file-prev" data-id="' + record.id + '" data-type = "' + record.type + '">在线预览 </a>' +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '下载地址：' +
                        '<a class="ss-file-down" data-id="' + record.id + '" data-type = "' + record.type + '">点击下载 </a>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<p>' + record.content + '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                } else if (index == "address") {
                    //通讯录
                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<a><h4><strong><span>'+ record.chineseName +' / '+ record.englishName +' / ' + record.accountId + '</strong></h4></a>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-2">' +
                        '<a>' +
                        '<img src="./img/result_icon/address.png" class="ss-icon-md">' +
                        '</a>' +
                        '</div>' +
                        '<div class="col-md-10" class="ss-record-file-metadata">' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '姓名：' + record.chineseName +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '别名：' + record.englishName +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '工号：' + record.accountId +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '手机：' + record.mobilePhone +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '座机：' + record.fixedPhone +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '邮箱：' + record.email +
                        '</div>' +
                        '<div class="col-md-4">' +
                        'QQ：' + record.qq +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '机构：' + record.organization +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '部门：' + record.department +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '职位：' + record.position +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '通讯地址：' + record.address +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '';

                } else if (index == "siteNavigation") {
                    //网址导航
                }

            }
            tdsFileList.append(inHtml);


            var element = $('#ss-result-pagination');

            var totalPages = (response.data.totalPages <= 5) ? response.data.totalPages : 5;

            var numberOfPages = (response.data.totalPages <= 5) ? response.data.totalPages : 5;

            var options = {
                bootstrapMajorVersion:3,
                currentPage: response.data.current,
                totalPages: totalPages,
                numberOfPages : numberOfPages,
                onPageClicked: function(e,originalEvent,type,page){
                    getResult($("#search-wd").val(), $("#ss-file-type-selector").val(), page, pageSize);
                    //alert("Page item clicked, type: "+type+" page: "+page);
                }
            }

            element.bootstrapPaginator(options);

        }
    });


}

function typeToIcon(filetype){

    switch(filetype){
        case "txt":
            return "text.png";
        case "doc":
            return "doc.png";
        case "docx":
            return "docx.png";
        case "xls":
            return "xls.png";
        case "xlsx":
            return "xlsx.png";
        case "ppt":
            return "ppt.png";
        case "pptx":
            return "pptx.png";
        case "pdf":
            return "pdf.png";
        default :
            return "ini.png";
    }

}