"use strict";
class minMaxAi {
  constructor() {
    this.isOn = false;
    this.ready = false;
    this.score = 99999;
    this.window = $(".playerSelection");
    this.depth = 5;

    this.initSelection();
  }

  /**
   * @param {int} depth
   */
  set Depth(depth) {
    this.depth = depth;
  }

  initSelection() {
    const that = this;

    $(window).resize(function () {
      if (that.ready) return;
      var boardPosition = $("#board").position();
      that.window.css("top", boardPosition.top + 20);
      that.window.css("left", boardPosition.left);
    });

    $(".right").on("click", function () {
      $(".dropbtn").show();
    });

    $(".dropbtn").on("click", function () {
      if ($(".dropdown-content .depth").hasClass("active")) {
        $(".dropdown-content .depth").removeClass("active");
        $(".dropdown-content .depth").css("display", "none");
      } else {
        $(".dropdown-content .depth").css("display", "block");
        $(".dropdown-content .depth").addClass("active");
      }
    });

    $(".depth").on("click", function () {
      that.depth = $(this).attr("depth");
      $(".dropdown-content .depth").removeClass("active");
      $(".dropdown-content .depth").css("display", "none");
    });

    $(".select-btn").on("click", function () {
      if ($(this).hasClass("right")) {
        that.isOn = true;
      }
      that.ready = true;
      that.window.css("top", "-554px");
    });
    $(document).trigger("resize");
  }

  getCell(row, col) {
    return $('.col[index="' + row + " " + col + '"]');
  }

  scoreAtPosition(board, row, column, step_i, step_j) {
    var aiScore = 0;
    var playerScore = 0;

    for (let i = 0; i < 4; i++) {
      if (board[row][column] === 1) playerScore++;
      else if (board[row][column] === 2) aiScore++;

      row += step_i;
      column += step_j;
    }

    if (playerScore === 4) return -99999;
    else if (aiScore === 4) return 99999;

    return aiScore;
  }

  calculateScore(board) {
    var verticalScore = 0;
    var horizontalScore = 0;
    var diagonalLRScore = 0;
    var diagonalRLScore = 0;

    // Calculate Vertical Score
    for (let i = 0; i < 3; i++) {
      for (let j = 0; j < 7; j++) {
        let score = this.scoreAtPosition(board, i, j, 1, 0);
        if (score >= 99999 || score <= -99999) return score;
        verticalScore += score;
      }
    }

    // Calculate Horizontal Score
    for (let i = 0; i < 6; i++) {
      for (let j = 0; j < 4; j++) {
        let score = this.scoreAtPosition(board, i, j, 0, 1);
        if (score >= 99999 || score <= -99999) return score;
        horizontalScore += score;
      }
    }

    // Calculate Diagonal Score Left-Right
    for (let i = 0; i < 3; i++) {
      for (let j = 0; j < 4; j++) {
        let score = this.scoreAtPosition(board, i, j, 1, 1);
        if (score >= 99999 || score <= -99999) return score;
        diagonalLRScore += score;
      }
    }

    // Calculate Diagonal Score Right-Left
    for (let i = 3; i < 6; i++) {
      for (let j = 0; j <= 3; j++) {
        let score = this.scoreAtPosition(board, i, j, -1, +1);
        if (score >= 99999 || score <= -99999) return score;
        diagonalRLScore += score;
      }
    }

    return horizontalScore + verticalScore + diagonalRLScore + diagonalLRScore;
  }

  maximize(board, depth, alpha, beta) {
    var score = this.calculateScore(board);

    // To stop recursion
    if (this.isFinshed(depth, score, board)) return [null, score];

    var maxMove = [null, -99999];

    for (let i = 0; i < 7; i++) {
      var copiedBoard = this.copyBoard(board);

      if (this.placeMove(copiedBoard, i)) {
        var nextMove = this.minimize(copiedBoard, depth - 1, alpha, beta);

        if (maxMove[0] === null || nextMove[1] > maxMove[1]) {
          maxMove[0] = i;
          maxMove[1] = nextMove[1];
          alpha = nextMove[1];
        }

        if (alpha >= beta) return maxMove;
      }
    }

    return maxMove;
  }

  minimize(board, depth, alpha, beta) {
    var score = this.calculateScore(board);

    // To stop recursion
    if (this.isFinshed(depth, score, board)) return [null, score];

    var minMove = [null, 99999];

    for (let i = 0; i < 7; i++) {
      var copiedBoard = this.copyBoard(board);

      if (this.placeMove(copiedBoard, i)) {
        var nextMove = this.maximize(copiedBoard, depth - 1, alpha, beta);

        if (minMove[0] === null || nextMove[1] < minMove[1]) {
          minMove[0] = i;
          minMove[1] = nextMove[1];
          beta = nextMove[1];
        }

        if (alpha >= beta) return minMove;
      }
    }

    return minMove;
  }

  createBoard() {
    var board = new Array(6);
    for (let i = 0; i < 6; i++) {
      board[i] = new Array(7).fill(0);
    }

    for (let i = 0; i < 6; i++) {
      for (let j = 0; j < 7; j++) {
        let cell = this.getCell(i, j);
        if (cell.hasClass("red") && cell.attr("empty") === "false")
          board[i][j] = 1;
        else if (cell.hasClass("yellow") && cell.attr("empty") === "false")
          board[i][j] = 2;
      }
    }
    board["player"] = 2;
    return board;
  }

  copyBoard(board) {
    var copiedBoard = new Array(6);
    for (let i = 0; i < 6; i++) {
      copiedBoard[i] = new Array(7).fill(0);
    }

    for (let i = 0; i < 6; i++) {
      for (let j = 0; j < 7; j++) {
        copiedBoard[i][j] = board[i][j];
      }
    }
    copiedBoard.player = board.player;
    return copiedBoard;
  }

  isFinshed(depth, score, board) {
    if (
      depth === 0 ||
      score >= this.score ||
      score <= -this.score ||
      this.isFull(board)
    ) {
      return true;
    }
    return false;
  }

  isFull(board) {
    for (let j = 0; j < 7; j++) {
      if (board[0][j] === 0) return false;
    }
  }

  placeMove(board, col) {
    if (board[0][col] !== 0) {
      return false;
    }
    for (let i = 5; i >= 0; i--) {
      if (board[i][col] === 0) {
        board[i][col] = board.player;
        break;
      }
    }
    board.player = board.player === 1 ? 2 : 1;
    return true;
  }

  makeMove() {
    var col = this.maximize(this.createBoard(), this.depth)[0];
    for (let i = 5; i >= 0; i--) {
      if (this.getCell(i, col).attr("empty") === "true")
        return this.getCell(i, col);
    }
  }
}
