"use strict";
$(document).ready(function () {
  const connect4 = new Connect4();
  const infobox = new InfoBox();
  const aiPlayer = new minMaxAi();

  // Reset Button Event Listener
  $(".btn").on("click", function () {
    connect4.reset();
    infobox.reset(connect4.player);
  });

  // Hover from cell Event Listener
  connect4.board.on("mouseenter", ".col", function () {
    if (connect4.gameOver) return;
    const emptyCell = connect4.isValidMove(this);
    if (!emptyCell) return;

    emptyCell.addClass(connect4.player);
    $(emptyCell).css("opacity", "60%");
  });

  // Leave from cell Event Listener
  connect4.board.on("mouseleave", ".col", function () {
    if (connect4.gameOver) return;
    const emptyCell = connect4.isValidMove(this);
    if (!emptyCell) return;

    $(emptyCell).css("opacity", "100%");
    emptyCell.removeClass("red");
    emptyCell.removeClass("yellow");
  });

  // Click on cell Event Listener
  connect4.board.on("click", ".col", function () {
    if (connect4.gameOver) return;
    if (!aiPlayer.ready) return;

    var emptyCell = connect4.isValidMove(this);
    if (!emptyCell) return;

    emptyCell.addClass(connect4.player);
    emptyCell.attr("empty", false);
    $(emptyCell).css("opacity", "100%");

    if (endGame(emptyCell)) {
      if (!aiPlayer.isOn) connect4.changePlayerTurn();
      return;
    }

    connect4.changePlayerTurn();
    infobox.newMove(connect4.player);
    // AI move
    if (aiPlayer.isOn) {
      var cell = aiPlayer.makeMove();

      emptyCell = connect4.isValidMove(cell);
      if (!emptyCell) return;

      emptyCell.addClass(connect4.player);
      emptyCell.attr("empty", false);
      $(emptyCell).css("opacity", "100%");

      if (endGame(emptyCell)) {
        window.alert("Δεν παίζεις με ποιότητα!");
      }
      connect4.player = "red";
      infobox.newMove(connect4.player);
    }
    $(this).trigger("mouseenter");
  });

  function endGame(emptyCell) {
    var won = connect4.hasPlayerWon(
      connect4.getRow(emptyCell),
      connect4.getCol(emptyCell),
      connect4.rows,
      connect4.cols,
      connect4.player
    );

    if (won === true) {
      connect4.gameOver = true;
      connect4.draws++;
      infobox.gameOver(
        connect4.player,
        won,
        connect4.yellowWins,
        connect4.redWins,
        connect4.draws
      );
      return true;
    } else if (won) {
      connect4.gameOver = true;
      if (connect4.player === "red") connect4.redWins++;
      else connect4.yellowWins++;
      infobox.gameOver(
        connect4.player,
        won,
        connect4.yellowWins,
        connect4.redWins,
        connect4.draws
      );
      for (let i = 0; i < won.length; i++) {
        won[i].addClass("blink");
        won[i].addClass("glow");
      }
      return true;
    }
  }
});
