


$(function () {
    $("#ss-login-btn").on("click", function () {
        submit();
    });

    //var doc=document,
    //    inputs=doc.getElementsByTagName('input'),
    //    supportPlaceholder='placeholder'in doc.createElement('input'),
    //
    //    placeholder=function(input){
    //        var text=input.getAttribute('placeholder'),
    //            defaultValue=input.defaultValue;
    //        if(defaultValue==''){
    //            input.value=text
    //        }
    //        input.onfocus=function(){
    //            if(input.value===text)
    //            {
    //                this.value=''
    //            }
    //        };
    //        input.onblur=function(){
    //            if(input.value===''){
    //                this.value=text
    //            }
    //        }
    //    };
    //
    //if(!supportPlaceholder){
    //    for(var i=0,len=inputs.length;i<len;i++){
    //        var input=inputs[i],
    //            text=input.getAttribute('placeholder');
    //        if(input.type==='text'&&text){
    //            placeholder(input)
    //        }
    //    }
    //}
    placeholderfun();
});

function submit() {
    var param = {
        username: $("#username").val(),
        password: $("#password").val()
    };
    $.ajax({
        type: "post",
        url: "rest/user/login",
        data: param,
        dataType: "json",
        success: function (data) {
            if ($("#username").val() == "") {
                alert("用户名不能为空!");
            } else if ($("#password").val() == "") {
                alert("密码不能为空!");
            } else if (data.status == "success") {
                window.location.href = "index.html";
            } else if (data.status == "error") {
                alert("用户名或密码错误!");
            }
        },
        error: function () {
            alert("调用失败....");
        }
    });
}

function placeholderfun(){

    if( !('placeholder' in document.createElement('input')) ){
        function GetStringNumValue(pxstr){
            return pxstr.substring(0,pxstr.length-2);
        }

        $('input[placeholder],textarea[placeholder]').each(function(){
            var $element = $(this),
                placeholder = $element.attr('placeholder');
            if($element.attr('type') != 'password'){//非密码
                if($element.val()===""){
                    $element.val(placeholder).addClass('placeholder');
                    $element.css('color','#ccc');
                }
                $element.focus(function(){
                    if($element.val()===placeholder){
                        $element.val("").removeClass('placeholder');
                        $element.css('color','#000');
                    }
                }).blur(function(){
                    if($element.val()===""){   //if($element.val()==="" &&  ($element.attr('type') != 'password')){
                        $element.val(placeholder).addClass('placeholder');
                        $element.css('color','#ccc');
                    }else if($element.val() == placeholder){
                        $element.css('color','#ccc');
                    }else{
                        $element.css('color','#000');
                    }
                }).closest('form').submit(function(){
                    if($element.val() === placeholder){
                        $element.val('');
                    }
                });
            }else{//密码框
                if (placeholder){
                    // 文本框ID
                    var elementId = $element.attr('id');
                    if (!elementId) {
                        var now = new Date();
                        elementId = 'lbl_placeholder' + now.getSeconds() + now.getMilliseconds();
                        $element.attr('id', elementId);
                    }
                }//end of if (placeholder)
                 // 添加label标签，用于显示placeholder的值
                var $label = $('<label>', {
                    html: $element.val() ? '' : placeholder,
                    'for': elementId,
                    css:{
                        position: 'absolute',
                        cursor: 'text',
                        color: '#cccccc',
                        fontSize: $element.css('fontSize'),
                        fontFamily: $element.css('fontFamily')
                    }
                }).insertAfter($element);
                // 绑定事件
                var _setPosition = function () {
                    $label.css({
                        marginTop:'-30px',
                        marginLeft:'10px'
                    });
                };
                var _resetPlaceholder = function () {
                    if ($element.val()) { $label.html(null); }
                    else {
                        _setPosition();
                        $label.html(placeholder);
                    }
                };
                _setPosition();
                $element.on('focus blur input keyup propertychange resetplaceholder', _resetPlaceholder);
            }
        });
    }
}