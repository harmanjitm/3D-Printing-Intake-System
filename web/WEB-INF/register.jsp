<%-- 
    Document   : registration
    Created on : Feb 4, 2019, 6:51:46 PM
    Author     : 687159
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>

<!DOCTYPE html>
<html>
    <head>
        <!--        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <ARIS3D:Imports/>
        <!--        <link href="res/css/login.css" rel="stylesheet" type="text/css"/>-->
        <title>ARIS 3D Printing Service - Registration</title>

        <style>
            body{
                background-color: #EEEAED;
                font-family: roboto;
            }
        </style>

    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="false" pageName="Register"></ARIS3D:Header>
                <v-content>
                    <br><br>
                    <v-flex xs12 sm8 offset-sm2>
                        <v-toolbar color="#1B222B" dark>
                            <v-toolbar-title>Register New Account</v-toolbar-title>
                        </v-toolbar>
                        <v-card class="elevation-10">
                            <v-container>
                                <v-form ref="form" v-model="valid" id="create-account" method="post" action="accountmanagement" lazy-validation>
                                    <v-text-field
                                        name="firstname"
                                        :counter="10"
                                        :rules="nameRules"
                                        label="First Name"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        name="lastname"
                                        :counter="10"
                                        :rules="nameRules"
                                        label="Last Name"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        name="email"
                                        :rules="emailRules"
                                        label="Email"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        v-model="password"
                                        label="Password"
                                        hint="At least 8 characters"
                                        :append-icon="show3 ? 'visibility_off' : 'visibility'"
                                        :rules="[rules.min]"
                                        :type="show3 ? 'text' : 'password'"
                                        @click:append="show3 = !show3"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        v-model="confirmPass"
                                        :append-icon="show4 ? 'visibility_off' : 'visibility'"
                                        :error-messages='passMatch()'
                                        :type="show4 ? 'text' : 'password'"
                                        label="Confirm Password"
                                        hint="At least 8 characters"
                                        @click:append="show4 = !show4"
                                        required
                                        ></v-text-field>

                                    <v-checkbox
                                        v-model="checkbox"
                                        :rules="[v => !!v || 'You must agree to continue']"
                                        label="Agree to terms of service"
                                        required
                                        ></v-checkbox>


                                    <v-layout row justify-space-between>
                                        <v-flex xs6>
                                            <input type="hidden" name="action" value="add">
                                            <v-btn :disabled="!valid" color="#8B2635" dark @click="submit">Register</v-btn>
                                        </v-flex>
                                    </v-layout>
                                </v-form>
                            </v-container>
                        </v-card>
                    </v-flex>
                </v-content>
            </v-app>
        </div>


        <script>
//            Vue.use(window['vue-validator'])

            new Vue({
                el: '#app',
                data: () => ({
                        drawer: false,
                        valid: true,
                        fname: '',
                        lname: '',
                        userItems: [
                            {title: 'Home', icon: 'home', link: 'userhome'},
                            {title: 'Dashboard', icon: 'dashboard', link: 'userdashboard'}
                        ],
                        nameRules: [
                            v => !!v || 'Name is required',
                            v => (v && v.length <= 30) || 'Name must be less than 30 characters'
                        ],
                        email: '',
                        emailRules: [
                            v => !!v || 'Email is required',
                            v => /.+@.+/.test(v) || 'Email must be valid'
                        ],
                        password: '',
                        confirmPass: '',  
                        rules: {
                            required: value => !!value || 'Required.',
                            min: v => v.length >= 8 || 'Min 8 characters', 
                        },
                        checkbox: false
                    }),
                computed: {
                    matchPassword: function () {
                        return this.password === this.confirmPass
                    }
                },
                validator: {
                    validates: {
                        match: function (v, result) {
                            return result
                        }
                    }
                },

                methods: {
                    validate() {
                        if (this.$refs.form.validate()) {
                            this.snackbar = true
                        }
                    },
                    passMatch() {
                       return (this.password === this.confirmPass) ? '' : 'The passwords you entered do not match' 
                    },
                    reset() {
                        this.$refs.form.reset()
                    },
                    resetValidation() {
                        this.$refs.form.resetValidation()
                    },
                    submit()
                    {
                        document.getElementById('create-account').submit();
                    }
                }
            });

        </script>
    </body>
</html>
