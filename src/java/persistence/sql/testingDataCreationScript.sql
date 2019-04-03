/* ***************************************************************
** Author:  Emily Pegg	                                       	**
** Creation Date:  February, 2019                               **
** Script Name: Test Data Creation				**
** Description: Creates test data for the ARIS 3D Printing DB   **
******************************************************************/
/* ACCOUNTS */
call aris.createAccount('emily.pegg@edu.sait.ca', 'password', 'Emily', 'Pegg', 'user');
call aris.createAccount('benjamin.wozak@edu.sait.ca', 'password', 'Benjamin', 'Wozak', 'admin');
call aris.createAccount('gregory.turnbull@edu.sait.ca', 'password', 'Gregory', 'Turnbull', 'admin');
call aris.createAccount('harmanjit.mohaar@edu.sait.ca', 'password', 'Hamanjit', 'Mohaar', 'admin');
call aris.createAccount('haseeb.sheiktsuyi@edu.sait.ca', 'password', 'Haseeb', 'Sheikh', 'admin');
call aris.createAccount('timothy.huynh@edu.sait.ca', 'password', 'Timothy', 'Huynh', 'admin');

/* PRINTERS */
call aris.createPrinter('406x355x406mm', 'active', 'Fortus 400mc', 'This is an FDM type printer. It is the fastest printer with the largest build volume. It is the best printer for ABS plastic because it has a heated chamber that prevents layers from splitting as they cool.', 2.5);
call aris.createPrinter('215x215x300mm', 'active', 'Ultimaker 3 Extended', 'This is an FDM type printer. It is smaller and slower than the Fortus but is also less expensive. It is still great for ABS plastic, and more materials are available.', 1);
call aris.createPrinter('215x215x300mm', 'active', 'Form 2+', 'This is an SLA type printer. It is best used for parts that have small features that need high precision. it will be expensive to produce a large part on this printer, so it is best saved for smaller parts.', 1);
/* MATERIALS */
call aris.createMaterial('ABS-M30', 'This is a basic ABS Plastic', 06.00,1);
call aris.createMaterial('SR30', 'This is support material. It is water soluble for removal.', 08.50,1);
call aris.createMaterial('ABS', 'This is a basic ABS Plastic. ABS is rigid, and stronger than PLA.', 00.09,2);
call aris.createMaterial('PLA', 'A basic PLA plastic; rigid and cheap', 00.09,2);
call aris.createMaterial('Nylon', 'Tougher and more flexible than ABS or PLA.', 00.15,2);
call aris.createMaterial('TPU95A', 'Very flexible and extremely tough; thin parts are stretchable too.', 00.15,2);
call aris.createMaterial('Polycarbonate', 'Rigid and resistant to high temperatures (up to about 110C).', 00.15,2);
call aris.createMaterial('PVA', 'This is support material. It is soluble in water for removal. Most parts will not need it.', 00.21,2);
call aris.createMaterial('Clear', 'A rigid resin, and the cheapest. It is fairly brittle if bent or cyclically loaded.', 29.00,3);
call aris.createMaterial('Flexible', 'Flexible like a gasket type material.', 39.00,3);
call aris.createMaterial('Tough', 'More tough than the clear material.', 35.00,3);

/* COLOURS */
call aris.createMaterialColour(50, 'gray');
call aris.createMaterialColour(50, 'white');
call aris.createMaterialColour(50, 'clear');
call aris.createMaterialColour(51, 'white');
call aris.createMaterialColour(52, 'gray');
call aris.createMaterialColour(52, 'white');
call aris.createMaterialColour(52, 'clear');
call aris.createMaterialColour(53, 'gray');
call aris.createMaterialColour(53, 'white');
call aris.createMaterialColour(53, 'clear');
call aris.createMaterialColour(54, 'gray');
call aris.createMaterialColour(54, 'white');
call aris.createMaterialColour(54, 'clear');
call aris.createMaterialColour(55, 'gray');
call aris.createMaterialColour(55, 'white');
call aris.createMaterialColour(55, 'clear');
call aris.createMaterialColour(56, 'transparent');
call aris.createMaterialColour(57, 'white');
call aris.createMaterialColour(58, 'transparent');
call aris.createMaterialColour(59, 'black');
call aris.createMaterialColour(60, 'transparent blue');

/* PRINTER MATERIALS */
call aris.createPrinterMaterial(1, 50);
call aris.createPrinterMaterial(1, 51);
call aris.createPrinterMaterial(2, 52);
call aris.createPrinterMaterial(2, 53);
call aris.createPrinterMaterial(2, 54);
call aris.createPrinterMaterial(2, 55);
call aris.createPrinterMaterial(2, 56);
call aris.createPrinterMaterial(2, 57);
call aris.createPrinterMaterial(3, 58);
call aris.createPrinterMaterial(3, 59);
call aris.createPrinterMaterial(3, 60);

/*NOTIFICATION DEFAULT MESSAGE*/
call aris.createNotificationDefaultMessage('issue','There was an issue when processing your print. Please check your account for more information.');
call aris.createNotificationDefaultMessage('ready','Your print has been printed sucessfully! Please pick it up from the lab!');
call aris.createNotificationDefaultMessage('printing','Your print is currently being printed.');
call aris.createNotificationDefaultMessage('queued','Your print has been approved and added to the queue of prints.');

/*FILES*/
call aris.createFile(100000, 'EmilyPegg.stl', 'test/', 90, 'STL');
call aris.createFile(100001, 'BenWozak.stl', 'test/', 100, 'STL');
call aris.createFile(100002, 'GregTurnbull.stl', 'test/', 80, 'STL');
call aris.createFile(100003, 'HarmanMohaar.stl', 'test/', 60, 'STL');
call aris.createFile(100004, 'HaseebSheikTsuyi.stl', 'test/', 70, 'STL');
call aris.createFile(100000, 'EmilyPegg.stl', 'test/', 90, 'STL');
call aris.createFile(100001, 'BenWozak.stl', 'test/', 100, 'STL');
call aris.createFile(100002, 'GregTurnbull.stl', 'test/', 80, 'STL');
call aris.createFile(100003, 'HarmanMohaar.stl', 'test/', 60, 'STL');
call aris.createFile(100004, 'HaseebSheikTsuyi.stl', 'test/', 70, 'STL');

/*ORDERS*/
call aris.createPrintOrder(123.00, 1, 50, 100000, 400000, 'gray');
call aris.createPrintOrder(123.00, 2, 57, 100000, 400001, 'gray');
call aris.createPrintOrder(123.00, 3, 58, 100000, 400002, 'gray');
call aris.createPrintOrder(123.00, 1, 51, 100000, 400003, 'gray');
call aris.createPrintOrder(123.00, 2, 55, 100000, 400004, 'gray');
call aris.createPrintOrder(123.00, 1, 50, 100000, 400005, 'gray');
call aris.createPrintOrder(123.00, 2, 57, 100000, 400006, 'gray');
call aris.createPrintOrder(123.00, 3, 58, 100000, 400007, 'gray');
call aris.createPrintOrder(123.00, 1, 51, 100000, 400008, 'gray');
call aris.createPrintOrder(123.00, 2, 55, 100000, 400009, 'gray');

/*ORDER QUEUE*/
call aris.createQueue(300000);
call aris.createQueue(300001);
call aris.createQueue(300002);
call aris.createQueue(300003);
call aris.createQueue(300004);
call aris.createQueue(300005);
call aris.createQueue(300006);
call aris.createQueue(300007);
call aris.createQueue(300008);
call aris.createQueue(300009);

/*NOTIFICATION*/
call aris.createNotification(300006, 'printing');
call aris.createNotification(300006, 'queued');
call aris.createNotification(300006, 'ready');
call aris.createNotification(300004, 'issue');