"use strict";
class InfoBox {
  constructor() {
    this.infoBox = $("#infobox");
    this.logBox = $("#logbox");
    this.startMove = 0;
    this.endMove = 0;
    this.chart = "";

    this.initLogBox();
    this.showInfoBox();
    this.reset("red");
  }

  /**
   * @param {string} player
   */
  set Player(player) {
    $("span")[0].innerHTML = player;
  }

  /**
   * @param {int} moves
   */
  set Moves(moves) {
    $("span")[1].innerHTML = moves;
  }

  /**
   * @param {int} cells
   */
  set Cells(cells) {
    $("span")[2].innerHTML = cells;
  }

  /**
   * @param {string} player
   */
  get Player() {
    return $("span")[0].innerHTML;
  }

  /**
   * @param {int} moves
   */
  get Moves() {
    return $("span")[1].innerHTML;
  }

  /**
   * @param {int} cells
   */
  get Cells() {
    return $("span")[2].innerHTML;
  }

  /**
   * @param {string} player
   */
  set PrevPlayer(player) {
    $("span")[3].innerHTML = player;
  }

  /**
   * @param {string} player
   */
  set Time(time) {
    $("span")[4].innerHTML = time;
  }

  initLogBox() {
    let gameover = $("<h2>").addClass("gameover");
    let yellowWins = $("<h2>").addClass("yellow-wins");
    let redWins = $("<h2>").addClass("red-wins");
    let draws = $("<h2>").addClass("draws");
    gameover.insertBefore("#myChart");
    yellowWins.insertBefore("#myChart");
    redWins.insertBefore("#myChart");
    draws.insertBefore("#myChart");
  }

  showInfoBox() {
    const that = this;

    function calculatePosition(infoBox, logBox) {
      var boardPosition = $("#board").position();
      let position = parseInt(boardPosition.left) - 240 - 40;
      let logBoxOffset = 0;
      if (position <= 0) {
        position += 290;
        logBoxOffset = position + 320;
        infoBox.css("opacity", "0.9");
        logBox.css("opacity", "0.9");
      } else {
        logBoxOffset = position + 900;
        logBox.css("opacity", "0.6");
        infoBox.css("opacity", "0.6");
      }
      infoBox.css("left", position + "px");
      logBox.css("left", logBoxOffset + "px");
      infoBox.css("top", boardPosition.top + 30 + "px");
      logBox.css("top", boardPosition.top + 30 + "px");
    }

    $(document).on("click", ".infobox-btn", function () {
      let infoBox = that.infoBox;
      let logBox = that.logBox;
      if (infoBox.hasClass("active")) {
        infoBox.removeClass("active");
        infoBox.css("left", "-240px");
        logBox.css("left", $(window).width() + "px");
        return;
      }
      logBox.show();
      infoBox.show();
      infoBox.addClass("active");
      calculatePosition(infoBox, logBox);
    });
    $(".infobox-btn").trigger("click");

    $(window).resize(function () {
      let infobox = that.infoBox;
      let logbox = that.logBox;
      if (infobox.hasClass("active")) {
        calculatePosition(infobox, logbox);
      }
    });
  }

  reset(player) {
    $(".hide").show();
    $(".gameover").hide();
    $(".yellow-wins").hide();
    $(".red-wins").hide();
    $(".draws").hide();
    $("#myChart").hide();
    $(".box h2").css("text-align", "left");
    this.Player = player;
    this.Moves = 0;
    this.Cells = 7 * 6;
    this.startMove = 0;
    this.endMove = 0;
    this.PrevPlayer = "";
    this.Time = "";
  }

  newMove(player) {
    this.Player = player;
    this.Moves = parseInt(this.Moves) + 1;
    this.Cells = parseInt(this.Cells) - 1;
    if (this.startMove === 0) {
      this.startMove = new Date();
      this.PrevPlayer = player === "red" ? "yellow" : "red";
      this.Time = "First Move";
      return;
    }
    this.endMove = new Date();
    var diff = this.endMove.getTime() - this.startMove.getTime();
    diff = diff / 1000;
    this.PrevPlayer = player === "red" ? "yellow" : "red";
    this.Time = diff;
    this.startMove = new Date();
  }

  gameOver(player, won, yellowWinsNum, redWinsNum, drawsNum) {
    $(".hide").hide();
    $(".gameover").show();
    $(".yellow-wins").show();
    $(".red-wins").show();
    $(".draws").show();
    $("#myChart").show();
    let output = "";
    if (won === true) {
      output = "Draw";
    } else {
      output = "Winner: " + player;
    }

    $("h2.gameover")[0].innerHTML = output;
    $(".yellow-wins")[0].innerHTML = "Yellow Wins: " + yellowWinsNum;
    $(".red-wins")[0].innerHTML = "Red Wins: " + redWinsNum;
    $(".draws")[0].innerHTML = "Draws: " + drawsNum;
    $(".box h2").css("text-align", "center");
    this.updateChart(redWinsNum, yellowWinsNum, drawsNum);
  }

  updateChart(redWins, yellowWins, draws) {
    var xValues = ["Red", "Yellow", "Draws"];
    var yValues = [redWins, yellowWins, draws];
    var barColors = ["#e56b6f", "#eaac8b", "#a695b2"];

    if (this.chart !== "") {
      this.chart.destroy();
    }

    this.chart = new Chart("myChart", {
      type: "pie",
      data: {
        labels: xValues,
        datasets: [
          {
            backgroundColor: barColors,
            data: yValues,
          },
        ],
      },
    });
  }
}
