/**
 * Created by taoyang on 11/6/15.
 */

// 分页功能
var currentPage = 1, pageSize = 5;

//最大显示摘要
var maxHighlightedSize = 250;

$(function(){

    //init();

});

function init(){

    if($_GET['wd']!=null && $_GET['wd']!=""){
        $("#search-wd").val(decodeURIComponent($_GET['wd']));

        if($_GET['tp']!=null && $_GET['tp']!=""){
            $("#ss-file-type-selector").html(typeToName(decodeURIComponent($_GET['tp']))+' <span class="caret"></span>')
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

    $('#ss-result-list').on('click','.ss-record-index-debug', function () {

        alert($(this).closest('.dropdown-menu').data('id'));

    });

    $('#ss-result-list').on('click','.ss-record-index-delete', function () {
        index = $(this).closest('.dropdown-menu').data('index');
        type = $(this).closest('.dropdown-menu').data('type');
        id = $(this).closest('.dropdown-menu').data('id');
        deleteIndex(index, type, id);
    });

    $('#ss-result-list').on('click','.ss-file-prev', function () {

        window.open ('./preview.html?id=' + $(this).data('id') + '&type=' + $(this).data('type'), 'newwindow', 'height=800, width=1200, top=100, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');

    });

    $('#ss-result-list').on('click','.ss-file-down', function () {
        window.open ('rest/file/download?id=' + $(this).data('id') + '&type=' + $(this).data('type'), 'newwindow');
    });

    $('.ss-type-item').on('click', function () {
        $("#ss-file-type-selector").html($(this).text()+' <span class="caret"></span>')
            .val($(this).data('id'));
    });


    $('.ss-file-facet-list').on('click','.ss-file-facet-item', function () {
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
                id = record.id;
                indexDate = new Date();
                indexDate.setTime(record.timestamp);

                indexOperateHtml =
                    '<div class="col-md-4">' +
                    '<div class="dropdown pull-right ss-record-manage">' +
                    '<button class="btn btn-danger btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">' +
                    '管理' +
                    '<span class="caret"></span>' +
                    '</button>' +
                    '<ul class="dropdown-menu" data-index="' + index + '" data-id="' + id + '" data-type = "' + type + '">' +
                    '<li><a class="ss-record-index-debug">定位</a></li>' +
                    '<li><a class="ss-record-index-delete">删除</a></li>' +
                    '</ul>' +
                    '</div>' +
                    '</div>' +
                    '';

                if(record.content!=null){
                    content = record.content.length <= maxHighlightedSize ? record.content : record.content.substring(0,200)
                }

                if(index == "filefulltext"){
                    //文件
                    modifyDate = new Date();
                    modifyDate.setTime(record.lastModified);
                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-8">' +
                            //'<a class="ss-file-down" data-hdfspath="http://' + hdfsHost + ':50070/webhdfs/v1' + record.hdfsPath + '?op=OPEN"><h4><strong>'+ (record.highlightName ? record.highlightName:record.name) +'</strong></h4></a>' +
                        '<a class="ss-file-down" data-id="' + record.id + '" data-type = "' + record.type + '"><h4><strong>'+ record.fileName +'</strong></h4></a>' +
                        '</div>' +
                        indexOperateHtml +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-2">' +
                            //'<a class="ss-file-prev" data-hdfspath="http://' + hdfsHost + ':50070/webhdfs/v1' + record.hdfsPath + '?op=OPEN">' +
                        '<a class="ss-file-prev" data-id="' + record.id + '" data-type = "' + record.type + '">' +
                        '<img src="./img/' + typeToIcon(record.type) + '" class="ss-icon-md">' +
                        '</a>' +
                        '</div>' +
                        '<div class="col-md-10" class="ss-record-file-metadata">' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '文件大小：' + Math.ceil(record.size/1024) + 'KB' +
                        '</div>' +
                        '</div>' +
                        //'<div class="row">' +
                        //'<div class="col-md-12">' +
                        //'文件归属：' + record.owner +
                        //'</div>' +
                        //'</div>' +
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
                        //'<div class="row">' +
                        //'<div class="col-md-4">' +
                        //'在线预览：' +
                        //'<a class="ss-file-prev" data-id="' + record.id + '" data-type = "' + record.type + '">在线预览 </a>' +
                        //'</div>' +
                        //'<div class="col-md-4">' +
                        //'下载地址：' +
                        //'<a class="ss-file-down" data-id="' + record.id + '" data-type = "' + record.type + '">点击下载 </a>' +
                        //'</div>' +
                        //'</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<p>' +
                         content +
                        '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                } else if (index == "address") {
                    //通讯录
                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-8">' +
                        '<a><h4><strong><span>'+ record.chineseName +' / '+ record.englishName +' / ' + record.accountId + '</strong></h4></a>' +
                        '</div>' +
                        indexOperateHtml +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-2">' +
                        '<a>' +
                        '<img src="./img/type_icon/address.png" class="ss-icon-md">' +
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

                } else if (index == "website") {
                    //网站
                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-8">' +
                        '<a href="' + record.url + '"><h4><strong>' + record.title + '</strong></h4></a>' +
                        '</div>' +
                        indexOperateHtml +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-2">' +
                        '<a>' +
                        '<img src="./img/file_type_icon/url.png" class="ss-icon-md">' +
                        '</a>' +
                        '</div>' +
                        '<div class="col-md-10" class="ss-record-file-metadata">' +
                        //'<div class="row">' +
                        //'<div class="col-md-12">' +
                        //'标题：' + record.title +
                        //'</div>' +
                        //'</div>' +
                        //'<div class="row">' +
                        //'<div class="col-md-12">' +
                        //'关键字：' + record.keywords +
                        //'</div>' +
                        //'</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '描述：' + record.description +
                        '</div>' +
                        '</div>' +
                        '<p>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '索引时间：' + indexDate.toLocaleString() +
                        '</div>' +
                        '</div>' +
                        '<p>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '链接：<a href="' + record.url + '">点击访问</a>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<p>' +
                         content +
                        '</p>' +
                        '</div>' +
                        '</div>' +
                        '';

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

function deleteIndex(index, type, id){

    $("#ss-file-type-selector").prop("disabled","disabled");
    $("#search-button").button('loading');

    var settings = {
        "async": true,
        "url": "rest/index/delete",
        "method": "POST",
        "headers": {
            "cache-control": "no-cache"
        },
        "data":{
            index:index,
            type:type,
            id:id
        }
    };

    $.ajax(settings).done(function (response) {
        if (response.success == true) {
            setTimeout("location.reload();",1000);
        }
    });
}

function typeToIcon(filetype){

    switch(filetype){
        case "txt":
            return "file_type_icon/text.png";
        case "doc":
            return "file_type_icon/doc.png";
        case "docx":
            return "file_type_icon/docx.png";
        case "xls":
            return "file_type_icon/xls.png";
        case "xlsx":
            return "file_type_icon/xlsx.png";
        case "ppt":
            return "file_type_icon/ppt.png";
        case "pptx":
            return "file_type_icon/pptx.png";
        case "pdf":
            return "file_type_icon/pdf.png";
        case "address":
            return "type_icon/address.png";
        case "website":
            return "file_type_icon/url.png";
        case "file":
            return "type_icon/file.png";
        default :
            return "type_icon/all.png";
    }

}

function typeToName(filetype){

    switch(filetype){
        case "txt":
            return "txt";
        case "doc":
            return "doc(x)";
        case "ppt":
            return "ppt(x)";
        case "xls":
            return "xls(x)";
        case "pdf":
            return "pdf";
        case "address":
            return "通讯录";
        case "website":
            return "网页";
        default :
            return "所有类型";
    }

}