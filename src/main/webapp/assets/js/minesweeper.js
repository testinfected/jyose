var minesweeper = {};

minesweeper.Board = {
    load: function (grid) {
        return {
            render: function (on) {
                function cellId(row, col) {
                    return 'cell-' + row + 'x' + col;
                }

                function cell(row, col) {
                    var cell = document.createElement('td');
                    cell.id = cellId(row + 1, col + 1);
                    cell.innerHTML = '(' + (row + 1) + ',' + (col + 1) + ')';
                    cell.setAttribute('data-content', grid[row][col]);
                    cell.addEventListener('click', function() {
                        this.className = this.getAttribute('data-content') == 'bomb' ? 'lost' : 'safe';
                    });
                    return cell;
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
        }
    }
};

function load() {
    var grid = document.grid;
    var field = document.getElementById('board');
    minesweeper.Board.load(grid).render(field);
}

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        // let's simulate yose game server behavior
        document.grid = [
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'bomb' , 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
            ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty']
        ];
        load();
    });
}());
