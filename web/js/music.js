getMusic();
var MusicList = new Array();


/**
 * 侦查附件上传情况 ,这个方法大概0.05-0.1秒执行一次
 */
function onprogress(evt){
    var loaded = evt.loaded;     //已经上传大小情况
    var tot = evt.total;      //附件总大小
    var per = Math.floor(100*loaded/tot);  //已经上传的百分比
    $("#toast").html( per +"%" );
}



function upLoad() {
    $("#toast").fadeIn(1000) ;
    var formData = new FormData($('#uploadForm')[0]);
    $.ajax({
        type: 'post',
        url: "http://jun:8080/Upload",
        data: formData,
        cache: false,
        processData: false,
        contentType: false,
        //这里我们先拿到jQuery产生的 XMLHttpRequest对象，为其增加 progress 事件绑定，然后再返回交给ajax使用
        xhr: function(){
            var xhr = $.ajaxSettings.xhr();
            if(onprogress && xhr.upload) {
                xhr.upload.addEventListener("progress" , onprogress, false);
                return xhr;
            }
        },
        success: function (res) {
            $("#toast").fadeOut(1000);
            getMusic() ;
        },
        error: function (e) {

        },
        complete: function () {

        }
    })
}


function addListener() {
    var mList = document.getElementsByClassName("musicBean");
    for (var i = 0; i < mList.length; i++) {
        let it = i;
        mList[i].onclick = function () {
            var myVideo = document.getElementsByTagName('audio')[0];
            myVideo.pause();
            document.getElementById("cd").classList.add("rotate");
            myVideo.src = MusicList[it];
            myVideo.play();
        }
    }
}

function getMusic() {

    var content = "";
    $.ajax({
        type: "get",
        cache: true,
        url: "http://jun:8080/GetMusic",
        async: true,
        success: function (res) {
            res = JSON.parse(res);
            for (var i = 0; i < res.length; i++) {
                content += "<div class=\"musicBean\"><span>" + res[i].name + "</span></div>";
                MusicList[i] = res[i].url;
            }
            $("#list").html(content);
            addListener();
        },
        error: function (e) {

        },
        complete: function () {

        }
    })
}


function playPause(e) {
    var myVideo = document.getElementsByTagName('audio')[0];
    if (myVideo.paused) {
        e.classList.add("rotate");
        myVideo.play();
    } else {
        e.classList.remove("rotate");
        myVideo.pause();
    }
}