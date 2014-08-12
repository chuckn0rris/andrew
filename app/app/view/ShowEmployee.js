Ext.define('App.view.ShowEmployee', {
    extend: 'Ext.window.Window',
    xtype: 'showemployee',

    layout: 'fit',
    height: 200,
    width: 300,
    modal: true,
    employee: false,

    initComponent: function() {
        this.bbar = [
            '->',
            {
                text: 'Save',
                width: 60,
                action: 'save',
                scope: this,
                handler: function() {
                    var form = this.down('form');

                    if (!form.isValid())
                        return;

                    var vals = form.getValues(),
                        employee = form.getRecord();

                    if (!employee.get('id')) {
                        employee = Ext.create('App.model.Employee', vals);
                        this.fireEvent('createemployee', employee);
                    } else {
                        employee.set(vals);
                        this.fireEvent('saveemployee', employee);
                    }
                    this.close();
                }
            }, '-', {
                text: 'Cancel',
                width: 60,
                action: 'cancel',
                scope: this,
                handler: this.close
            }
        ];

        this.items = [this.getEmployeeForm()];

        this.on('afterrender', this.loadEmployee, this);
        this.callParent();
    },

    getEmployeeForm: function() {
        return {
            xtype: 'form',
            border: false,
            bodyPadding: 10,
            items: [{
                xtype: 'textfield',
                fieldLabel: 'Name',
                allowBlank: false,
                name: 'name'
            }, {
                xtype: 'textfield',
                allowBlank: false,
                fieldLabel: 'Address',
                name: 'address'
            }]
        };
    },

    loadEmployee: function() {
        if (!this.employee)
            return;

        this.setTitle(this.employee.get('name'));
        this.down('form').loadRecord(this.employee);
    }
});