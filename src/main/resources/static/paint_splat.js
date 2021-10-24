$(document).ready(function () {
    var room = $("#roomNumber");
    var player = $("#scoreBoard");
    var scores = $("#playergitScore");
    var playerNum = 0;
    var playerscore;
    $.post("/getGame", {}, function (data) {
        console.log(data);
        console.log(data["scores"]);
        playerscore = data["scores"];
    });
    setInterval(function () {
            $.post("/countPlayer", {}, function (playerNum) {
                playerNum = playerNum;
                console.log(playerNum);
            });
        }
        , 5000);
});
