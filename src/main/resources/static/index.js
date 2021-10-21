//Globals letiables
//-----------------
let fps = 30;
let canvas = document.getElementById('canvas');
let ctx = canvas.getContext("2d");
let bgColor = "rgb(40,40,40)"
let ready;
let playerScore;
let renderTimer = setInterval(render,1/fps*100);
let difficultyTimer;
let moveTimer;
let moveTime;
let gameTime;
let difficulty;
let score;
let highScore=0;
let gameOver;
let entities = [];
let player;
let board;
let fader;


//Global Functions
//----------------

$(document).ready(function() {
    //Adding a listener to set size of canvas
    $(window).resize(resizeCanvas);
    //After loading the page, resize the canvas
    resizeCanvas();
});

//Setting the size by the size of windows.
function resizeCanvas() {
    $("#canvas").attr("width", $(window).get(0).innerWidth);
    $("#canvas").attr("height", $(window).get(0).innerHeight);
};

//Reset app back to 'Ready?' Screen
function reset(){
    if (score > highScore) highScore = score;
    ready = true;
    playerScore = 0;
    gameTime = 0;
    difficulty = 1;
    score = 0;
    moveTime = 1500;
    gameOver = false;
    fader = 0;
    entities = [];
    player = null;
    board = null;
    clearTimers()
}

//Clear all timers
function clearTimers(){
    clearInterval(difficultyTimer);
    clearInterval(moveTimer);
}

//Initialize all timers
function initializeTimers(){
    difficultyTimer = setInterval(increaseDifficulty,2000);
    moveTimer = setInterval(moveBoard,moveTime);
}

//Initialize / Start game
function init(){
    ready = false;
    clearTimers();
    initializeTimers();

    //Create player
    player = new Player();
    board = new Board();
    board.position.set(canvas.width/6, canvas.height/6);
    player.position.set(canvas.width/2,canvas.height-player.size);
    board.render();
    player.render();
    entities.push(board);
    entities.push(player);

}

//Update Entities
function updateEntities(){
    entities.forEach(function(e){
        if (e.position.y > canvas.height + 20){
            // to do (score calculating)
            removeEntity(e);
        }
        e.update(1/fps);
    })
}

//Draw background
function drawBG(){
    ctx.fillStyle = bgColor;
    ctx.fillRect(0,0,canvas.width,canvas.height);
}

//Draw Score / HUD
function drawScore(){
    ctx.fillStyle = "#CCFF99";
    ctx.font = "24px sans-serif";
    ctx.fillText("Score: " + score,10,24);
    ctx.font = "16px sans-serif";
    ctx.fillText("High Score: " + highScore,10,48);
    let playerScoreString = "";
    for (let i = 0; i < playerScore; i++){
        playerScoreString += "X";
    }
    ctx.font = "24px sans-serif";
    ctx.fillStyle = "#FF6666";
    ctx.fillText(playerScoreString,canvas.width - 75,24);
    ctx.font = "16px sans-serif";
    ctx.fillText("Difficulty: " + difficulty,canvas.width/2 - 50,24);
}

//Draws some static.
//@param alpha transparency
function drawStatic(alpha){
    let s = 15 ;
    for (let x = 0; x < canvas.width; x+=s){
        for (let y = 0; y < canvas.width; y+=s){
            let n = Math.floor(Math.random() * 60);
            ctx.fillStyle = "rgba(" + n + "," + n + "," + n + "," + (Math.random() * alpha) + ")";
            ctx.fillRect(x,y,s,s);
        }
    }
}

//Draws 'Ready?' screen
function drawReadyScreen(){
    drawBG();
    //drawStatic(.25);
    drawScore();
    fader += .1 * 1/fps;
    ctx.fillStyle = "rgba(255,255,255," + fader + ")";
    ctx.font = "72px sans-serif";
    ctx.fillText("READY?",canvas.width/2 - 140,canvas.height/2);
    drawScore();
}

//Draw all entities
function drawEntities(){
    entities.forEach(function(e){e.render();});
}

//Draw 'Game Over' screen
function drawGameOver(){
    ctx.fillStyle = "rgba(0,0,0,"+fader +")";
    ctx.fillRect(0,0,canvas.width,canvas.height);
    drawStatic(fader/2);
    drawScore();
    fader += .1 * 1/fps
    ctx.fillStyle = "rgba(255,255,255," + fader + ")";
    ctx.font = "72px sans-serif";
    ctx.fillText("GAME OVER",canvas.width/2 - 220,canvas.height/2);
}

//Render everything
function render(){
    drawBG();
    drawEntities();
    drawScore();
    if (gameOver){drawGameOver(); return;}
    else if (ready){drawReadyScreen(); return;}
    updateEntities();
    gameTime += 1/fps;
    if (playerScore >= 3) {
        clearTimers();
        gameOver = true;
        fader = 0;
    }
}

//Return mouse position relative to canvas
function getMousePos(canvas, evt) {
    let rect = canvas.getBoundingClientRect();
    return new Vector2(evt.clientX - rect.left,
        evt.clientY - rect.top)
}

//Click Event
function canvasClick(){
    if (gameOver){ if (fader > .5) reset();return;}
    if (ready)		{init(); return;}
    let bullet = new Bullet();
    bullet.position.set(player.position.x + player.size / 2 - bullet.size/2,player.position.y - player.size/2 - bullet.size /2);
    bullet.velocity.y = -30;
    entities.push(bullet);
    if (score > 0) score -= 1;
}

//Increment difficulty
function increaseDifficulty(){
    difficulty += 1;
    if (moveTime > 20) moveTime -= 20;
    clearInterval(spawnTimer);
    moveTimer = setInterval(moveBoard,moveTime);
}

//Change alpha of color
function setAlpha(color,alpha){
    if (color.indexOf('a') == -1){
        return color.replace(")",","+alpha+")").replace("rgb","rgba");
    }
}

//Remove Entity
function removeEntity(entity){
    let idx = entities.indexOf(entity);
    if (idx > -1) entities.splice(idx,1);
}

//Check if two entities overlap
function overlaps(entityA,entityB){
// to do
}

//Moving the painting board
function moveBoard() {
// to do
}

// Classes
//----------

//Vector2
let Vector2 = function(x1,y1){
    this.x = x1;
    this.y = y1;
}
Vector2.prototype.set = function(a, b){
    this.x = a;
    this.y = b;
}

//Entity (Base class)
let Entity = function(){
    this.name = "Entity";
    this.size = 25;
    this.position = new Vector2(0,0);
    this.velocity = new Vector2(0,0);
    this.color = "#FFFFFF";
}
Entity.prototype.sayName = function(){
    console.log(this.name);
}
Entity.prototype.update = function(deltaTime){
    this.position.x += this.velocity.x * deltaTime;
    this.position.y += this.velocity.y * deltaTime;
    //Keep in bounds
    if (this.position.x - this.size < 0) {this.position.x = this.size;}
    if (this.position.x + this.size > canvas.width) {this.position.x = canvas.width - this.size;}
}
Entity.prototype.render = function(){
    ctx.fillStyle = this.color;
    ctx.fillRect(this.position.x,this.position.y,this.size,this.size);
}

//Board Entity
let Board = function(){
    Entity.call(this);
    this.name = "Board";
    this.size = $(window).get(0).innerWidth * 0.2;
    this.color = "rgb(255, 255, 255)";
}
Board.prototype = Object.create(Entity.prototype);
Board.prototype.constructor = Entity;

//Player Entity
let Player = function(){
    Entity.call(this);
    this.name = "Player";
    this.color = "#4747FF"
}
Player.prototype = Object.create(Entity.prototype);
Player.prototype.constructor = Entity;

//Particle Entity
let Particle = function(){
    Entity.call(this);
    this.name = "Particle";
    this.size = 10;
    this.time = 0;
    this.maxTime = Math.floor((Math.random() * 10) + 3);
    this.velocity.x = Math.floor((Math.random() * 20) - 10);
    this.velocity.y = Math.floor((Math.random() * 20) - 10);
}
Particle.prototype = Object.create(Entity.prototype);
Particle.prototype.constructor = Entity;
Particle.prototype.update = function(deltatime){
    Entity.prototype.update.call(this,deltatime);
    this.time += deltatime;
    if (this.time >= this.maxTime) removeEntity(this);
}

//Bullet Entity
let Bullet = function(){
    Entity.call(this);
    this.name = "Bullet";
    this.size = 10;
    this.time = 0;
    this.color = "rgba(200,200,200,1)";
    this.particlesDelay = .5;
}
Bullet.prototype = Object.create(Entity.prototype);
Bullet.prototype.constructor = Entity;
Bullet.prototype.update = function(deltatime){
    Entity.prototype.update.call(this,deltatime);

    //Check for collisions
    let me = this;
    entities.forEach(function(e){
        if (e !== me && e.name != "Particle"){
            if (overlaps(me,e)){
                death(e);
                removeEntity(me);
            }
        }
    })
    //Remove from game if outside bounds
    if (this.position.y < 0) removeEntity(this);

    //Create particles
    this.time += deltatime;
    if (this.time >= this.particlesDelay){
        this.time = 0;
        let p = new Particle();
        p.size = Math.floor((Math.random() * 5)+2);
        p.color = setAlpha("rgb(125,125,125)",Math.random());
        //p.color = setAlpha(randomColor(100,255),Math.random()); //Rainbow colored particles
        p.velocity.x /=2;
        p.position.x = this.position.x + this.size /2 - p.size/2;
        p.position.y = this.position.y - p.size/2;
        entities.push(p);
    }
}



//	These must remain at the bottom of this file & in this order //
//	============================================================ //

//HTML load event
document.addEventListener('DOMContentLoaded', reset());

canvas.addEventListener("click",canvasClick);

//Mouse move event
canvas.addEventListener('mousemove', function(evt) {
    let mousePos = getMousePos(canvas, evt);
    if (player && !gameOver) player.position.x = mousePos.x;
}, false);



