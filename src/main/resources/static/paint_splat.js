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
    var keyDownSet = new Set();

    //Regular expressions read whether the accessing end is a mobile device or a computer, and switch the corresponding display method
    if (!/Android|webOS|iPhone|iPod|iPad|BlackBerry/i.test(navigator.userAgent)) {
        $("#up").hide();
        $("#down").hide();
        $("#left").hide();
        $("#right").hide();
        $("#shoot").hide();
        $("#function_phone").attr("id", "function_web")
    }

    //Query and display room number from backend
    $.post("/checkRoomId", {}, function (RoomId) {
        $("#roomNumber").text("Room Number: " + RoomId);
    });

    //Initialize the game, query the data needed for initialization from the background, get the data and then initialize it (synchronization)
    $.post("/getPlayerId", {}, function (playerId) {
        sessionId = playerId;
        $.post("/getGame", {}, function (data) {
            // console.log(data);
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
                            enableShoot();
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
                        enableShoot();
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

    //Move the board according to the random point information in the boardPositions, recursively calling itself until the entire boardPositions are traversed
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

    //Tool method
    function calculateDistance(point1, point2) {
        return Math.sqrt((point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]));
    }

    //Start interval to achieve state synchronization by polling (synchronize data from backend server)
    var gameUpdater = setInterval(updateGame, 50);

    //Request the backend and synchronize the client local data with the latest data obtained
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
                $("#gameOver").append("<br/><br/>");
                $("#gameOver").append($("#quit"));
                $("#cover").show();
                setTimeout(function () {
                    $("#quit").click();
                }, 10000);
            }
        });
    }

    //Rendering client pages
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
                scoreBoard.append("<div class='mine'>Player " + (i + 1) + " : " + scores[i] + "</div>");
            } else {
                scoreBoard.append("<div class='others'>Player " + (i + 1) + ": " + scores[i] + "</div>");
            }
        }

        $("#timer").text("Time Remaining: " + (60 - Math.round(timeAfterStart / 1000)));

        if (isStart) {
            $("#startInfo").hide();
        } else {
            $("#startInfo").text("Game auto start in " + (60 - Math.round(timeAfterCreate / 1000)) + "s");
            $("#startInfo").show();
        }
        renderPaints();
    }

    // Start button function
    $("#start").click(function () {
        $.post("/startGame", {})
    });

    // Quit button function
    $("#quit").click(function () {
        $.post("/quitRoom", {});
        window.location.href = "index.html";
    });

    //Display the collimator and bind the associated keyboard events
    function enableShoot() {
        $("#aim").show();
        $(document).keydown(function (e) {
            switch (e.keyCode) {
                case 37:
                case 65:
                    keyDownSet.add(37);
                    moveAim();
                    break;
                case 38:
                case 87:
                    keyDownSet.add(38);
                    moveAim();
                    break;
                case 39:
                case 68:
                    keyDownSet.add(39);
                    moveAim();
                    break;
                case 40:
                case 83:
                    keyDownSet.add(40);
                    moveAim();
                    break;
                case 32:
                    shoot();
            }
        });
        $(document).keyup(function (e) {
            switch (e.keyCode) {
                case 37:
                case 65:
                    keyDownSet.delete(37);
                    break;
                case 38:
                case 87:
                    keyDownSet.delete(38);
                    break;
                case 39:
                case 68:
                    keyDownSet.delete(39);
                    break;
                case 40:
                case 83:
                    keyDownSet.delete(40);
            }
        });

        var moveSpeed = 50;
        var upTimerTask;
        $("#up").on("touchstart", function () {
            keyDownSet.add(38);
            upTimerTask = setInterval(moveAim, moveSpeed);
        });

        $("#up").on("touchend", function () {
            window.clearInterval(upTimerTask);
            keyDownSet.delete(38);
        });

        $("#up").on("touchcancel", function () {
            window.clearInterval(upTimerTask);
            keyDownSet.delete(38);
        });
        var downTimerTask;
        $("#down").on("touchstart", function () {
            keyDownSet.add(40);
            downTimerTask = setInterval(moveAim, moveSpeed);
        });

        $("#down").on("touchend", function () {
            window.clearInterval(downTimerTask);
            keyDownSet.delete(40);
        });

        $("#down").on("touchcancel", function () {
            window.clearInterval(downTimerTask);
            keyDownSet.delete(40);
        });
        var leftTimerTask;
        $("#left").on("touchstart", function () {
            keyDownSet.add(37);
            leftTimerTask = setInterval(moveAim, moveSpeed);
        });

        $("#left").on("touchend", function () {
            window.clearInterval(leftTimerTask);
            keyDownSet.delete(37);
        });

        $("#left").on("touchcancel", function () {
            window.clearInterval(leftTimerTask);
            keyDownSet.delete(37);
        });
        var rightTimerTask;
        $("#right").on("touchstart", function () {
            keyDownSet.add(39);
            rightTimerTask = setInterval(moveAim, moveSpeed);
        });

        $("#right").on("touchend", function () {
            window.clearInterval(rightTimerTask);
            keyDownSet.delete(39);
        });

        $("#right").on("touchcancel", function () {
            window.clearInterval(rightTimerTask);
            keyDownSet.delete(39);
        });

        // var upTimerTask;
        // $("#up").mousedown(function () {
        //     keyDownSet.add(38);
        //     upTimerTask = setInterval(moveAim, moveSpeed);
        // });
        // $("#up").mouseup(function () {
        //     window.clearInterval(upTimerTask);
        //     keyDownSet.delete(38);
        // });
        // var downTimerTask;
        // $("#down").mousedown(function () {
        //     keyDownSet.add(40);
        //     downTimerTask = setInterval(moveAim, moveSpeed);
        // });
        // $("#down").mouseup(function () {
        //     window.clearInterval(downTimerTask);
        //     keyDownSet.delete(40);
        // });
        // var leftTimerTask;
        // $("#left").mousedown(function () {
        //     keyDownSet.add(37);
        //     leftTimerTask = setInterval(moveAim, moveSpeed);
        // });
        // $("#left").mouseup(function () {
        //     window.clearInterval(leftTimerTask);
        //     keyDownSet.delete(37);
        // });
        // var rightTimerTask;
        // $("#right").mousedown(function () {
        //     keyDownSet.add(39);
        //     rightTimerTask = setInterval(moveAim, moveSpeed);
        // });
        // $("#right").mouseup(function () {
        //     window.clearInterval(rightTimerTask);
        //     keyDownSet.delete(39);
        // });
        $("#shoot").click(function () {
            shoot();
        });


    }

    //A specific method of moving the collimator, this approach allows the user to perform multi-point (multiple keystrokes) operations
    function moveAim() {
        keyDownSet.forEach(function (key) {
            var aim = $("#aim");
            switch (key) {
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
            }
        })
    }

    //Calculate the percentage of coordinates of the current collimator relative to the board and upload it to the backend, the backend will judge and return whether it is successful or not, if it is not successful then the shooting failure animation will be played
    function shoot() {
        var board = $("#board");
        var aim = $("#aim");
        var aimTop = aim.offset().top + aim.height() / 2;
        var aimLeft = aim.offset().left + aim.width() / 2;
        var boardTop = board.offset().top;
        var boardLeft = board.offset().left;
        var boardBottomRightPointTop = boardTop + board.height();
        var boardBottomRightPointLeft = boardLeft + board.width();
        if (aimTop > boardTop + aim.height() / 2 && aimTop < boardBottomRightPointTop - aim.height() / 2 && aimLeft > boardLeft + aim.width() / 2 && aimLeft < boardBottomRightPointLeft - aim.width() / 2) {
            var positionTop = (aimTop - boardTop) * 100 / (boardBottomRightPointTop - boardTop);
            var positionLeft = (aimLeft - boardLeft) * 100 / (boardBottomRightPointLeft - boardLeft);
            $.post("/shoot", {top: positionTop, left: positionLeft}, function (result) {
                if (!result) {
                    playShootFailedAnimation();
                }
            })
        } else {
            playShootFailedAnimation();
        }
    }


    function playShootFailedAnimation() {
        var aim = $("#aim");
        aim.animate({opacity: 0}, 100, function () {
            aim.animate({opacity: 1}, 100, function () {
                aim.animate({opacity: 0}, 100, function () {
                    aim.animate({opacity: 1}, 100);
                });
            });
        });
    }

    //Render paints to the board, renderPaints() will be called every time renderBoard() is executed
    function renderPaints() {
        $("#board").html("");
        for (var i = 0; i < paints.length; i++) {
            // console.log(paints[i]);
            if (paints[i].player === players[0]) {
                $("#board").append("<img class='paint' style='top:" + paints[i].position[0] + "%;left: " + paints[i].position[1] + "%' src='Img/paint1.png'>");
            } else if (paints[i].player === players[1]) {
                $("#board").append("<img class='paint' style='top:" + paints[i].position[0] + "%;left: " + paints[i].position[1] + "%' src='Img/paint2.png'>");
            } else if (paints[i].player === players[2]) {
                $("#board").append("<img class='paint' style='top:" + paints[i].position[0] + "%;left: " + paints[i].position[1] + "%' src='Img/paint3.png'>");
            } else if (paints[i].player === players[3]) {
                $("#board").append("<img class='paint' style='top:" + paints[i].position[0] + "%;left: " + paints[i].position[1] + "%' src='Img/paint4.png'>");
            }
        }
    }

});



