//https://wildermuth.com/2019/03/27/Creating-a-Vuex-Plugin

export default store => {
    store.subscribe((mutation, state) => {
        if (mutation.type === "login_success") {
           // alert("Login succs!");
            console.log(store, mutation, state);
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('userName', mutation.payload.userName);

            const moment = require('moment');
            let now = moment();

            //   console.log(`Now: ${now.format('ll')}`);
           localStorage.setItem('endDate', now.format('YYYY-MM-DD'));
           now.subtract('1', 'months');
           localStorage.setItem('startDate', now.format('YYYY-MM-DD'));
        }

        if (mutation.type === "logout_success") {
          //  alert("Logout succs!");
            console.log(store, mutation, state);
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('userName');
            localStorage.removeItem('startDate');
            localStorage.removeItem('endDate');
        }
    });
};