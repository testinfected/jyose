var ajax = {
    encode: function (data) {
        var parameters = [];

        for (var key in  data) {
            var name = encodeURIComponent(key);
            var value = data[key] instanceof Array ?
                data[key].map(function(v) { return encodeURIComponent(v) }).join('&' + name + '=') :
                encodeURIComponent(data[key]);
            parameters.push(name + '=' + value);
        }
        return parameters.join('&');
    },

    send: function (settings, done) {
        var request = new XMLHttpRequest();
        //Yose uses Zombie which does not support send data, so we append data to the url
        request.open(settings.method, settings.url + '?' + this.encode(settings.data));
        if (settings.encoding)
            request.setRequestHeader("Content-type", settings.encoding);
        request.onload = function () {
            done(JSON.parse(request.responseText))
        };
        request.send();
        //request.send(this.encode(settings.data));
    }
};

var primes = {};

primes.Form = {
    parse: function (form) {
        function Form(settings) {
            this.settings = settings;

            this.submit = function (render) {
                ajax.send(this.settings, render);
            };

            this.clear = function() {
                form.querySelector('#number').value = ''
            }
        }

        function read(form) {
            var settings = {
                method: form.method,
                url: form.action,
                encoding: form.enctype,
                data: {}
            };
            var number = form.querySelector('#number');
            settings.data[number.name] = number.value.split(',').map(function(value) { return value.trim(); });
            return settings;
        }

        return new Form(read(form));
    }
};

primes.renderDecompositions = function (data) {
    function renderAll(container) {
        for (var i = 0; i < data.length; i++) {
            container.innerHTML += '<li>' + primes.format(data[i]) + '</li>'
        }
    }

    function renderSingle(container) {
        container.innerHTML = primes.format(data);
    }

    var results = document.querySelector("#results");
    results.innerHTML = '';
    var result = document.querySelector("#result");
    result.innerHTML = '';

    if (data instanceof Array) {
        renderAll(results);
    } else {
        renderSingle(result);
    }
};

primes.format = function (data) {
    if (data.decomposition) {
        return data.number + ' = ' + data.decomposition.join(' x ');
    } else if (data.error == 'not a number') {
        return data.number + ' is ' + data.error;
    } else if (data.error) {
        return data.error;
    } else {
        return '';
    }
};

primes.Last = {
    get: function (render) {
        this.settings = {
            url: '/primeFactors/last',
            method: 'get',
            data: {}
        };

        ajax.send(this.settings, render);
    }
};

primes.renderLastDecomposition = function (data) {
    var container = document.querySelector("#last-decomposition");
    container.innerHTML = primes.format(data);
};

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        primes.Last.get(primes.renderLastDecomposition);

        document.querySelector('#primes').addEventListener('submit', function (event) {
            event.preventDefault();
            var form = primes.Form.parse(this);
            form.submit(primes.renderDecompositions);
            form.clear();
        });
    });
}());