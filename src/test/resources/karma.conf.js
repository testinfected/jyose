module.exports = function(config) {

    config.set({
        basePath: '../../..',
        frameworks: ['mocha', 'chai'],

        files: [
            'src/main/webapp/assets/javascripts/**/*.js',
            'src/test/js/**/*.js'
        ],

        reporters: ['progress'],

        port: 9876,
        colors: true,
        autoWatch: true,
        singleRun: false,
        browsers: ['PhantomJS'],
        logLevel: config.LOG_ERROR
    });
};