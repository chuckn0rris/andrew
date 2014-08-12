Ext.define('App.store.Companies', {
    extend: 'Ext.data.Store',
    requires: ['App.model.Company'],
    model: 'App.model.Company',

    proxy: {
        type: 'ajax',
        url: '/spring/rest/companies',
        reader: {
            type: 'json',
            rootProperty: ''
        }
    }
});