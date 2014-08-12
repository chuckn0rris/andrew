Ext.define('App.controller.Main', {
    extend: 'Ext.app.Controller',

    refs: [{
        ref: 'companiesGrid',
        selector: 'companiesgrid'
    }, {
        ref: 'employeesGrid',
        selector: 'employeesgrid'
    }],

    init: function() {
        this.control({
            'companiesgrid': {
                selectionchange: this.loadEmployees
            },
            'employeesgrid': {
                editemployee: this.showEmployee,
                removeemployee: this.removeEmployee
            },
            'employeesgrid button[action=addEmployee]': {
                click: this.addEmployee
            },
            'showemployee': {
                createemployee: this.createEmployee,
                saveemployee: this.saveEmployee
            }
        });
    },

    loadEmployees: function(model, records) {
        // get first record
        var company = records[0];
        if (company) {
            this.getEmployeesGrid().loadEmployees(company);
        }
    },

    createEmployee: function(employee) {
        Ext.Ajax.request({
            url: '/spring/rest/employee/create',
            method: 'POST',
            scope: this,
            params: {
                companyId: this.getEmployeesGrid().companyId,
                name: employee.get('name'),
                address: employee.get('address')
            },
            success: function() {
                this.getEmployeesGrid().getStore().load();
            },
            failure: function() {
                Ext.Msg.alert('Creation Error', 'The employee wasn\'t created');
            }
        });
    },

    saveEmployee: function(employee) {
        Ext.Ajax.request({
            url: '/spring/rest/employee/update',
            method: 'POST',
            params: {
                id: employee.get('id'),
                name: employee.get('name'),
                address: employee.get('address')
            },
            scope: this,
            success: function() {
                this.getEmployeesGrid().getStore().load();
            },
            failure: function() {
                Ext.Msg.alert('Saving Error', 'The employee wasn\'t saved');
            }
        });
    },

    showEmployee: function(employee) {
        this.employeeWindow = Ext.create('widget.showemployee', {
            employee: employee
        });

        this.employeeWindow.showBy(this.getEmployeesGrid(), 'tl-tl');
    },

    addEmployee: function() {
        var employee = Ext.create('App.model.Employee', {
            name: 'New Employee',
            address: ''
        });

        this.showEmployee(employee);
    },

    removeEmployee: function(employee) {
        Ext.Ajax.request({
            url: Ext.String.format('/spring/rest/employee/delete/{0}', employee.get('id')),
            method: 'PUT',
            scope: this,
            success: function() {
                this.getEmployeesGrid().getStore().load();
            },
            failure: function() {
                Ext.Msg.alert('Deletion Error', 'The employee wasn\'t deleted');
            }
        });
    }
});