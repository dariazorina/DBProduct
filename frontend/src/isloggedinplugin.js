//https://wildermuth.com/2019/03/27/Creating-a-Vuex-Plugin

export default store => {
    store.subscribe((mutation, state) => {
        if (mutation.type === "login_success") {
           // alert("Login succs!");
            console.log(store, mutation, state);
            localStorage.setItem('isLoggedIn', 'true');
            localStorage.setItem('userName', mutation.payload.userName);

            if (typeof mutation.payload.account.movementList !== 'undefined' && mutation.payload.account.movementList !== null) {
                if (mutation.payload.account.movementList.length < 2) {
                    localStorage.setItem('movement', mutation.payload.account.movementList[0].id);
                    localStorage.setItem('movementSingle', 'true');
                } else {
                    localStorage.setItem('movementSingle', 'false');
                }
            }

            const moment = require('moment');
            let now = moment();

            //   console.log(`Now: ${now.format('ll')}`);
           localStorage.setItem('endDate', now.format('YYYY-MM-DD'));
           now.subtract('1', 'months');
           localStorage.setItem('startDate', now.format('YYYY-MM-DD'));
        }

        if (mutation.type === "logout_success") {
            // alert("Logout succs!");
            console.log(store, mutation, state);
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('userName');
            localStorage.removeItem('startDate');
            localStorage.removeItem('endDate');
            localStorage.removeItem('movementSingle');
            localStorage.removeItem('movement');
        }

        if (mutation.type === "movement_selection") {
            // console.log("PLUGIN MOVEMENT", state, mutation.payload);
            localStorage.setItem('movement', mutation.payload.movement);
        }

        // if (mutation.type === "clear_storage") {  //todo check is it in use?
        //     localStorage.clear();
        // }
    });
};