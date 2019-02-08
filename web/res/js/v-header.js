new Vue({ 
    el: '#header', 
    data: {
        account: '',
        logout: '',
        drawer: false,
        items: [ {title: 'Dashboard', icon: 'dashboard'}, { title: 'Account Management', icon: 'people'}]
    }});