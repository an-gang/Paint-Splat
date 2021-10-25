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
        $("#roomNumber").text("Room Number: " + RoomId);
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
                scoreBoard.append("<div>Player " + (i + 1) + ": " + scores[0] + "</div>")
            }
        }
        $("#timer").text("Time: " + (60 - Math.round(time / 1000)));
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
            if(!time||time===0){
                playStartAnimation();
            }
            $("#aim").show();
            setTimeout(moveBoard, 3000);
            window.clearInterval(gameStarter)
        }
    }, 50);

    function playStartAnimation() {
        $("#num3").animate({opacity: 1}, 500, function () {
            $("#num3").animate({opacity: 0}, 500, function () {
                $("#num2").animate({opacity: 1}, 500, function () {
                    $("#num2").animate({opacity: 0}, 500, function () {
                        $("#num1").animate({opacity: 1}, 500, function () {
                            $("#num1").animate({opacity: 0}, 500);
                        });
                    });
                });
            });
        });

    }

    function moveBoard() {
        var current = boardPositions[currentPosition];
        var target = boardPositions[currentPosition + 1];
        if (target) {
            var time = Math.round(calculateDistance(current, target) / step);
            console.log(time);
            $("#board").animate({top: target[0] + "%", left: target[1] + "%"}, time, function () {
                currentPosition++;
                moveBoard();
            });
        }
    }

    function calculateDistance(point1, point2) {
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]));
    }

    $(document).keydown(function (event) {
        var aimPos = $("#aim").position();
        console.log(aimPos.left);
        switch(event.which){
            case 37:
                console.log("999");
                $("#aim").css("left",aimPos.left-10+"px");
                break;
            case 38:
                $("#aim").css("top",aimPos.top-10+"px");
                break;
            case 39:
                console.log("123");
                $("#aim").css("left",(aimPos.left+10)+"px");
                break;
            case 40:
                $("#aim").css("top",aimPos.top+10+"px");
                break;
            default:
                console.log("请按上下左右键");
                break;
        }
    })


});



