$(document).ready(function () {
    $("#test").click(function () {
        $.post("/test", {}, function (data) {
            moveBoard(data[0], data[1])
        });
    });

    var topNow;
    var leftNow;
    var topTarget;
    var leftTarget;
    var i;
    var t = 0;

    function moveBoard(top, left) {
        //setinterval
        //jquery css()方法更改top和left
        //如果更改完后等于了传进来的参数，shut interval
        topNow = parseInt($("#board").css("top"));
        leftNow = parseInt($("#board").css("left"));
        console.log(topNow + "-----" + leftNow);

        var verticalGap = topTarget - topNow;
        var horizontalGap = leftTarget - leftNow;
        var speedX;
        var speedY;
        if (verticalGap > horizontalGap) {
            speedX = 0;
        }


        topTarget = top;
        leftTarget = left;
        i = setInterval(function () {
            test(t)
        }, 10);
    }

    function test(x, y) {
        if (topTarget > topNow) {
            topNow++;
        } else if (topTarget < topNow) {
            topNow--;
        }
        if (leftTarget > leftNow) {
            leftNow++;
        } else if (leftTarget < leftNow) {
            leftNow--;
        }
        $("#board").css({
            "top": topNow + "px",
            "left": leftNow + "px"
        });
        console.log(topNow + "-----" + leftNow);
        if (topTarget == topNow && leftTarget == leftNow) {
            console.log("chenglila");
            window.clearInterval(i);
            $.post("/test", {}, function (data) {
                moveBoard(data[0], data[1])
            });
        }

    }





});





