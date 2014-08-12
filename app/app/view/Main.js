Ext.define('App.view.Main', {
    extend: 'Ext.container.Container',
    requires:[
        'Ext.tab.Panel',
        'Ext.layout.container.Border'
    ],

    xtype: 'app-main',

    layout: {
        type: 'border'
    },
    border: false,

    items: [{
        region: 'center',
        xtype: 'companiesgrid',
        title: 'Companies list',
        flex: 2
    }, {
        region: 'east',
        xtype: 'employeesgrid',
        title: 'Employees',
        collapsible: true,
        split: true,
        flex: 1
    }]
});