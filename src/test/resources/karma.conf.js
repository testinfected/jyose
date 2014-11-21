module.exports = function(config) {

    var ASSET_PATH = 'src/main/webapp/assets';
    var TEST_PATH = 'test/js';

    config.set({
        basePath: '../../',
        frameworks: ['jasmine'],

        files: [
            ASSET_PATH + '/javascripts/**/*.js',
            TEST_PATH + '/**/*-spec.js'
        ],

        exclude: [],
        preprocessors: {},
        reporters: ['progress'],
        port: 9876,
        colors: true,
        logLevel: config.LOG_ERROR,
        autoWatch: true,
        browsers: ['PhantomJS'],
        singleRun: false
    });
};