$(document).ready(function () {
    var sessionId;
    var currentPosition = 0;
    var boardPositions;
    var isStart;
    var step;
    var timeAfterStart;
    var timeAfterCreate;
    var players;
    var scores;
    var paints;

    $.post("/checkRoomId", {}, function (RoomId) {
        $("#roomNumber").text("Room Number: " + RoomId);
    });
    $.post("/getPlayerId", {}, function (playerId) {
        sessionId = playerId;
        $.post("/getGame", {}, function (data) {
            console.log(data);
            boardPositions = data.boardPositions;
            isStart = data.isStart;
            step = data.step;
            timeAfterStart = data.timeAfterStart;
            timeAfterCreate = data.timeAfterCreate;
            players = data.players;
            scores = data.scores;
            paints = data.paints;
            var gameStarter = setInterval(function () {
                if (isStart) {
                    if (!timeAfterStart || timeAfterStart === 0) {
                        playStartAnimation();
                        setTimeout(function () {
                            $("#aim").show();
                            moveBoard();
                        }, 3000);
                    } else {
                        var step = 0.01;
                        var timeSpent = 0;
                        while (timeSpent < timeAfterStart) {
                            var current = boardPositions[currentPosition];
                            var target = boardPositions[currentPosition + 1];
                            if (target) {
                                timeSpent += calculateDistance(current, target) / step;
                                if (timeSpent / 1000 > 40) {
                                    step = 0.03
                                } else if (timeSpent / 1000 > 20) {
                                    step = 0.02
                                }
                                currentPosition++;
                            }
                        }
                        $("#aim").show();
                        moveBoard();
                    }
                    window.clearInterval(gameStarter)
                }
            }, 50);
        });
    });

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
        console.log(boardPositions.length + "----" + (currentPosition + 1) + "----" + timeAfterStart / 1000 + "----" + step);
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

    var gameUpdater = setInterval(updateGame, 50);

    function updateGame() {
        $.post("/getGame", {}, function (data) {
            isStart = data.start;
            step = data.step;
            timeAfterStart = data.timeAfterStart;
            timeAfterCreate = data.timeAfterCreate;
            players = data.players;
            scores = data.scores;
            paints = data.paints;
            renderBoard(players[0]);
            if (timeAfterStart >= 60000) {
                window.clearInterval(gameUpdater);
                $("#gameOver").append($("#scoreBoard"));
                $("#gameOver").append($("#quit"));
                $("#cover").show();
                setTimeout(function () {
                    $("#quit").click();
                }, 10000);
            }
        });
    }

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
        if (isStart) {
            $("#startInfo").hide();
        } else {
            $("#startInfo").text("Game auto start in " + (60 - Math.round(timeAfterCreate / 1000)) + "s");
            $("#startInfo").show();
        }
    }

    $("#start").click(function () {
        $.post("/startGame", {})
    });

    $("#quit").click(function () {
        $.post("/quitRoom", {});
        window.location.href = "index.html";
    });

    $(document).keydown(function (event) {
        var aim = $("#aim");
        switch (event.which) {
            case 37:
                aim.css({left: parseFloat(aim.css("left")) - 8});
                break;
            case 38:
                aim.css({top: parseFloat(aim.css("top")) - 8});
                break;
            case 39:
                aim.css({left: parseFloat(aim.css("left")) + 8});
                break;
            case 40:
                aim.css({top: parseFloat(aim.css("top")) + 8});
                break;
        }
    });


});



