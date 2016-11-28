/**
 * Created by taoyang on 11/25/15.
 */

$(function(){
    embedContent();
});

function embedContent(){

    index = $_GET['index'];
    type = $_GET['type'];
    id = $_GET['id'];

    var settings = {
        "async": true,
        "url": "rest/index/get",
        "method": "GET",
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
        if(response.success==true){
            $("#lawname").html(response.data.lawname);
            $("#itemtitle").html(response.data.itemtitle);
            $("#itemcontent").html(response.data.itemcontent);
        }
    });
}
