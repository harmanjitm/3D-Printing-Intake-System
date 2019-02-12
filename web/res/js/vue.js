new Vue(
{ 
    el: '#app', 
    data: 
    {
        dialog: false,
        account: '',
        logout: '',
        drawer: false,
        adminItems: 
        [ 
            {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
            {title: 'Order Queue', icon: 'queue', link: 'queue'},
            {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
            {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
            {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
            {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
        ],
        userItems: 
        [ 
            {title: 'Dashboard', icon: 'dashboard', link: 'userdashboard'}
        ],
        accountmanagementheaders:
        [
            {text: 'Email Address', value: 'email'},
            {text: 'First Name', value: 'firstname'},
            {text: 'Last Name', value: 'lastname'},
            {text: 'Status', value: 'status'},
            {text: 'Actions', value: 'actions'}
        ],
        accounts:
        [
            {email: 'harmanjit.mohaar@edu.sait.ca', firstname: 'Harmanjit', lastname: 'Mohaar', status: 'User'},
            {email: 'benjamin.wozak@edu.sait.ca', firstname: 'Ben', lastname: 'Wozak', status: 'User'}
        ]
    }
});