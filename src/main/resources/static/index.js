$(document).ready(function(){
    moveDiv();
});

function makeNewPosition(){

    var h = $(window).height() - 300;
    var w = $(window).width() - 400;

    var nh = Math.floor(Math.random() * h * 0.6);
    var nw = Math.floor(Math.random() * w * 0.7);

    return [nh,nw];
}

function moveDiv(){
    var newq = makeNewPosition();
    $('.basicRectangle').animate({ top: newq[0], left: newq[1]},1000, function(){
      moveDiv();
    });

};