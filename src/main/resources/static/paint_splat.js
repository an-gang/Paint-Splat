$(document).ready(function () {
    var room = $("#roomNumber");
    var player = $("#scoreBoard");
    var scores = $("#playerScore");
    $.post("/checkRoomId",{},function (roomId) {
        var roomid = roomId;
        room.text(roomid);
        var playerid = 0;
        var playerscore;
        $.post("/getGame",{roomId: roomId},function (data) {
            console.log(data);
            console.log(data["scores"]);
            playerid = data["players"][0];
            playerscore = data["scores"];
            player.text(playerid);
            scores.text(playerscore);
        });
    })
});