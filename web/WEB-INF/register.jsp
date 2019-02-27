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
                    <v-flex xs12 sm4 offset-sm4>
                        <v-toolbar color="#1B222B" dark>
                            <v-toolbar-title>Register New Account</v-toolbar-title>
                        </v-toolbar>
                        <v-card class="elevation-10">
                            <v-container>
                                <v-form ref="form" v-model="valid" id="create-account" method="post" action="accountmanagement" lazy-validation>
                                    <v-text-field
                                        name="firstname"
                                        :counter="20"
                                        :rules="nameRules"
                                        label="First Name"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        name="lastname"
                                        :counter="20"
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
                                        v-bind="password.primary"
                                        v-validate="password.primary"
                                        :append-icon="show1 ? 'visibility_off' : 'visibility'"
                                        :rules="[rules.required, rules.min]"
                                        :type="show1 ? 'text' : 'password'"
                                        label="Password"
                                        hint="At least 8 characters"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        v-model="confirmPass"
                                        v-bind="password.confirm"
                                        v-validate="password.confirm"
                                        :append-icon="show1 ? 'visibility_off' : 'visibility'"
                                        :rules="[rules.required, rules.min, rules.passMatch]"
                                        :type="show1 ? 'text' : 'password'"
                                        label="Confirm Password"
                                        hint="At least 8 characters"
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
                            v => !!v || 'required',
                            v => (v && v.length <= 30) || 'Name must be less than 30 characters'
                        ],
                        email: '',
                        emailRules: [
                            v => !!v || 'Email is required',
                            v => /.+@.+/.test(v) || 'This E-mail is invalid'
                        ],
                        password: '',
                        confirmPass: '',
                        rules: {
                            required: value => !!value || 'Required.',
                            passMatch: () => ('Passwords do not match')
                        },
                        checkbox: false
                    }),

                methods: {
                    validate() {
                        if (this.$refs.form.validate()) {
                            this.snackbar = true
                        }
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
