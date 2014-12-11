var minesweeper = {};

minesweeper.suspectMode = false;

minesweeper.toggleSuspectMode = function() {
    this.suspectMode = !this.suspectMode;
};

minesweeper.Board = function (grid) {
    var RIGHT_BUTTON = 3;

    function bombAt(row, col) {
        return legal(row, col) && grid[row][col] == 'bomb';
    }

    function legal(row, col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length;
    }

    function id(row, col) {
        return 'cell-' + (row + 1) + 'x' + (col + 1);
    }

    function neighbors(row, col) {
        var around = [
            [-1, -1], [-1, 0], [-1, 1],
            [0, -1],           [0, 1],
            [1, -1],  [1, 0],  [1, 1]
        ];

        return around.map(function (pos) {
            return {row: row + pos[0], col: col + pos[1]}
        });
    }

    function legalNeighbors(row, col) {
        return neighbors(row, col).filter(function (pos) { return legal(pos.row, pos.col); });
    }

    function unknownNeighbors(row, col) {
        return legalNeighbors(row, col).filter(function (pos) { return !revealed(pos.row, pos.col); });
    }

    function bombsAround(row, col) {
        var bombs = 0;
        legalNeighbors(row, col).forEach(function (pos) {
            if (bombAt(pos.row, pos.col)) bombs += 1;
        });
        return bombs;
    }

    function $(row, col) {
        return document.getElementById(id(row, col));
    }

    function markSafe(row, col) {
        var cell = $(row, col);
        cell.className = 'safe';

        var bombs = bombsAround(row, col);
        if (bombs != 0) {
            cell.textContent = bombs.toString();
            cell.className += ' safe-' + bombs
        }
    }

    function markLost(row, col) {
        $(row, col).className = 'lost';
    }

    function revealed(row, col) {
        var classes = $(row, col).className;
        return ~classes.indexOf('lost') || ~classes.indexOf('safe');
    }

    function reveal(row, col) {
        if (flagged(row, col)) return;

        if (bombAt(row, col)) {
            markLost(row, col);
            return;
        }

        markSafe(row, col);

        if (bombsAround(row, col) == 0) {
            unknownNeighbors(row, col).forEach(function (pos) {
                reveal(pos.row, pos.col);
            });
        }
    }

    function flagged(row, col) {
        return ~$(row, col).className.indexOf('suspect');
    }

    function toggleFlag(row, col) {
        if (revealed(row, col)) return;
        $(row, col).className = flagged(row, col) ? '' : 'suspect';
    }

    function Cell(row, col) {
        this.row = row;
        this.col = col;

        this.onclick = function(handler) {
            this.elem.addEventListener('click', function(event) {
                if (event.which != RIGHT_BUTTON && !minesweeper.suspectMode) handler(event);
            });
        };

        this.onrightclick = function(handler) {
            this.elem.addEventListener('click', function(event) {
                if (event.which == RIGHT_BUTTON || minesweeper.suspectMode) handler(event);
            });
            this.elem.addEventListener('contextmenu', function (event) {
                event.preventDefault();
                handler(event);
            });
        };

        this.render = function() {
            this.elem = document.createElement('td');
            this.elem.id = id(this.row, this.col);
            return this.elem;
        };
    }

    this.render = function (on) {
        on.innerHTML = '';

        function cell(row, col) {
            var cell = new Cell(row, col);
            var td = cell.render();
            cell.onclick(function (event) {
                reveal(row, col);
            });
            cell.onrightclick(function (event) {
                toggleFlag(row, col);
            });
            return td;
        }

        function line(row) {
            var tr = document.createElement('tr');
            for (var col = 0; col < grid[row].length; col++) {
                tr.appendChild(cell(row, col));
            }
            return tr;
        }

        for (var row = 0; row < grid.length; row++) {
            on.appendChild(line(row));
        }
    }
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

function load() {
    var grid = document.grid;
    var field = document.getElementById('board');
    var board = new minesweeper.Board(grid);
    board.render(field);
}

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById("test-mode").addEventListener('click', function() {
            document.grid = [
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['bomb' , 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['bomb' , 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'bomb',  'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty']
            ];
            load();
        });

        document.getElementById("suspect-mode").addEventListener('click', function() {
            minesweeper.toggleSuspectMode();
        });

        document.grid = minesweeper.Grid(8, 8)(minesweeper.Generator(0.20));
        load();
    });
}());
