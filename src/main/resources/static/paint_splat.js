$(document).ready(function () {
    var sessionId;
    var players;
    var scores;
    var boardPositions;
    var currentPosition = 0;
    var step;
    var timeAfterStart;
    var timeAfterCreate;
    var isStart;
    var paints;

    $.post("/checkRoomId", {}, function (RoomId) {
        $("#roomNumber").text("Room Number: " + RoomId);
    });
    $.post("/getPlayerId", {}, function (playerId) {
        sessionId = playerId;
        $.post("/getGame", {}, function (data) {
            console.log(data);
            boardPositions = data.boardPositions;
        });
    });

    function renderBoard(playersId) {
        if (playersId === sessionId && !isStart) {
            $("#start").show();
        } else {
            $("#start").hide();
        }
        var scoreBoard = $("#scoreBoard");
        scoreBoard.html("");
        for (var i = 0; i < players.length; i++) {
            if (players[i] === sessionId) {
                scoreBoard.append("<div class='mine'>Player " + (i + 1) + " : " + scores[0] + "</div>")
            } else {
                scoreBoard.append("<div>Player " + (i + 1) + ": " + scores[0] + "</div>")
            }
        }
        $("#timer").text("Time Remaining: " + (60 - Math.round(timeAfterStart / 1000)));
    }

    // var gameUpdater = setInterval(updateGame, 50);

    function updateGame() {
        $.post("/getGame", {}, function (data) {
            players = data.players;
            scores = data.scores;
            timeAfterStart = data.timeAfterStart;
            timeAfterCreate = data.timeAfterCreate;
            isStart = data.start;
            step = data.step;
            renderBoard(players[0]);
        });
    }

    $("#start").click(function () {
        $.post("/startGame", {})
    });

    $("#quit").click(function () {
        $.post("/quitRoom", {});
        window.location.href = "index.html";
    });

    // var gameStarter = setInterval(function () {
    //     if (isStart) {
    //         if (!timeAfterStart || timeAfterStart === 0) {
    //             playStartAnimation();
    //         }
    //         $("#aim").show();
    //         setTimeout(moveBoard, 3000);
    //         window.clearInterval(gameStarter)
    //     }
    // }, 50);

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
            var timeUse = Math.round(calculateDistance(current, target) / step);
            $("#board").animate({top: target[0] + "%", left: target[1] + "%"}, timeUse, function () {
                currentPosition++;
                moveBoard();
            });
        }
    }

    function calculateDistance(point1, point2) {
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]));
    }


    $(document).keydown(function (event) {
        var aim = $("#aim");
        switch (event.which) {
            case 37:
                aim.css({left: parseFloat(aim.css("left")) - 10});
                break;
            case 38:
                aim.css({top: parseFloat(aim.css("top")) - 10});
                break;
            case 39:
                aim.css({left: parseFloat(aim.css("left")) + 10});
                break;
            case 40:
                aim.css({top: parseFloat(aim.css("top")) + 10});
                break;
        }
    })


});



