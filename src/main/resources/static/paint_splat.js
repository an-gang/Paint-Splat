$(document).ready(function () {
    var roomid = $.post("/checkRoomId",{},function (roomId) {
        $.post("/getGame",{roomId: roomId},function (data) {
            console.log(data);
        })
    var room = $("#roomNumber");
    room.text(roomid);
    })
});