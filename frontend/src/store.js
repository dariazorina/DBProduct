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
        account: null,
        movement: null
    },

    mutations: {
        login_success(state, payload) {
            state.loginSuccess = true;
            state.userName = payload.userName;
            state.userPass = payload.userPass;
            state.account = payload.account;
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

        movement_selection(state, payload) {
            state.movement = payload.movement;
        }
    },
    actions: {
        login({commit}, {user, password}) {
            return new Promise((resolve, reject) => {
                console.log("Accessing backend with user: " + user + " " + commit);

                const params = new URLSearchParams();
                params.append('username', user);
                params.append('password', password);

                api.getSecured(params)
                    .then(response => {
                        //console.log("Status code " + response.data);
                        if (response.status == 200) {
                            console.log("Login successful");
                            // place the loginSuccess state into our vuex store

                            api.getAccount().then(
                                response => {
                                    console.log("Account retrieved :" + response.data.login + response.data.createdBy);
                                    commit('login_success', {
                                        userName: user,
                                        userPass: password,
                                        account: response.data
                                    });

                                    // if (response.data.movementList.length > 1)
                                    //     alert("бздынь!");
                                }
                            ).catch(
                                error => {
                                    console.log("Error: " + error);
                                }
                            );
                        }

                        commit('login_success', {
                            userName: user,
                            userPass: password,
                            account: {
                                authorities: [],
                            }
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
        },

        movement_selection({commit}, {movement}) {
           // console.log("IN ACTION movement", movement);
            commit('movement_selection', {movement});
        },

        // clear_storage({commit}, {}) {
        //     commit('clear_storage', {});
        // }
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