/* 
 * drag 1.0
 * create by tony@jentian.com
 * date 2015-08-18
 * 拖动滑块
 */
(function($){
    $.fn.drag = function(options){
        var x, drag = this, isMove = false, defaults = {
        };
        var options = $.extend(defaults, options);
        //添加背景，文字，滑块
        var html = '<div class="drag_bg"></div>'+
                    '<div class="drag_text" onselectstart="return false;" unselectable="on">拖动滑块验证</div>'+
                    '<div class="handler handler_bg"></div>';
        this.append(html);
        
        var handler = drag.find('.handler');
        var drag_bg = drag.find('.drag_bg');
        var text = drag.find('.drag_text');
        var maxWidth = drag.width() - handler.width();  //能滑动的最大间距
        
        //鼠标按下时候的x轴的位置
        handler.mousedown(function(e){
            isMove = true;
            x = e.pageX - parseInt(handler.css('left'), 10);
        });
        
        //鼠标指针在上下文移动时，移动距离大于0小于最大间距，滑块x轴位置等于鼠标移动距离
        $(document).mousemove(function(e){
            var _x = e.pageX - x;
            if(isMove){
                if(_x > 0 && _x <= maxWidth){
                    handler.css({'left': _x});
                    drag_bg.css({'width': _x});
                }else if(_x > maxWidth){  //鼠标指针移动距离达到最大时清空事件
                    dragOk();
                }
            }
        }).mouseup(function(e){
            isMove = false;
            var _x = e.pageX - x;
            if(_x < maxWidth){ //鼠标松开时，如果没有达到最大距离位置，滑块就返回初始位置
                handler.css({'left': 0});
                drag_bg.css({'width': 0});
            }
        });
        
        //清空事件
        function dragOk(){
            handler.removeClass('handler_bg').addClass('handler_ok_bg');
            text.text('验证通过');
            drag.css({'color': '#fff'});
            handler.unbind('mousedown');
            $(document).unbind('mousemove');
            $(document).unbind('mouseup');
        }
    };
})(jQuery);


















var SignSuccessName ;

function login() {
    var data = {
        name: $("#name").val(),
        password: $("#password").val()
    }
    $.ajax({
        data: data,
        type: "post",
        dataType: "json",
        cache: false,
        url: "http://jun:8080/Login",
        async: true,
        success: function (res) {
            if(res.status == "success"){
                window.location.href = "http://jun:8080/music.html";
            }
            else {
                $("#toast").html("登录失败") ;
                $("#toast").fadeIn() ;
                $("#toast").fadeOut() ;
            }

        },
        error: function (e) {

        },
        complete: function () {

        }
    })
}

function sign() {
    var patternName = /^[0-9a-zA-Z]+$/;
    var patternNPsd = /^[0-9a-zA-Z]+$/;

    if (!patternName.test($("#sign_name").val())){
        $("#toast").html("用户名只能包含数字和字母") ;
        $("#toast").fadeIn() ;
        $("#toast").fadeOut() ;
        return ;
    }
    SignSuccessName = $("#sign_name").val() ;
    if ($("#sign_password").val()==""){
        $("#toast").html("请输入密码") ;
        $("#toast").fadeIn() ;
        $("#toast").fadeOut() ;
        return ;
    }
    if ($("#sign_password").val().length<5){
        $("#toast").html("密码必须大于5位") ;
        $("#toast").fadeIn() ;
        $("#toast").fadeOut() ;
        return ;
    }
    if ($("#sign_repeat_password").val()!=$("#sign_password").val()){
        $("#toast").html("两次密码必须相同") ;
        $("#toast").fadeIn() ;
        $("#toast").fadeOut() ;
        return ;
    }

    if(!patternNPsd.test($("#sign_password").val())){
        $("#toast").html("密码只能包含数字和字母") ;
        $("#toast").fadeIn() ;
        $("#toast").fadeOut() ;
        return ;
    }
    var data = {
        name: $("#sign_name").val(),
        password: $("#sign_password").val(),
        sex: $("#sign_sex").val()
    }
    $.ajax({
        data: data,
        type: "post",
        dataType: "json",
        cache: false,
        url: "http://jun:8080/Sign",
        async: true,
        success: function (res) {
            if(res.status == "success"){
                $("#toast").html("注册成功") ;
                $("#toast").fadeIn() ;
                $("#toast").fadeOut() ;
                $("#name").val(SignSuccessName) ;
                show_login() ;
            }
            else{
                $("#toast").html("注册失败") ;
                $("#toast").fadeIn() ;
                $("#toast").fadeOut() ;
            }
        },
        error: function (e) {

        },
        complete: function () {

        }
    })

}

function show_sign() {
    $("#login-wrap").hide(1000);
    $("#sign-wrap").show(1000);
}

function show_login() {
    $("#login-wrap").show(1000);
    $("#sign-wrap").hide(1000);
}



$('#drag').drag();
