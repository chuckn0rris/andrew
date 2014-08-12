Ext.define('App.view.EmployeesGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'employeesgrid',

    // Disabled by default for no chance to add employee before company was selected
    disabled: true,

    requires: [
        'Ext.grid.column.Action',
        'Ext.grid.plugin.RowEditing',
        'App.view.ShowEmployee'
    ],

    initComponent: function() {
        this.store = Ext.create('App.store.Employees');
        this.tbar = [{
            text: 'Add new employee',
            action: 'addEmployee',
            icon: 'resources/images/add.png'
        }];

        this.columns = [{
            name: 'id',
            dataIndex: 'id',
            text: 'ID',
            width: 40
        }, {
            name: 'name',
            dataIndex: 'name',
            text: 'Name',
            flex: 1,
            editor: {
                allowBlank: false
            }
        }, {
            name: 'address',
            dataIndex: 'address',
            text: 'Address',
            flex: 2
        }, {
            xtype: 'actioncolumn',
            width: 50,
            items: [{
                icon: 'resources/images/delete.png',
                tooltip: 'Delete',
                scope: this,
                handler: function(grid, rowIndex, colIndex) {
                    // get employee from the store
                    var employee = grid.getStore().getAt(rowIndex);
                    Ext.Msg.confirm('Delete ' + employee.get('name'), 'Are you sure you want to delete this employee?', function(btn) {
                        if (btn == 'no')
                            return;

                        this.fireEvent('removeemployee', employee);
                    }, this);
                }
            }, {
                icon: 'resources/images/cog_edit.png',
                tooltip: 'Edit',
                scope: this,
                handler: function(grid, rowIndex, colIndex) {
                    // get employee from the store
                    var employee = grid.getStore().getAt(rowIndex);

                    this.fireEvent('editemployee', employee);
                }
            }]
        }];

        this.callParent();
    },

    loadEmployees: function(company) {
        this.setDisabled(false);

        this.companyId = company.get('id');

        console.log('Load employees of company '+ company.get('name'));

        this.store.proxy.url = Ext.String.format('/spring/rest/employees/{0}', this.companyId);
        this.store.load();
    }
});