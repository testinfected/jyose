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

        function safeNeighbors(row, col) {
            return legalNeighbors(row, col).filter(function(pos) { return !bombAt(pos.row, pos.col)});
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
            $(row, col).className = bombAt(row, col) ? 'lost' : 'safe';
            $(row, col).textContent = bombsAround(row, col) != 0 ? bombsAround(row, col).toString() : '';
        }

        function revealed(row, col) {
            return $(row, col).className != '';
        }

        function reveal(row, col) {
            if (revealed(row, col)) return;
            decorate(row, col);

            safeNeighbors(row, col).forEach(function(pos) {
                reveal(pos.row, pos.col);
            });
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

function load() {
    var grid = document.grid;
    var field = document.getElementById('board');
    var board = new minesweeper.Board(grid);
    board.render(field);
}

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        // let's simulate yose game server behavior
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
}());
