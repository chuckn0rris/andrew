Ext.define('App.model.Employee', {
    extend: 'Ext.data.Model',

    fields: [{
        name: 'id',
        type: 'int'
    }, {
        name: 'companyId',
        type: 'int'
    }, {
        name: 'name',
        type: 'string'
    }, {
        name: 'address',
        type: 'string'
    }]
});