$(document).ready(function () {
    $.post("/checkRoomId",{},function (roomId) {
        $.post("/getGame",{roomId: roomId},function (data) {
            console.log(data);
        })
    })
});