/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


new Vue ({
    el: '#header',
    data: {
        drawer: false,
        userItems:
        [ 
            {title: 'Home', icon: 'home', link: 'home'},
            {title: 'Dashboard', icon: 'dashboard', link: 'userhome'}
        ],
        adminItems: 
        [ 
            {title: 'Home', icon: 'home', link: 'home'},
            {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
            {title: 'Order Queue', icon: 'queue', link: 'queue'},
            {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
            {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
            {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
            {title: 'Reports', icon: 'poll', link: 'reportmanagement'}
        ],
    }
});