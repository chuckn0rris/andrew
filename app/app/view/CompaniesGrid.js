Ext.define('App.view.CompaniesGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'companiesgrid',

    initComponent: function() {
        this.store = Ext.create('App.store.Companies');

        this.columns = [{
            name: 'name',
            dataIndex: 'name',
            text: 'Name',
            flex: 1
        }, {
            name: 'address',
            dataIndex: 'address',
            text: 'Address',
            flex: 2
        }];

        this.on('afterrender', this.loadCompanies, this);
        this.callParent();
    },

    loadCompanies: function() {
        this.store.load();
    }
});