new Vue(
{ 
    el: '#app', 
    data: 
    {
        dialog: false,
        account: '',
        logout: '',
        password: '',
        email: '',
        verifyEmail: '',
        drawer: false,
        valid: false,
        carouselDelimiter: true,
        adminItems: 
        [ 
            {title: 'Home', icon: 'home', link: 'home'},
            {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
            {title: 'Order Queue', icon: 'queue', link: 'queue'},
            {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
            {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
            {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
            {title: 'Reports', icon: 'poll', link: 'reportmanagement'},
            {title: 'Backups', icon: 'restore', link: 'backup'},
            {title: 'New Order', icon: 'folder_open', link: 'order'}
        ],
        userItems: 
        [ 
            {title: 'Home', icon: 'home', link: 'home'},
            {title: 'Submit Order', icon: 'add_box', link: 'order'}
        ],
        accountmanagementheaders:
        [
            {text: 'Email Address', value: 'email'},
            {text: 'First Name', value: 'firstname'},
            {text: 'Last Name', value: 'lastname'},
            {text: 'Status', value: 'status'},
            {text: 'Actions', value: 'actions'}
        ],
        carousel:
        [
            {src: 'res/img/carousel/carouselImage1.jpg', filter: 'to top right, rgba(0,0,0,.33), rgba(0,0,0,.33)'},
            {src: 'res/img/carousel/carouselImage2.jpg', filter: 'to top right, rgba(0,0,0,.33), rgba(0,0,0,.33)'},
            {src: 'res/img/carousel/carouselImage3.jpg', filter: 'to top right, rgba(0,0,0,.33), rgba(0,0,0,.33)'}
        ],
        emailRules: 
        [
            v => !!v || 'E-mail is required',
            v => /.+@.+/.test(v) || 'E-mail must be valid'
        ]
                
    },
    methods:
    {
        login()
        {
            document.getElementById('login').submit();
        },
        resend()
        {
            document.getElementById('resend').submit();
        }
    }
});