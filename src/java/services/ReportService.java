package services;

import domain.Account;
import java.util.ArrayList;

import domain.Report;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.fontbox.FontBoxFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import persistence.ReportBroker;

/**
 * The Class ReportService provides methods to access and modify archive
 * objects.
 */
public class ReportService {

    private ReportBroker rb;

    public ReportService() {
        rb = new ReportBroker();
    }

    public ArrayList<Report> getAllReports() throws SQLException {
        return rb.getAllReports();
    }

    public void createReport(Account acc) throws IOException, SQLException {
        ReportBroker rb = new ReportBroker();
        int printers;
        int totalPending;
        int totalComplete;
        int totalCancelled;
        int totalApproved;
        int totalAdmins;
        int totalUsers;

        PDDocument report = new PDDocument();
        PDPage page = new PDPage();

        //Format a new date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = format.format(new Date());
        Date startDate = new Date();

        //Make the directory structure if it's not already there.
        String path = System.getProperty("user.dir").concat("/Reports/") + acc.getAccountID() + date + ".pdf";
        path = path.replace("\\", "/");
        File folder = new File(System.getProperty("user.dir").concat("/Reports"));
        folder.mkdirs();

        //Start writing to the PDF
        PDPageContentStream cs = new PDPageContentStream(report, page);

        cs.beginText();//Begin writing
        cs.setLeading(14.5f);
        cs.newLineAtOffset(50, 725);

        cs.setFont(PDType1Font.HELVETICA_BOLD, 25);
        cs.showText("ARIS3D System Report");

        cs.newLine();

        cs.setFont(PDType1Font.HELVETICA, 12);
        cs.showText("3D Printing Intake System");

        cs.newLine();
        cs.newLine();

        cs.showText(acc.getFirstname() + " " + acc.getLastname());
        cs.newLine();
        cs.showText(new Date().toString());
        cs.newLine();
        cs.newLine();
        cs.newLine();
        cs.newLine();
        cs.newLine();

        cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
        cs.showText("Total Accounts:                                                                                                              " + rb.getTotalAccounts());
        cs.newLine();
        cs.newLine();
        cs.setFont(PDType1Font.HELVETICA, 12);
        cs.showText("                    User:     " + rb.getTotalUsers());
        cs.newLine();
        cs.newLine();
        cs.showText("          Technician:     " + rb.getTotalAdmins());
        cs.newLine();
        cs.newLine();
        cs.newLine();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
        cs.showText("Total Materials:                                                                                                              " + rb.getTotalMaterials());
        cs.newLine();
        cs.newLine();
        cs.newLine();
        cs.showText("Total Printers:                                                                                                                " + rb.getTotalPrinters());
        cs.newLine();
        cs.newLine();
        cs.newLine();
        cs.showText("Total Colours:                                                                                                                " + rb.getTotalMaterialColours());
        cs.newLine();
        cs.newLine();
        cs.setFont(PDType1Font.HELVETICA, 12);
        cs.showText("              In Stock:     " + rb.getInStockMaterials());
        cs.newLine();
        cs.newLine();
        cs.showText("       Out of Stock:     " + rb.getOutOfStockMaterials());
        cs.newLine();
        cs.newLine();
        cs.newLine();
        cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
        cs.showText("Total Orders:                                                                                                                 " + rb.getTotalOrders());
        cs.newLine();
        cs.newLine();
        cs.setFont(PDType1Font.HELVETICA, 12);
        cs.showText("         Completed:     " + rb.getTotalCompleteOrders());
        cs.newLine();
        cs.newLine();
        cs.showText("           Approved:     " + rb.getTotalApprovedOrders());
        cs.newLine();
        cs.newLine();
        cs.showText("             Pending:     " + rb.getTotalPendingOrders());
        cs.newLine();
        cs.newLine();
        cs.showText("          Cancelled:     " + rb.getTotalCancelledOrders());
        cs.newLine();
        cs.newLine();
        cs.newLine();
        
        cs.endText();//End writing

        cs.close();

        report.addPage(page);

        //Save the report in the appropriate directory
        report.save(path);
        report.close();
        
        Report r = new Report(0, acc.getAccountID()+date, acc, startDate, "Complete Report", new Date(), "Complete", path);
        rb.addReport(r);
    }
}
