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
        <title>ARIS3D - Report Management</title>
    </head>
    <body>
        <div id="app">
            <v-app>
                <ARIS3D:Header isAdmin="true" pageName="Report Management"></ARIS3D:Header>
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
                            <v-toolbar-title>Manage Reports</v-toolbar-title>
                            <v-spacer></v-spacer>
                            <v-btn color="#8B2635" @click="add" dark class="mb-2">Generate Report</v-btn>
                        </v-toolbar>
                        <v-data-table class="elevation-3" :headers="reportHeaders" :items="reports">
                            <template slot="items" v-bind:id="props.item.id" slot-scope="props">
                                <td>{{ props.item.name }}</td>
                                <td>{{ props.item.creator }}</td>
                                <td>{{ props.item.created }}</td>
                                <td>{{ props.item.completed }}</td>
                                <td>{{ props.item.content }}</td>
                                <td>{{ props.item.status }}</td>
                                <td><v-icon @click="download(props.item)">cloud_download</v-icon></td>
                            </template>
                        </v-data-table>
                        <form action="reportmanagement" method="post" id="download"><input type="hidden" name="action" value="download"><input id="path" type="hidden" name="path" value=""></form>
                        <form action="reportmanagement" method="post" id="add"><input type="hidden" name="action" value="add"></form>
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
                    downloadReport:
                    {
                        id: '',
                        name: '',
                        creator: '',
                        created: '',
                        content: '',
                        completed: '',
                        status: '',
                        path: ''
                    },
                    reports:
                    [
                        <c:forEach items="${reports}" var="report">
                            {id: '${report.reportId}', name: '${report.title}', creator: '${report.owner}', created: '${report.dateCreated}',content: '${report.content}', completed: '${report.dateCompleted}', status: '${report.status}',path: '${report.path}',},
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
                    reportHeaders:
                    [
                        {text: 'Report Name', value: 'name'},
                        {text: 'Owner', value: 'creator'},
                        {text: 'Date Created', value: 'created'},
                        {text: 'Date Completed', value: 'completed'},
                        {text: 'Content', value: 'content'},
                        {text: 'Status', value: 'status'},
                        {text: 'Actions', value: '', sortable:false}
                    ]
                },
                methods:
                {
                    download(item)
                    {
                        this.downloadReport = Object.assign({}, item);
                        document.getElementById('path').value=this.downloadReport.path;
                        document.getElementById('download').submit();
                    },
                    add()
                    {
                        document.getElementById('add').submit();
                    }
                }
            });
        </script>
    </body>
</html>
