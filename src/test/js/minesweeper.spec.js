describe('board', function () {
    describe('when loading data grid', function () {
        var mouse = effroi.mouse;
        var field;

        beforeEach(function () {
            var body = document.querySelector('body');
            body.innerHTML = '<table id="board"></table>';
            field = document.getElementById('board');
            var grid = [
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'bomb',  'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty']
            ];
            minesweeper.Board.load(grid).render(field);
        });

        function cell(row, col) {
            return field.querySelector('#cell-' + row + 'x' + col);
        }

        it('renders a field of same size as the grid', function () {
            for (var row = 1; row <= 8; row++) {
                for (var col = 1; col <= 8; col++) {
                    cell(row, col).should.exist();
                }
            }
        });

        it('stores cell content as a data attribute', function () {
            cell(1, 1).getAttribute('data-content').should.equal('empty');
            cell(6, 2).getAttribute('data-content').should.equal('bomb');
        });

        it('sets cell class to lost when clicked if it contains a bomb', function () {
            mouse.click(cell(6, 2)).should.be.ok;
            cell(6, 2).className.should.equal('lost');
        });

        it('sets cell class to safe when clicked if it is empty', function () {
            mouse.click(cell(1, 1)).should.be.ok;
            cell(1, 1).className.should.equal('safe');
        });
    });
});