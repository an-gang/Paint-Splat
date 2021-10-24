$(document).ready(function () {
    var room = $("#roomNumber");
    var player = $("#scoreBoard");
    //var scores = $("#playergitScore");
    var playerNumber = 0;
    var playerscore;
    var playerHtml="";
    $.post("/checkRoomId", {}, function (RoomId) {
        console.log(RoomId);
        room.text(RoomId);
    });
    $.post("/getGame", {}, function (data) {
        console.log(data);
        console.log(data["scores"]);
        playerscore = data["scores"];
    });
    setInterval(function () {
            $.post("/countPlayer", {}, function (playerNum) {
                console.log(playerNum);
                if(playerNum>playerNumber){
                    playerHtml += "<span class='playerScore'>player"+ playerNum + "</span><br>";
                    $("#scoreBoard").append(playerHtml);
                }
                playerNumber=playerNum;
            });
        }
        , 5000);
});
