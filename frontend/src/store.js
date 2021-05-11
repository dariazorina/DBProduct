import Vue from 'vue'
import Vuex from 'vuex'
import api from './components/login-api'
import isLoggedInPlugin from './isloggedinplugin'

Vue.use(Vuex);

export default new Vuex.Store({
    plugins: [isLoggedInPlugin],
    state: {
        loginSuccess: false,
        loginError: false,
        userName: null,
        userPass: null,
        account: null
    },

    mutations: {
        login_success(state, payload) {
            state.loginSuccess = true;
            state.userName = payload.userName;
            state.userPass = payload.userPass;
            state.account  = payload.account;
        },
        login_error(state, payload) {
            state.loginError = true;
            state.userName = payload.userName;
        },
        logout_success(state, payload) {
            state.loginSuccess = false;
            state.userName = null;
            state.userPass = null;
            state.account = {};
        },
    },
    actions: {
        login({commit}, {user, password}) {
            return new Promise((resolve, reject) => {
                console.log("Accessing backend with user: '" + user + " " + commit);

                const params = new URLSearchParams();
                params.append('username', user);
                params.append('password', password);

                api.getSecured(params)
                    .then(response => {
                        console.log("Status code " + response.status);
                        /*                        if (response.status == 200) {
                                                    console.log("Login successful");
                                                    // place the loginSuccess state into our vuex store

                                                    api.getAccount().then(
                                                        response => {
                                                            console.log("Account retrieved :" + response.data);
                                                            commit('login_success', {
                                                                userName: user,
                                                                userPass: password,
                                                                account: response.data
                                                            });
                                                        }
                                                    ).catch(
                                                        error => {
                                                            console.log("Error: " + error);
                                                        }
                                                    );
                                                }*/

                        commit('login_success', {
                            userName: user,
                            userPass: password,
                            account: {}
                        });

                        resolve(response);
                    })
                    .catch(error => {
                        console.log("Error: " + error);
                        // place the loginError state into our vuex store
                        commit('login_error', {
                            userName: user
                        });
                        reject("Invalid credentials!")
                    })
            })
        },

        logout({commit}) {
            return new Promise((resolve, reject) => {
                console.log("Trying to logout");

                api.logout().then(response => {
                        console.log("Logout success");
                        commit('logout_success', {});


                        // api.getAccount().then(
                        //     response => {
                        //         console.log("Account retrieved :" + response.status);
                        //     }).catch(
                        //     error => {
                        //         console.log("Error: " + error);
                        //     });
                        resolve(response);
                    }
                ).catch(
                    error => {
                        console.log("Logout failed");
                        reject("Logout failed");
                    }
                );
            })
        }
    },
    getters: {
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored:
            state => state.loginError,
        getUserName:
            state => state.userName,
        getUserPass:
            state => state.userPass
    }
})