describe('board', function () {
    var mouse = effroi.mouse;
    var field;

    beforeEach(function () {
        var body = document.querySelector('body');
        body.innerHTML = '<table id="board"></table>';
    });

    function cell(row, col) {
        return field.querySelector('#cell-' + row + 'x' + col);
    }

    describe('when loading data grid', function () {
        beforeEach(function () {
            field = document.getElementById('board');
            var grid = [
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'bomb', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty']
            ];
            new minesweeper.Board(grid).render(field);
        });

        it('renders a field of same size as the grid', function () {
            for (var row = 1; row <= 8; row++) {
                for (var col = 1; col <= 8; col++) {
                    cell(row, col).should.exist();
                }
            }
        });
    });

    describe('when clicking cells', function () {
        beforeEach(function () {
            field = document.getElementById('board');
            var grid = [
                ['empty', 'empty', 'empty'],
                ['empty', 'empty', 'bomb'],
                ['bomb', 'empty', 'empty']
            ];
            new minesweeper.Board(grid).render(field);
        });

        it('sets cell class to lost if it contains a bomb', function () {
            mouse.click(cell(3, 1)).should.be.ok;
            cell(3, 1).className.should.equal('lost');
        });

        it('sets cell class to safe if it is empty', function () {
            mouse.click(cell(2, 2)).should.be.ok;
            cell(2, 2).className.should.equal('safe');
        });

        it('indicates number of neighboring bombs when cell is safe', function() {
            for (var row = 1; row <=3; row++) {
                for (var col = 1; col <=3; col++) {
                    cell(row, col).textContent.should.equal('');
                    mouse.click(cell(row, col)).should.be.ok;
                }
            }
            cell(1, 1).textContent.should.equal('0');
            cell(2, 1).textContent.should.equal('1');
            cell(1, 2).textContent.should.equal('1');
            cell(3, 3).textContent.should.equal('1');
            cell(2, 2).textContent.should.equal('2');
            cell(3, 2).textContent.should.equal('2');
            cell(3, 3).textContent.should.equal('1');
        })
    });
});