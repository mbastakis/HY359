"use strict";
class Connect4 {
  constructor() {
    this.rows = 6;
    this.cols = 7;
    this.board = $("#board");
    this.gameOver = false;
    this.player = "red";
    this.firstPlayer = "red";
    this.redWins = 0;
    this.yellowWins = 0;
    this.draws = 0;

    this.createBoard();
  }

  createBoard() {
    for (let row = 0; row < this.rows; row++) {
      let curr_row = $("<div>").addClass("row");
      for (let col = 0; col < this.cols; col++) {
        curr_row.append(
          $("<div>")
            .addClass("col")
            .attr("index", row + " " + col)
            .attr("empty", true)
        );
      }
      this.board.append(curr_row);
    }
  }

  getCell(row, col) {
    return $('.col[index="' + row + " " + col + '"]');
  }

  // Check if a player won
  hasPlayerWon(row, col, rows, cols, player) {
    const that = this;

    function horizontalWin(row, player) {
      let count = [];
      for (let i = 0; i < cols; i++) {
        if (that.getCell(row, i).hasClass(player)) {
          count.push(that.getCell(row, i));
        } else {
          count = [];
        }

        if (count.length >= 4) {
          return count;
        }
      }
      return null;
    }

    function verticalWin(col, player) {
      let count = [];
      for (let i = 0; i < rows; i++) {
        if (that.getCell(i, col).hasClass(player)) {
          count.push(that.getCell(i, col));
        } else {
          count = [];
        }

        if (count.length >= 4) {
          return count;
        }
      }
      return null;
    }

    function diagonalWin(row, col, player) {
      var ul = row < col ? row : col;
      var dl = rows - row - 1 < col ? rows - row - 1 : col;
      var ur = row < cols - col - 1 ? row : cols - col - 1;
      var dr =
        rows - row - 1 < cols - col - 1 ? rows - row - 1 : cols - col - 1;
      var count = [];
      var diagonal = [];

      // Up left diagonal
      var step_i = -1;
      var step_j = -1;
      var tmp_row = parseInt(row);
      var tmp_col = parseInt(col);
      for (let i = 0; i <= ul; i++) {
        diagonal.push(that.getCell(tmp_row, tmp_col));

        tmp_row += step_i;
        tmp_col += step_j;
      }

      // Down right diagonal
      step_i = 1;
      step_j = 1;
      tmp_row = parseInt(row) + step_i;
      tmp_col = parseInt(col) + step_j;
      for (let i = 0; i <= dr; i++) {
        diagonal.push(that.getCell(tmp_row, tmp_col));

        tmp_row += step_i;
        tmp_col += step_j;
      }

      // Check for score in the left to right diagonal
      diagonal.sort(function (a, b) {
        if (a.attr("index") === undefined || b.attr("index") === undefined) {
          return 0;
        }
        return a.attr("index")[0] - b.attr("index")[0];
      });
      for (let i = 0; i < diagonal.length; i++) {
        if (diagonal[i].hasClass(player)) {
          count.push(diagonal[i]);
        } else {
          count = [];
        }

        if (count.length >= 4) {
          return count;
        }
      }

      // Up right diagonal
      diagonal = [];
      count = [];
      step_i = -1;
      step_j = 1;
      tmp_row = parseInt(row);
      tmp_col = parseInt(col);
      for (let i = 0; i <= ur; i++) {
        diagonal.push(that.getCell(tmp_row, tmp_col));

        tmp_row += step_i;
        tmp_col += step_j;
      }

      // Down left diagonal
      step_i = 1;
      step_j = -1;
      tmp_row = parseInt(row) + step_i;
      tmp_col = parseInt(col) + step_j;
      for (let i = 0; i <= dl; i++) {
        diagonal.push(that.getCell(tmp_row, tmp_col));

        tmp_row += step_i;
        tmp_col += step_j;
      }

      // Check for score in the right to left diagonal
      diagonal.sort(function (a, b) {
        if (a.attr("index") === undefined || b.attr("index") === undefined) {
          return 0;
        }
        return a.attr("index")[0] - b.attr("index")[0];
      });
      for (let i = 0; i < diagonal.length; i++) {
        if (diagonal[i].hasClass(player)) {
          count.push(diagonal[i]);
        } else {
          count = [];
        }

        if (count.length >= 4) {
          return count;
        }
      }

      return null;
    }

    function isDraw() {
      for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols; j++) {
          if (that.getCell(i, j).attr("empty") === "true") {
            return false;
          }
        }
      }
      return true;
    }

    var won = verticalWin(col, player);
    if (won) return won;
    won = horizontalWin(row, player);
    if (won) return won;
    won = diagonalWin(row, col, player);
    if (won) return won;

    return isDraw();
  }

  getEmptyCell(col) {
    for (let i = 5; i >= 0; i--) {
      var cell = $("div[index='" + i + " " + col + "']");
      if (cell.attr("empty") === "true") {
        return cell;
      }
    }
    return null;
  }

  isValidMove(cell) {
    let emptyCell = this.getEmptyCell($(cell).attr("index")[2]);
    if (!emptyCell) {
      // TODO Print Text
    }
    return emptyCell;
  }

  changePlayerTurn() {
    if (this.gameOver === true) {
      this.player = this.firstPlayer === "red" ? "yellow" : "red";
      this.firstPlayer = this.player;
    } else this.player = this.player === "red" ? "yellow" : "red";
  }

  getRow(cell) {
    return $(cell).attr("index")[0];
  }

  getCol(cell) {
    return $(cell).attr("index")[2];
  }

  reset() {
    for (let i = 0; i < this.rows; i++) {
      for (let j = 0; j < this.cols; j++) {
        var cell = this.getCell(i, j);
        cell.removeClass("yellow");
        cell.removeClass("red");
        cell.attr("empty", "true");
        cell.removeClass("blink glow");
      }
    }

    this.gameOver = false;
  }
}
