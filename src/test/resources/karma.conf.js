module.exports = function(config) {

    config.set({
        basePath: '../../..',
        frameworks: ['mocha', 'chai', 'effroi'],

        files: [
            'src/main/webapp/assets/js/**/*.js',
            'src/test/js/**/*.js'
        ],

        reporters: ['progress', 'coverage'],

        preprocessors: {
            'src/main/webapp/assets/js/**/*.js': ['coverage']
        },

        // optionally, configure the reporter
        coverageReporter: {
            dir : 'build/reports/istanbul/',
            reporters: [
                { type: 'html', subdir: 'html' },
                { type: 'lcov', subdir: 'lcov' }
            ]
        },

        port: 9876,
        colors: true,
        autoWatch: true,
        singleRun: false,
        browsers: ['Firefox'],
        logLevel: config.LOG_ERROR
    });
};