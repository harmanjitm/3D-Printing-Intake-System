1. BEFORE EXECUTING SCRIPTS

	Open mySQL Workbench
	Connect to the instance of mySQL that will host the DB
	Go to Edit --> Preferences
	Click "SQL Editor" tab and uncheck "Safe Updates" check box
	Click ok to save changes
	Query --> Reconnect to Server
*********NOTE THE RECONNECTING IS CRITICAL OR YOUR CHANGES WONT STICK*********
2. RUN aris3DSchema.sql
3. RUN accountManagementProceduresScript.sql
4. RUN printerManagementProceduresScript.sql
5. RUN materialManagementProceduresScript.sql
6. RUN notificationManagementProceduresScript.sql
7. RUN fileManagementProceduresScript.sql
8. RUN printOrderManagementProceduresScript.sql
9. RUN queueManagementProceduresScript.sql
10. RUN testingDataCreationScript.sql
