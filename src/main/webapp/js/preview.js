/**
 * Created by taoyang on 11/25/15.
 */

$(function(){
    embedPDF();
});

function wait(){
    window.location.replace(location)
}

function embedPDF(){

    if(pdfPath=="wait"){

        $("#pdf").load("./common/loading/index.html");

        setTimeout('wait()',3000);

        //alert("文件正在转换");

    } else if(pdfPath!=null && pdfPath!=""){

        var myPDF = new PDFObject({

            url : "." + pdfPath

            //url :  "./cache/pdf/20151117061046/个人外部训练申请表.pdf"

        }).embed('pdf');

    }else{

        alert("文件预览异常");

    }

}
