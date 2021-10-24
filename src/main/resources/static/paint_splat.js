$(document).ready(function () {
    var sessionId;
    var players;
    var scores;
    var boardPositions;
    var currentPosition = 0;
    var step;
    var time;
    var paints;
    var isStart;

    $.post("/checkRoomId", {}, function (RoomId) {
        $("#roomNumber").text(RoomId);
    });
    $.post("/getPlayerId", {}, function (playerId) {
        sessionId = playerId
        $.post("/getGame", {}, function (data) {
            console.log(data);
            if (data.players[0] === sessionId) {
                $("#start").show();
            }
            players = data.players;
            scores = data.scores;
            time = data.time;
            renderBoard();
            boardPositions = data.boardPositions;
        });
    });

    function renderBoard() {
        var scoreBoard = $("#scoreBoard");
        scoreBoard.html("");
        for (var i = 0; i < players.length; i++) {
            if (players[i] === sessionId) {
                scoreBoard.append("<div class='mine'>Player " + (i + 1) + " : " + scores[0] + "</div>")
            } else {
                scoreBoard.append("<div>Player " + (i + 1) + " : " + scores[0] + "</div>")
            }
        }

        $("#timer").text("Time : " + (60 - Math.round(time / 1000)));
    }

    var gameUpdater = setInterval(updateGame, 50);

    function updateGame() {
        $.post("/getGame", {}, function (data) {
            players = data.players;
            scores = data.scores;
            time = data.time;
            isStart = data.start;
            step = data.step;
            renderBoard();
        });
    }

    $("#start").click(function () {
        $.post("/startGame", {})
    });


    var gameStarter = setInterval(function () {
        if (isStart) {
            moveBoard();
            window.clearInterval(gameStarter)
        }
    }, 50);

    var timeused = 0;

    function moveBoard() {
        var current = boardPositions[currentPosition];
        var target = boardPositions[currentPosition + 1];
        if (target) {
            var time = Math.round(calculateDistance(current, target) / step);
            console.log(time);
            $("#board").animate({top: target[0] + "%", left: target[1] + "%"}, time, function () {
                currentPosition++;
                timeused += time;
                console.log(boardPositions.length + "----" + currentPosition + "----" + timeused);
                moveBoard();
            });
        }
    }

    function calculateDistance(point1, point2) {
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]));
    }
});



