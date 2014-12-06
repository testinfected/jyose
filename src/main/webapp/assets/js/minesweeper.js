var minesweeper = {};

minesweeper.Board = {
    generate: function (rows, cols) {
        return {
            render: function (on) {
                for (var row = 1; row <= rows; row++) {
                    on.appendChild(this._renderRow(row));
                }
            },

            _renderRow: function (row) {
                var tr = document.createElement('tr');
                for (var col = 1; col <= cols; col++) {
                    tr.appendChild(this._renderCell(row, col));
                }
                return tr;
            },

            _renderCell: function (row, col) {
                var cell = document.createElement('td');
                cell.id = 'cell-' + row + 'x' + col;
                return cell;
            }
        }
    }
};

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        minesweeper.Board.generate(8, 8).render(document.getElementById('board'));
    });
}());
