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
            {title: 'Homepage', icon: 'home', link: 'home'},
            {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
            {title: 'Order Queue', icon: 'queue', link: 'queue'},
            {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
            {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
            {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
            {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
        ],
        userItems: 
        [ 
            {title: 'Home', icon: 'home', link: 'home'},
            {title: 'Dashboard', icon: 'dashboard', link: 'userhome'},
            {title: 'Notifications', icon: 'mail', link: 'notifications'},
            {title: 'Previous Orders', icon: 'replay', link: 'previousorders'},
            {title: 'Submit Order', icon: '', link: ''},
            {title: 'Orders', icon: '', link: 'orders'}
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
            {src: 'res/img/carousel/carouselImage1.jpg', filter: 'to top right, rgba(100,115,201,.33), rgba(25,32,72,.7)'},
            {src: 'res/img/carousel/carouselImage2.jpg', filter: 'to top right, rgba(100,115,201,.33), rgba(25,32,72,.7)'},
            {src: 'res/img/carousel/carouselImage3.jpg', filter: 'to top right, rgba(100,115,201,.33), rgba(25,32,72,.7)'}
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