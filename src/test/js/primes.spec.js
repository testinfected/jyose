describe('ajax', function () {
    describe('without data', function () {
        var parameters;

        beforeEach(function () {
            parameters = ajax.encode({});
        });

        it('does not create parameters', function () {
            parameters.should.equal('');
        });
    });

    describe('when encoding values', function () {
        var parameters;

        beforeEach(function () {
            parameters = ajax.encode({
                number: 42,
                name: 'primes'
            });
        });

        it('creates name value pairs', function () {
            parameters.should.equal('number=42&name=primes');
        });
    });

    describe('when encoding', function () {
        var parameters;

        beforeEach(function () {
            parameters = ajax.encode({
                say: "I'll be back"
            });
        });

        it('escapes values', function () {
            parameters.should.equal("say=I'll%20be%20back");
        });
    });

    describe('when encoding arrays', function () {
        var parameters;

        beforeEach(function () {
            parameters = ajax.encode({
                number: [42, 37, 12]
            });
        });

        it('creates multiple name value pairs', function () {
            parameters.should.equal('number=42&number=37&number=12');
        });
    });
});

describe('primes', function () {

    describe('when parsing form', function () {
        var form;
        var settings;

        beforeEach(function () {
            document.body.innerHTML =
                '<form id="primes" action="/url" method="post" enctype="application/x-www-form-urlencoded">' +
                '   <input id ="number" name="number" value=""/>' +
                '</form>';
            form = document.getElementById('primes')
        });

        describe('always', function() {
            beforeEach(function() {
                settings = primes.Form.parse(form).settings;
            });

            it('reads the form method', function () {
                settings.should.have.property('method', 'post');
            });

            it('reads the form action', function () {
                settings.should.have.property('url').with.string('/url');
            });

            it('reads the form encoding', function () {
                settings.should.have.property('encoding', 'application/x-www-form-urlencoded');
            });
        });

        describe('with a single number', function() {
            beforeEach(function() {
                form.querySelector('#number').value = 'value';
                settings = primes.Form.parse(form).settings;
            });

            it('includes the number in the data', function () {
                settings.should.have.deep.property('data.number[0]', 'value');
            });
        });

        describe('with multiple numbers', function() {
            beforeEach(function() {
                form.querySelector('#number').value = 'first, second, third';
                settings = primes.Form.parse(form).settings;
            });

            it('splits number input and includes all values', function () {
                settings.should.have.deep.property('data.number[0]', 'first');
                settings.should.have.deep.property('data.number[1]', 'second');
                settings.should.have.deep.property('data.number[2]', 'third');
            });
        });
    });
});

describe('rendering', function () {
    beforeEach(function () {
        document.body.innerHTML =
            '<span id="last-decomposition"></span>' +
            '<span id="result"></span>' +
            '<ol id="results"></ol>';
    });

    function result() {
        return document.querySelector('#result').innerHTML;
    }

    function results() {
        return document.querySelector('#results').innerHTML;
    }

    function item(number) {
        return document.querySelector('#results li:nth-child(' + number + ')').innerHTML;
    }

    function lastResult() {
        return document.querySelector('#last-decomposition').innerHTML;
    }

    describe('a single decomposition', function() {
        describe('when successful', function () {
            it('displays the original number', function () {
                var factors = {number: 1, decomposition: []};
                primes.renderDecompositions(factors);
                result().should.contain('1');
            });

            it('displays the prime factors decomposition of the number', function () {
                var factors = {number: 66, decomposition: [2, 3, 11]};
                primes.renderDecompositions(factors);
                result().should.contain('= 2 x 3 x 11');
            });
        });

        describe('when failing', function () {
            it('indicates when an error occurs', function () {
                var factors = {number: 1000001, error: "number too big"};
                primes.renderDecompositions(factors);
                result().should.equal('number too big');
            });

            it('includes the input in the error message when not a number', function () {
                var factors = {number: "1allo", error: "not a number"};
                primes.renderDecompositions(factors);
                result().should.equal('1allo is not a number');
            });
        })
    });

    describe('multiple decompositions', function() {
        it('displays each result in a list', function () {
            var factors = [
                {number: 66, decomposition: [2, 3, 11]},
                {number: 1000001, error: "number too big"},
                {number: "1allo", error: "not a number"}
            ];
            primes.renderDecompositions(factors);

            item(1).should.equal('66 = 2 x 3 x 11');
            item(2).should.equal('number too big');
            item(3).should.equal('1allo is not a number');
        });
    });

    describe('successive decompositions', function() {
        it('clears preceding result', function () {
            primes.renderDecompositions({number: 66, decomposition: [2, 3, 11]});
            results().should.be.equal('');
            primes.renderDecompositions([
                {number: 1000001, error: "number too big"},
                {number: "1allo", error: "not a number"}
            ]);
            result().should.equal('');
            primes.renderDecompositions({number: 66, decomposition: [2, 3, 11]});
            results().should.equal('')
        });
    });

    describe('last decomposition', function() {
        it('omits if absent', function () {
            primes.renderLastDecomposition({});
            lastResult().should.be.equal('');
        });

        it('displays if included', function () {
            primes.renderLastDecomposition({number: 22, decomposition: [2, 11]});
            lastResult().should.equal('22 = 2 x 11');
        });
    });
});