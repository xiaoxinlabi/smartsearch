/**
 * Created by taoyang on 11/25/15.
 */

$(function(){
    embedPDF();
});

function wait(){
    window.location.replace(location);
}

function embedPDF(){

    $("#pdf").load("./common/loading/index.html");

    id = $_GET['id'];
    type = $_GET['type'];

    var settings = {
        "async": true,
        "url": "rest/file/preview",
        "method": "GET",
        "headers": {
            "cache-control": "no-cache"
        },
        "data":{
            id:id,
            type:type
        }
    };

    $.ajax(settings).done(function (response) {
        var status = response.status;
        switch (status) {
            case 'ok':
                $("#pdf").children().remove();
                pdfPath = response.pdfPath;
                if(pdfPath!=null && pdfPath!=""){
                    var myPDF = new PDFObject({
                        url : pdfPath
                    }).embed('pdf');
                }
                break;
            case 'wait':
                setTimeout('wait()',3000);
                break;
            case 'error':
                alert("文件不支持");
                break;
            default :
                alert("未知异常");
                break;
        }
    });
}
