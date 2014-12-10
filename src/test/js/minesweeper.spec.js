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

    function loc(row, col) {
        return '(' + row + ', ' + col + ')';
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
                ['empty', 'bomb' , 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty', 'empty']
            ];
            new minesweeper.Board(grid).render(field);
        });

        it('renders a field of same size as the grid', function () {
            for (var row = 1; row <= 8; row++) {
                for (var col = 1; col <= 8; col++) {
                    should.exist(cell(row, col), loc(row, col));
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
                ['bomb' , 'empty', 'empty']
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

        it('indicates number of neighboring bombs when cell is safe', function () {
            for (var row = 1; row <= 3; row++) {
                for (var col = 1; col <= 3; col++) {
                    mouse.click(cell(row, col)).should.be.ok(loc(row, col));
                }
            }
            cell(1, 1).textContent.should.equal('');
            cell(2, 1).textContent.should.equal('1');
            cell(1, 2).textContent.should.equal('1');
            cell(3, 3).textContent.should.equal('1');
            cell(2, 2).textContent.should.equal('2');
            cell(3, 2).textContent.should.equal('2');
            cell(3, 3).textContent.should.equal('1');
        });
    });

    describe('when playing', function () {
        var safeCells;

        beforeEach(function () {
            field = document.getElementById('board');
            var grid = [
                ['empty', 'empty', 'empty'],
                ['bomb',  'empty', 'bomb' ],
                ['empty', 'empty', 'empty']
            ];
            safeCells = [
                [1, 1], [1, 2], [1, 3],
                [2, 2],
                [3, 1], [3, 2], [3, 3]
            ];
            new minesweeper.Board(grid).render(field);
        });

        it('reveals safe cells around and around, etc.', function () {
            mouse.click(cell(1, 1)).should.be.ok;
            safeCells.forEach(function(pos) {
                cell(pos[0], pos[1]).className.should.equal('safe', loc(pos[0], pos[1]));
            });
        });
    });
});

describe("grid", function() {
    var grid;

    describe("with 0 rows", function() {
        beforeEach(function () {
            grid = minesweeper.Grid(0, 0)(function() {});
        });

        it("produces a grid without content", function() {
            grid.length.should.equal(0);
        });
    });

    describe("with rows and cols", function() {
        beforeEach(function () {
            grid = minesweeper.Grid(8, 6)(function() { return 'empty' });
        });

        it("fills field with generated content", function() {
            grid.length.should.equal(8);
            for (var row = 0; row < 8; row++) {
                for (var col = 0; col < 6; col++) {
                    grid[row][col].should.equal('empty');
                }
            }
        });
    });
});

describe("random generator", function() {
    var generator;

    describe("without bombs", function () {
        beforeEach(function () {
            generator = new minesweeper.Generator(0);
        });

        it("generate empty cells", function () {
            for (var n = 0; n < 100; n++) {
                generator().should.equal('empty');
            }
        });
    });

    describe("with only bombs", function () {
        beforeEach(function () {
            generator = new minesweeper.Generator(1);
        });

        it("fills the field with bombs only", function () {
            for (var n = 0; n < 100; n++) {
                generator().should.equal('bomb');
            }
        });
    });

    describe("with random bombs", function () {
        beforeEach(function () {
            generator = new minesweeper.Generator(0.45);
        });

        it("fills the field randomly with bombs", function () {
            var bombs = 0;
            for (var n = 0; n < 10000; n++) {
                if (generator() == 'bomb') bombs++;
            }
            bombs.should.be.below(5000);
            bombs.should.be.above(4000);
        });
    });
});