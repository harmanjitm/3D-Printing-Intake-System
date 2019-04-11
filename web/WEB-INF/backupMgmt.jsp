<%-- 
    Document   : reportMgmt
    Created on : Mar 26, 2019, 3:29:26 PM
    Author     : Harmanjit Mohaar (000758243)
--%>

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="ARIS3D" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <ARIS3D:Imports></ARIS3D:Imports>
        <title>ARIS3D - Backup Management</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Backup Management"></ARIS3D:Header>
                <v-content>
                    <v-container>
                        <v-alert <c:if test='${successMessage != null}'>value="true"</c:if> type="success">
                            ${successMessage}
                        </v-alert>
                        <v-alert <c:if test='${errorMessage != null}'>value="true"</c:if> type="error">
                            ${errorMessage}
                        </v-alert>
                        <v-card color="#8B2635" height="5px"></v-card>
                        <v-toolbar class="elevation-1" dark>
                            <v-toolbar-title>Manage Backups</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-btn color="#8B2635" @click="create" dark class="mb-2">Create Backup</v-btn>
                        </v-toolbar>
                        <v-data-table class="elevation-3" :headers="backupHeaders" :items="backups">
                            <template slot="items" v-bind:id="props.item.id" slot-scope="props">
                                <td>{{ props.item.filename }}</td>
                                <td>{{ props.item.status }}</td>
                                <td>{{ props.item.date }}</td>
                                <td><v-icon @click="restore(props.item)">restore</v-icon></td>
                            </template>
                        </v-data-table>
                        <form action="backup" method="post" id="restore"><input type="hidden" name="action" value="restore"><input id="filename" type="hidden" name="filename" value=""></form>
                        <form action="backup" method="post" id="create"><input type="hidden" name="action" value="create"></form>
                    </v-container>
                </v-content>
            </v-app>
        </div>
        <script>
            new Vue ({ 
                el: '#app',
                data: 
                {
                    account: '',
                    logout: '',
                    drawer: false,
                    restoreBackup:
                    {
                        id: '',
                        filename: '',
                        status: '',
                        date: '',
                        path: '',
                    },
                    backups:
                    [
                        <c:forEach items="${backups}" var="backup">
                            {id: '${backup.backupId}', filename: '${backup.title}', status: '${backup.status}', date: '${backup.date}', path: '${backup.path}'},
                        </c:forEach>
                    ],
                    adminItems: 
                    [ 
                        {title: 'Home', icon: 'home', link: 'home'},
                        {title: 'Dashboard', icon: 'dashboard', link: 'dashboard'},
                        {title: 'Order Queue', icon: 'queue', link: 'queue'},
                        {title: 'Account Management', icon: 'people', link: 'accountmanagement'},
                        {title: 'Material Management', icon: 'texture', link: 'materialmanagement'},
                        {title: 'Printer Management', icon: 'print', link: 'printermanagement'},
                        {title: 'Reports', icon: 'poll', link: 'reportmanagement'},
                        {title: 'Backups', icon: 'restore', link: 'backup'},
                        {title: 'New Order', icon: 'folder_open', link: 'order'}
                    ],
                    backupHeaders:
                    [
                        {text: 'File Name', value: 'filename'},
                        {text: 'Backup Status', value: 'status'},
                        {text: 'Backup Date', value: 'date'},
                        {text: 'Actions', value: '', sortable:false}
                    ]
                },
                methods:
                {
                    restore(item)
                    {
                        this.restoreBackup = Object.assign({}, item);
                        document.getElementById('filename').value=this.restoreBackup.filename;
                        document.getElementById('restore').submit();
                    },
                    create()
                    {
                        document.getElementById('create').submit();
                    }
                }
            });
        </script>
    </body>
</html>
