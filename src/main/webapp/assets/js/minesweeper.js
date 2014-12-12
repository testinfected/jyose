var minesweeper = {};

minesweeper.suspectMode = false;

minesweeper.toggleSuspectMode = function() {
    this.suspectMode = !this.suspectMode;
};

minesweeper.Cell = function(row, col, content) {
    var RIGHT_BUTTON = 3;

    function init() {
        this.row = row;
        this.col = col;
        this.content = content;
        this.elem = document.createElement('td');
        this.elem.id = id(this.row, this.col);
    }

    function id(row, col) {
        return 'cell-' + (row + 1) + 'x' + (col + 1);
    }

    this.onClick = function(handler) {
        this.elem.addEventListener('click', function(event) {
            if (event.which != RIGHT_BUTTON && !minesweeper.suspectMode) handler(event);
        });
    };

    this.onRightClick = function(handler) {
        this.elem.addEventListener('click', function(event) {
            if (event.which == RIGHT_BUTTON || minesweeper.suspectMode) handler(event);
        });
        this.elem.addEventListener('contextmenu', function (event) {
            event.preventDefault();
            handler(event);
        });
    };

    this.reveal = function(board) {
        if (this.flagged()) return;

        if (this.trapped()) {
            this.markLost();
            return;
        }

        this.markSafe(board);

        if (this.bombsAround(board) == 0) {
            this.maskedNeighbors(board).forEach(function (neighbor) {
                neighbor.reveal(board);
            });
        }
    };

    this.vicinity = function() {
        var around = [
            [-1, -1], [-1, 0], [-1, 1],
            [0, -1],           [0, 1],
            [1, -1],  [1, 0],  [1, 1]
        ];

        return around.map(function translate(pos) {
            return {row: this.row + pos[0], col: this.col + pos[1]}
        }.bind(this));
    };

    this.neighbors = function(board) {
        return this.vicinity().
            filter(function (loc) { return board.legal(loc.row, loc.col); }).
            map(function(loc) { return board.cellAt(loc.row, loc.col)});
    };

    this.maskedNeighbors = function(board) {
        return this.neighbors(board).filter(function (neighbor) { return !neighbor.revealed(); });
    };

    this.bombsAround = function(board) {
        var bombs = 0;
        this.neighbors(board).forEach(function (neighbor) {
            if (neighbor.trapped()) bombs += 1;
        });
        return bombs;
    };

    this.markSafe = function(board) {
        this.elem.className = 'safe';

        var bombs = this.bombsAround(board);
        if (bombs != 0) {
            this.elem.textContent = bombs.toString();
            this.elem.className += ' safe-' + bombs
        }
    };

    this.markLost = function() {
        this.elem.className = 'lost';
    };

    this.revealed = function() {
        var classes = this.elem.className;
        return ~classes.indexOf('lost') || ~classes.indexOf('safe');
    };

    this.trapped = function() {
        return this.content == 'bomb';
    };

    this.flagged = function() {
        return ~this.elem.className.indexOf('suspect');
    };

    this.toggleFlag = function() {
        if (this.revealed()) return;
        this.elem.className = this.flagged() ? '' : 'suspect';
    };

    this.render = function() {
        return this.elem;
    } ;

    init.call(this);
};

minesweeper.Board = function (grid) {
    function init(grid) {
        this.cells = [[]];
        for (var row = 0; row < grid.length; row++) {
            this.cells.push([]);
            for (var col = 0; col < grid[row].length; col++) {
                this.cells[row].push(makeCell.call(this, row, col, grid[row][col]));
            }
        }
    }

    function makeCell(row, col, content) {
        var cell = new minesweeper.Cell(row, col, content);

        cell.onClick(function(event) {
            cell.reveal(this)
        }.bind(this));

        cell.onRightClick(function (event) {
            cell.toggleFlag();
        });

        return cell;
    }

    this.legal = function(row, col) {
        return row >= 0 && row < this.cells.length && col >= 0 && col < this.cells[row].length;
    };

    this.cellAt = function(row, col) {
        return this.cells[row][col];
    };

    this.render = function (on) {
        on.innerHTML = '';

        for (var row = 0; row < this.cells.length; row++) {
            var tr = document.createElement('tr');
            for (var col = 0; col < this.cells[row].length; col++) {
                tr.appendChild(this.cellAt(row, col).render());
            }
            on.appendChild(tr);
        }
    };

    init.call(this, grid);
};

minesweeper.Grid = function(height, width) {
    return function(generator) {
        var data = [];
        for (var row = 0; row < height; row++) {
            data.push([]);
            for (var col = 0; col < width; col++) {
                data[row].push(generator());
            }
        }
        return data;
    };
};

minesweeper.Generator = function(occurence) {
    return function generate() {
        return Math.random() <= occurence ? 'bomb' : 'empty';
    };
};

function load(data) {
    document.grid = data || document.grid;
    var field = document.getElementById('board');
    var board = new minesweeper.Board(document.grid);
    board.render(field);
}

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById("test-mode").addEventListener('click', function() {
            load([
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['bomb' , 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['bomb' , 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'bomb',  'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty']
            ]);
        });

        document.getElementById("suspect-mode").addEventListener('click', function() {
            minesweeper.toggleSuspectMode();
        });

        load(minesweeper.Grid(8, 8)(minesweeper.Generator(10/64)));
    });
}());
