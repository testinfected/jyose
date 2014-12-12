module.exports = function(config) {

    config.set({
        basePath: '../../..',
        frameworks: ['mocha', 'chai', 'effroi'],

        files: [
            'src/main/webapp/assets/js/**/*.js',
            'src/test/js/**/*.js'
        ],

        reporters: ['progress'],

        port: 9876,
        colors: true,
        autoWatch: true,
        singleRun: false,
        browsers: ['Firefox'],
        logLevel: config.LOG_ERROR
    });
};