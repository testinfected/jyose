describe('ajax', function () {
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
            [12].map(function(v) { return v}).join('&name=').should.equal('12');
            [12, 13].map(function(v) { return v }).join('&name=').should.equal('12&name=13');
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
            var fragment = document.createDocumentFragment();
            form = document.createElement("form");
            form.id = 'primes';
            form.setAttribute('action', '/url');
            form.setAttribute('method', 'post');
            form.setAttribute('enctype', 'application/x-www-form-urlencoded');
            var number = document.createElement('input');
            number.id = 'number';
            number.setAttribute('name', 'number');
            form.appendChild(number);
            fragment.appendChild(form);
        });

        describe('in any case', function() {
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

describe('rendering results', function () {
    var result;

    function render(factors) {
        primes.renderIn(result)(factors);
        return result.innerHTML;
    }

    beforeEach(function () {
        var fragment = document.createDocumentFragment();
        result = document.createElement("div");
        result.id = 'result';
        fragment.appendChild(result);
    });

    describe('when decomposition succeeds', function () {
        it('displays the original number', function () {
            var factors = {number: 1, decomposition: []};
            render(factors).should.contain('1');
        });

        it('displays the prime factors decomposition of the number', function () {
            var factors = {number: 66, decomposition: [2, 3, 11]};
            render(factors).should.contain('= 2 x 3 x 11');
        });
    });

    describe('when decomposition fails', function () {
        it('indicates when number is too big', function () {
            var factors = {"number": 1000001, "error": "number too big"};
            render(factors).should.equal('number too big');
        });

        it('indicates when input is not a number', function () {
            var factors = {"number": "1allo", "error": "not a number"};
            render(factors).should.equal('1allo is not a number');
        })
    })
});