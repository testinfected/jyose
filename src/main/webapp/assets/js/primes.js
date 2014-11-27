var ajax = {
    encode: function (data) {
        var parameters = [];
        for (var key in  data) {
            parameters.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
        }
        return parameters.join('&');
    },

    send: function (settings, done) {
        var request = new XMLHttpRequest();
        request.open(settings.method, settings.url);
        request.setRequestHeader("Content-type", settings.encoding);
        request.onload = function () {
            done(JSON.parse(request.responseText))
        };
        request.send(this.encode(settings.data));
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
        }

        function read(form) {
            var settings = {
                method: form.method,
                url: form.action,
                encoding: form.enctype,
                data: {}
            };
            var number = form.querySelector('#number');
            settings.data[number.name] = encodeURIComponent(number.value);
            return settings;
        }

        return new Form(read(form));
    }
};

primes.renderIn = function (container) {
    function format(data) {
        return data.number + ' = ' + data.decomposition.join(' x ');
    }

    return function (data) {
        container.innerHTML = format(data);
    };
};

(function () {
    document.addEventListener('DOMContentLoaded', function () {
        document.querySelector('#primes').addEventListener('submit', function (event) {
            event.preventDefault();
            var form = primes.Form.parse(this);
            var renderer = primes.renderIn(document.querySelector("#result"));
            form.submit(renderer);
        });
    });
}());