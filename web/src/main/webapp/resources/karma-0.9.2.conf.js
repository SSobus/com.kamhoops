frameworks = ['jasmine'];

files = [];

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

autoWatch = true;

browsers = ['Chrome'];

reporters = ['dots'];

plugins = [
    'karma-jasmine',
    'karma-chrome-launcher'
];


