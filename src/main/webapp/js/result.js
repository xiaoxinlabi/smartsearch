/**
 * Created by taoyang on 11/6/15.
 */

// 分页功能
var currentPage = 1, pageSize = 10, maxPages = 15;

$(function(){

    init();

});

function init(){

    $("#headbar").load(function() {
        $("#headbar").contents().find("#headbar-login").on("click", function () {
            $("#login").modal({
                backdrop: 'static',
                keyboard: false
            });
        });

        $(".btn-close").on("click", function () {
            //$("#login").hide();
            $("#login").modal("toggle");
        })

    });

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

        window.open ('./file-preview.html?id=' + $(this).data('id') + '&type=' + $(this).data('type'), 'newwindow', 'height=800, width=1200, top=100, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');

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
            if($("#login").css("display")=="none"){
                $("#search-button").trigger("click");
            }
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
        "url": "rest/query?"+Math.random(),
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
            inHtml='为您找到相关结果约' + count + '个';
            ssResultCount.html(inHtml);

            var tdsFileList = $('#ss-result-list');
            tdsFileList.children().remove();
            list = response.data.datas;
            inHtml='';
            for(var i =0; i<list.length; i++){
                var record = list[i];

                //auth
                indexOperateItemHtml = '';
                permissions = record.permissions;
                if(permissions.indexOf("read") != -1){
                    indexOperateItemHtml += '<li><a class="ss-record-index-debug">定位</a></li>';
                }
                if(permissions.indexOf("write") != -1){
                    indexOperateItemHtml += '<li><a class="ss-record-index-delete">删除</a></li>';
                }

                index = record.index;
                type = record.type;
                id = record.id;
                indexDate = new Date();
                indexDate.setTime(record.timestamp);
                indexOperateHtml = "";
                if(permissions.indexOf("write") != -1){
                    indexOperateHtml =
                        '<div class="col-md-1">' +
                            //'<div class="dropdown pull-right ss-record-manage" ' + buttonStyle + ' >' +
                        '<div class="dropdown pull-right ss-record-manage" >' +
                        '<button class="btn btn-danger btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">' +
                        '管理' +
                        '<span class="caret"></span>' +
                        '</button>' +
                        '<ul class="dropdown-menu" data-index="' + index + '" data-id="' + id + '" data-type = "' + type + '">' +
                        indexOperateItemHtml +
                        '</ul>' +
                        '</div>' +
                        '</div>' +
                        '';
                }

                if(record.content!=null && record.content!="null" && record.content!=""){
                    content = record.content + "..." ;
                }else{
                    content = "";
                }

                if(index == "filefulltext"){
                    //文件
                    modifyDate = new Date();
                    modifyDate.setTime(record.lastModified);

                    if(record.type == "txt" || record.type == "doc" || record.type == "docx" || record.type == "xls" || record.type == "xlsx" || record.type == "ppt" || record.type == "pptx" || record.type == "pdf"){
                        titleLink = '<a class="ss-result-row-title ss-file-prev" data-id="' + record.id + '" data-type = "' + record.type + '">'+ record.fileName +'</a>';
                    }else{
                        titleLink = '<a class="ss-result-row-title ss-file-down" data-id="' + record.id + '" data-type = "' + record.type + '">'+ record.fileName +'</a>';
                    }

                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-11">' +
                        '<img src="./img/' + typeToIcon(record.type) + '" class="ss-icon-sm">' +
                        titleLink +
                        '</div>' +
                        indexOperateHtml +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<p class="ss-result-row-content">' +
                        content +
                        '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>';
                } else if (index == "address") {
                    //通讯录
                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-11">' +
                        '<img src="./img/' + typeToIcon(record.type) + '" class="ss-icon-sm">' +
                        //'<a class="ss-result-row-title">'+ record.chineseName +' / '+ record.englishName +' / ' + record.accountId + '</a>' +
                        '<a class="ss-result-row-title">'+ record.chineseName +' / '+ record.englishName + '</a>' +
                        '</div>' +
                        indexOperateHtml +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '姓名：' + record.chineseName +
                        '</div>' +
                        '<div class="col-md-8">' +
                        '别名：' + record.englishName +
                        '</div>' +
                        //'<div class="col-md-4">' +
                        //'工号：' + record.accountId +
                        //'</div>' +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '手机：' + (record.mobilePhone!=null ? record.mobilePhone : "") +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '座机：' + (record.fixedPhone!=null ? record.fixedPhone : "") +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '传真：' + (record.fax!=null ? record.fax : "") +
                        '</div>' +
                        '</div>' +
                        //'<div class="row">' +
                        //'<div class="col-md-4">' +
                        //'邮箱：' + record.email +
                        //'</div>' +
                        //'<div class="col-md-4">' +
                        //'QQ：' + record.qq +
                        //'</div>' +
                        //'</div>' +
                        '<div class="row">' +
                        '<div class="col-md-4">' +
                        '机构：' + record.organization +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '部门：' + record.department +
                        '</div>' +
                        '<div class="col-md-4">' +
                        '职位：' + (record.position!=null ? record.position : "") +
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
                        '<div class="col-md-11">' +
                        '<img src="./img/' + typeToIcon(record.type) + '" class="ss-icon-sm">' +
                        '<a href="' + record.url + '" target = "_blank" class="ss-result-row-title">' + record.title + '</a>' +
                        '</div>' +
                        indexOperateHtml +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<p class="ss-result-row-content">' +
                        content +
                        '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '';

                } else if (index == "rdbms") {
                    //关系型库
                    inHtml+='<div class="ss-record-row">' +
                        '<div class="row">' +
                        '<div class="col-md-11">' +
                        '<img src="./img/file_type_icon/readme.png" class="ss-icon-sm">' +
                        '<a class="ss-result-row-title" href="./law-preview.html?index='+ record.index + '&type='+ record.type + '&id='+ record.id + '" target="_blank">' + record.lawname + '</a>' +
                        '</div>' +
                        indexOperateHtml +
                        '</div>' +
                        '<div class="row">' +
                        '<div class="col-md-12">' +
                        '<p class="ss-result-row-content">' +
                        record.itemtitle + '<br>' +
                        content +
                        '</p>' +
                        '</div>' +
                        '</div>' +
                        '';

                }

            }
            tdsFileList.append(inHtml);

            var element = $('#ss-result-pagination');
            var totalPages = (response.data.totalPages <= maxPages) ? response.data.totalPages : maxPages;
            var numberOfPages = (response.data.totalPages <= maxPages) ? response.data.totalPages : maxPages;
            var currentPage = response.data.current;
            if(count==0){
                currentPage=1;
                totalPages=1;
                numberOfPages=1;
            }

            var options = {
                bootstrapMajorVersion:3,
                currentPage: currentPage,
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
            alert("删除成功，稍后生效。");
        } else{
            alert("没有相关权限！");
        }
        setTimeout("location.reload();",1000);
    });
}

function typeToIcon(filetype){

    switch(filetype){
        case "txt":
            return "type_icon/txt.png";
        case "doc":
            return "type_icon/doc.png";
        case "docx":
            return "type_icon/docx.png";
        case "xls":
            return "type_icon/xls.png";
        case "xlsx":
            return "type_icon/xlsx.png";
        case "ppt":
            return "type_icon/ppt.png";
        case "pptx":
            return "type_icon/pptx.png";
        case "pdf":
            return "type_icon/pdf.png";
        case "gd":
            return "type_icon/gd.png";
        case "gw":
            return "type_icon/gd.png";
        case "address":
            return "type_icon/address.png";
        case "website":
            return "type_icon/web.png";
        case "file":
            return "type_icon/file.png";
        case "law":
            return "file_type_icon/readme.png";
        default :
            return "type_icon/file.png";
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
            return "pdf"
        case "gd":
            return "gd/gw";
        case "gw":
            return "gd/gw";
        case "file":
            return "文件";
        case "address":
            return "通讯录";
        case "website":
            return "网页";
        case "law":
            return "内控合规";
        default :
            return "所有类型";
    }

}