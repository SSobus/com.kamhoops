// list of files / patterns to load in the browser
files = [
    JASMINE,
    JASMINE_ADAPTER
];

// List of required libraries. Order is important.
libraries = [
    'scripts/lib/angular-1.0.7.js',
    'scripts/lib/angular-1.0.7-mocks.js',
    'scripts/lib/angular-ui-0.2.js',
    'scripts/lib/ui-utils.js',
    'scripts/lib/ui-bootstrap-0.3.0.js',
    'scripts/lib/underscore.js',
    'scripts/lib/moment.min.js',
    'test/application-test-dsl.js',
    'test/data/**/*.json'
];

modules = [
    'scripts/modules/config.js',
    'scripts/modules/filters.js',
    'scripts/modules/services.js',
    'scripts/controllers/*.js',
    'scripts/application.js'
];

mocks = [
    'test/application-mocks.js'
];

specs = [
    'test/**/*Specs.js'
];

files = files.concat(libraries, modules, mocks, specs);

// web server port
port = 8090;

// cli runner port
runnerPort = 9100;

// enable / disable colors in the output (reporters and logs)
colors = true;

// level of logging
// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
logLevel = LOG_INFO;

loggers = [
    {type: 'console'}
];

reporters = [
    'dots'
];

// enable / disable watching file and executing tests whenever any file changes
autoWatch = true;

// Start these browsers, currently available:
// - Chrome
// - ChromeCanary
// - Firefox
// - Opera
// - Safari (only Mac)
// - PhantomJS
// - IE (only Windows)
browsers = ['Chrome'];

// If browser does not capture in given timeout [ms], kill it
captureTimeout = 5000;

// Continuous Integration mode
// if true, it capture browsers, run tests and exit
singleRun = false;