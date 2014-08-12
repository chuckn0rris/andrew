Ext.define('App.store.Employees', {
    extend: 'Ext.data.Store',
    requires: ['App.model.Employee'],
    model: 'App.model.Employee',
    proxy: {
        type: 'ajax',
        url: '/employees',
        reader: {
            type: 'json',
            rootProperty: ''
        }
    }
});