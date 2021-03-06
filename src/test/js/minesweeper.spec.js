describe('minesweeper', function () {
    var mouse = effroi.mouse;
    var field;

    beforeEach(function () {
        document.body.innerHTML = '<table id="board"></table>';
    });

    function cell(row, col) {
        return field.querySelector('#cell-' + row + 'x' + col);
    }

    function loc(row, col) {
        return '(' + row + ', ' + col + ')';
    }

    describe('when loading grid', function () {
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
            cell(3, 1).className.should.have.string('lost');
            cell(3, 1).textContent.should.equal('');
        });

        it('sets cell class to safe if it is empty', function () {
            mouse.click(cell(2, 2)).should.be.ok;
            cell(2, 2).className.should.have.string('safe');
        });

        it('indicates number of neighboring bombs when cell is safe', function () {
            for (var row = 1; row <= 3; row++) {
                for (var col = 1; col <= 3; col++) {
                    mouse.click(cell(row, col)).should.be.ok;
                }
            }
            cell(1, 1).textContent.should.equal('');
            cell(2, 1).textContent.should.equal('1');
            cell(2, 1).className.should.have.string('safe-1');
            cell(2, 2).textContent.should.equal('2');
            cell(2, 2).className.should.have.string('safe-2');
        });

        it('cannot reveal a suspect cell', function () {
            mouse.rightclick(cell(3, 1)).should.be.ok;
            mouse.click(cell(3, 1)).should.be.ok;
            cell(3, 1).className.should.not.have.string('lost');
            mouse.rightclick(cell(3, 1)).should.be.ok;
            mouse.click(cell(3, 1)).should.be.ok;
            cell(3, 1).className.should.have.string('lost');
        });
    });

    describe('when flagging suspects', function () {
        beforeEach(function () {
            field = document.getElementById('board');
            var grid = [
                ['empty', 'empty', 'empty'],
                ['empty', 'empty', 'bomb'],
                ['bomb', 'empty', 'empty']
            ];
            new minesweeper.Board(grid).render(field);
        });

        it('sets cell class to suspect if still hidden', function () {
            mouse.rightclick(cell(3, 1)).should.be.ok;
            cell(3, 1).className.should.have.string('suspect');
            cell(3, 1).textContent.should.equal('');
        });

        it('cannot flag an exploded bomb', function () {
            mouse.click(cell(3, 1)).should.be.ok;
            mouse.rightclick(cell(3, 1)).should.be.ok;
            cell(3, 1).className.should.have.string('lost');
        });

        it('cannot flag a safe cell', function () {
            mouse.click(cell(2, 1)).should.be.ok;
            mouse.rightclick(cell(2, 1)).should.be.ok;
            cell(2, 1).className.should.have.string('safe');
        });

        it('removes flag when set', function () {
            mouse.rightclick(cell(3, 1)).should.be.ok;
            mouse.rightclick(cell(3, 1)).should.be.ok;
            cell(3, 1).className.should.not.have.string('suspect');
        });
    });

    describe('when playing', function () {
        var safeCells;

        beforeEach(function () {
            field = document.getElementById('board');
            var grid = [
                ['empty', 'empty', 'empty', 'empty', 'empty'],
                ['empty', 'bomb' , 'empty', 'empty', 'empty'],
                ['empty', 'empty', 'bomb' , 'empty', 'empty'],
                ['bomb',  'empty', 'empty', 'empty', 'bomb' ],
                ['empty', 'empty', 'empty', 'empty', 'empty']
            ];
            new minesweeper.Board(grid).render(field);
        });

        it('reveals safe neighbors if no bomb is around ', function () {
            mouse.click(cell(5, 3)).should.be.ok;
            [[5, 2], [5, 4], [4, 2], [4, 3], [4, 4]].forEach(function(pos) {
                cell(pos[0], pos[1]).className.should.have.string('safe', loc(pos[0], pos[1]));
            });
        });

        it('reveals no neighbor when bomb is around', function () {
            mouse.click(cell(1, 1)).should.be.ok;
            [[1, 2], [2, 1]].forEach(function(pos) {
                cell(pos[0], pos[1]).className.should.equal('', loc(pos[0], pos[1]));
            });
        });

        it('keeps revealing neighbors if no bomb is still around', function () {
            mouse.click(cell(1, 5)).should.be.ok;
            [[1, 3], [3, 4], [3, 5]].forEach(function(pos) {
                cell(pos[0], pos[1]).className.should.have.string('safe', loc(pos[0], pos[1]));
            });
        });

        it('does not reveal neighbors of bombs', function () {
            mouse.click(cell(4, 1)).should.be.ok;
            [[3, 1], [3, 2], [4, 2], [5, 1], [5, 2]].forEach(function(pos) {
                cell(pos[0], pos[1]).className.should.equal('', loc(pos[0], pos[1]));
            });
        });

        it('does not reveal suspect cells', function () {
            mouse.rightclick(cell(2, 4)).should.be.ok;
            mouse.click(cell(1, 5)).should.be.ok;
            cell(2, 4).className.should.equal('suspect');
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