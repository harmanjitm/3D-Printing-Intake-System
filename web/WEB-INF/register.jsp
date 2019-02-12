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
        <br>
        <br>
        <div id="app">
            <v-app>
                <v-content>
                    <ARIS3D:Header isAdmin="false" pageName="Register"></ARIS3D:Header>
                    <v-flex xs12 sm4 offset-sm4>
                        <v-toolbar color="#1B222B" dark>
                            <v-toolbar-title>Register New Account</v-toolbar-title>
                        </v-toolbar>
                        <v-card class="elevation-10">
                            <v-container>
                                <v-form ref="form" v-model="valid" lazy-validation>
                                    <v-text-field
                                        v-model="fname"
                                        :counter="10"
                                        :rules="nameRules"
                                        label="First Name"
                                        required
                                        ></v-text-field>



                                    <v-text-field
                                        v-model="lname"
                                        :counter="10"
                                        :rules="nameRules"
                                        label="Last Name"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        v-model="email"
                                        :rules="emailRules"
                                        label="E-mail"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        v-model="password"
                                        :append-icon="show1 ? 'visibility_off' : 'visibility'"
                                        :rules="[rules.required, rules.min]"
                                        :type="show1 ? 'text' : 'password'"
                                        label="Password"
                                        hint="At least 8 characters"
                                        required
                                        ></v-text-field>

                                    <v-text-field
                                        v-model="confirmPass"
                                        :append-icon="show1 ? 'visibility_off' : 'visibility'"
                                        :rules="[rules.required, rules.min]"
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
                                            <v-btn :disabled="!valid" color="#8B2635" dark @click="validate">Register</v-btn>
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
                        valid: true,
                        fname: '',
                        lname: '',
                        nameRules: [
                            v => !!v || 'Name is required',
                            v => (v && v.length <= 30) || 'Name must be less than 30 characters'
                        ],
                        email: '',
                        emailRules: [
                            v => !!v || 'E-mail is required',
                            v => /.+@.+/.test(v) || 'E-mail must be valid'
                        ],
                        password: '',
                        confirmPass: '',
                        rules: {
                            required: value => !!value || 'Required.',
                            min: v => v.length >= 8 || 'Min 8 characters',
                            emailMatch: () => ('The email and password you entered don\'t match')
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
                    }
                }
            });

        </script>
    </body>
</html>
