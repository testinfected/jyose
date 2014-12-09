var minesweeper = {};

minesweeper.Board = function (grid) {
    function bombAt(row, col) {
        return legal(row, col) && grid[row][col] == 'bomb';
    }

    function legal(row, col) {
        return row >= 0 && row < grid.length;
    }

    function cell(row, col) {
        function id(row, col) {
            return 'cell-' + row + 'x' + col;
        }

        function neighbors() {
            var around = [
                [-1, -1], [-1, 0], [-1, 1],
                [0, -1], [0, 1],
                [1, -1], [1, 0], [1, 1]
            ];

            return around.map(function (pos) {
                return {row: row + pos[0], col: col + pos[1]}
            });
        }

        function bombsAround() {
            var bombs = 0;
            neighbors().forEach(function (pos) {
                if (bombAt(pos.row, pos.col)) bombs += 1;
            });
            return bombs;
        }

        function reveal(me) {
            me.className = bombAt(row, col) ? 'lost' : 'safe';
            me.textContent = bombsAround().toString();
        }

        var cell = document.createElement('td');
        cell.id = id(row + 1, col + 1);

        cell.addEventListener('click', function () {
            reveal(this);
        });

        return cell;
    }

    this.render = function (on) {
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
            ['bomb', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['bomb', 'empty', 'empty', 'bomb', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'bomb', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'bomb', 'empty', 'bomb', 'bomb', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'bomb', 'empty']
        ];
        load();
    });
}());
