describe('ajax', function () {
    describe('when encoding data', function () {
        var parameters;

        beforeEach(function () {
            parameters = ajax.encode({
                number: 'value'
            });
        });

        it('creates name value pairs', function () {
            parameters.should.equal('number=value');
        });
    });
});

describe('primes', function () {

    describe('when parsing form', function () {
        var settings;

        beforeEach(function () {
            var fragment = document.createDocumentFragment();
            var form = document.createElement("form");
            form.id = 'primes';
            form.setAttribute('action', '/url');
            form.setAttribute('method', 'post');
            form.setAttribute('enctype', 'application/x-www-form-urlencoded');
            var number = document.createElement('input');
            number.id = 'number';
            number.setAttribute('name', 'number');
            number.value = 'value';
            form.appendChild(number);
            fragment.appendChild(form);
            settings = primes.Form.parse(form).settings;
        });

        it('reads the form method', function () {
            settings.should.have.property('method', 'post');
        });

        it('reads the form action', function () {
            settings.should.have.property('url');
            settings.url.should.have.string('/url');
        });

        it('reads the form encoding', function () {
            settings.should.have.property('encoding', 'application/x-www-form-urlencoded');
        });

        it('includes the number input in the data', function () {
            settings.should.have.property('data');
            settings.data.should.have.property('number', 'value');
        });
    });
});

describe('rendering results', function () {
    var result;

    beforeEach(function () {
        var fragment = document.createDocumentFragment();
        result = document.createElement("div");
        result.id = 'result';
        fragment.appendChild(result);
    });

    it('displays the original number', function () {
        var factors = {number: 1, decomposition: []};
        primes.render(result)(factors);
        result.innerHTML.should.contain('1');
    });

    it('displays the prime factors decomposition of the number', function () {
        var factors = {number: 66, decomposition: [2, 3, 11]};
        primes.render(result)(factors);
        result.innerHTML.should.contain('= 2 x 3 x 11');
    });
});