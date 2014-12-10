var minesweeper = {};

minesweeper.Board = function (grid) {
    function bombAt(row, col) {
        return legal(row, col) && grid[row][col] == 'bomb';
    }

    function legal(row, col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length;
    }

    function cell(row, col) {
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

        function decorate(row, col) {
            var cell = $(row, col);
            cell.className = bombAt(row, col) ? 'lost' : 'safe';

            var bombs = bombsAround(row, col);
            if (safe(row, col) && bombs != 0) {
                cell.textContent = bombs.toString();
                cell.className += ' safe-' + bombs
            }
        }

        function revealed(row, col) {
            return $(row, col).className != '';
        }

        function safe(row, col) {
            return !bombAt(row, col);
        }

        function reveal(row, col) {
            if (revealed(row, col)) return;
            decorate(row, col);

            if (safe(row, col) && bombsAround(row, col) == 0) {
                legalNeighbors(row, col).forEach(function (pos) {
                    reveal(pos.row, pos.col);
                });
            }
        }

        var cell = document.createElement('td');
        cell.id = id(row, col);
        cell.addEventListener('click', function () { reveal(row, col); });
        return cell;
    }

    this.render = function (on) {
        on.innerHTML = '';
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

        document.grid = minesweeper.Grid(8, 8)(minesweeper.Generator(0.25));
        load();
    });
}());
