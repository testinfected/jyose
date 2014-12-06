describe('board', function () {
    describe('upon generation', function () {
        var grid;
        var rows = 8;
        var cols = 8;

        beforeEach(function () {
            var body = document.querySelector('body');
            body.innerHTML = '<table id="board"></table>';
            grid = document.getElementById('board');
            minesweeper.Board.generate(rows, cols).render(grid);
        });

        it('renders a grid properly sized', function () {
            for (var row = 1; row <= rows; row++) {
                for (var col = 1; col <= cols; col++) {
                    grid.querySelector('#cell-' + row + 'x' + col).should.exist();
                }
            }
        });
    });
});