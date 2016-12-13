/**
 * Created by taoyang on 11/6/15.
 */

// 分页功能
var currentPage = 1, pageSize = 10, maxPages = 15;

$(function(){

    init();

});

function init(){

    $('#ss-result-list').on('click','.ss-record-index-delete', function () {
        index = $(this).parent().data('index');
        type = $(this).parent().data('type');
        id = $(this).parent().data('id');

        if(confirm("确定删除吗？")){
            deleteIndex(index, type, id);
        }
    });

    $('#ss-result-list').on('click','.ss-record-index-share', function () {
        index = $(this).parent().data('index');
        type = $(this).parent().data('type');
        id = $(this).parent().data('id');

        if(confirm("确定共享吗？")){
            shareIndex(index, type, id);
        }
    });

    $('#ss-result-list').on('click','.ss-record-index-noshare', function () {
        index = $(this).parent().data('index');
        type = $(this).parent().data('type');
        id = $(this).parent().data('id');

        if(confirm("确定取消共享吗？")){
            noShareIndex(index, type, id);
        }
    });

    $('#ss-result-list').on('click','.ss-file-down', function () {
        window.open ('rest/file/download?id=' + $(this).data('id') + '&type=' + $(this).data('type'), 'newwindow');
    });

    refresh();

}


function refresh(){
    getResult(currentPage, pageSize);
}

function getResult(currentPage, pageSize){

    var settings = {
        "async": true,
        "url": "rest/file/queryMine?"+Math.random(),
        "method": "GET",
        "headers": {
            "cache-control": "no-cache"
        },
        "data":{
            currentPage:currentPage,
            pageSize:pageSize
        }
    };

    $.ajax(settings).done(function (response) {
        if(response.success==true){

            //alert(JSON.stringify(response.data));

            var count = response.data.count;

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

                if(record.content!=null && record.content!="null" && record.content!=""){
                    content = record.content + "..." ;
                }else{
                    content = "";
                }

                if(index == "filefulltext"){
                    //文件
                    modifyDate = new Date();
                    modifyDate.setTime(record.lastModified);

                    if(record.group.indexOf("public")<0){
                        shareImg = ' <img src="./img/type_icon/noshare.png" class="ss-icon-sm ss-record-index-share">';
                    }else{
                        shareImg = ' <img src="./img/type_icon/share.png" class="ss-icon-sm ss-record-index-noshare">';
                    }

                    indexDate = new Date(parseInt(record.timestamp)).toLocaleString();

                    titleLink = '<img src="./img/' + typeToIcon(record.type) + '" class="ss-icon-sm">' +
                        '<span class="ss-result-row-title ss-file-down" data-id="' + record.id + '" data-type = "' + record.type + '"> '+ record.fileName +'</span>'
                        + shareImg +
                        ' <img src="./img/type_icon/rubbish.png" class="ss-icon-sm ss-record-index-delete">' +
                        '<span class="ss-result-row-timestamp">上传于 '+ indexDate + '</span>';

                    inHtml+='<a href="#" class="list-group-item">' +
                        '<h5 class="list-group-item-heading" data-id="' + record.id + '" data-type = "' + record.type + '" data-index = "'+ record.index +'">' +
                        titleLink +
                        '</h5>' +
                        '<p class="list-group-item-text">' +
                        content +
                        '</p>' +
                        '</a>';
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
                    getResult(page, pageSize);
                    //alert("Page item clicked, type: "+type+" page: "+page);
                }
            }

            element.bootstrapPaginator(options);

        }
    });

}

function deleteIndex(index, type, id){

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

function shareIndex(index, type, id){

    var settings = {
        "async": true,
        "url": "rest/index/share",
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
            alert("共享成功，稍后生效。");
        } else{
            alert("没有相关权限！");
        }
        setTimeout("location.reload();",1000);
    });
}

function noShareIndex(index, type, id){

    var settings = {
        "async": true,
        "url": "rest/index/noshare",
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
            alert("取消共享成功，稍后生效。");
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