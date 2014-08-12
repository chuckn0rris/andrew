Ext.define('App.Application', {
    name: 'App',

    extend: 'Ext.app.Application',

    stores: [
        'Employees',
        'Companies'
    ],

    views: [
        'CompaniesGrid',
        'EmployeesGrid'
    ],

    models: [
        'Employee',
        'Company'
    ],

    views: [
        'CompaniesGrid',
        'EmployeesGrid'
    ],

    controllers: [
        'Main'
    ]
});
