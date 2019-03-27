package services;

import java.util.ArrayList;

import domain.Report;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.fontbox.FontBoxFont;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
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

    public void createReport() throws IOException {
        PDDocument report = new PDDocument();
        PDPage page = new PDPage();

        report.addPage(page);

        PDPageContentStream cs = new PDPageContentStream(report, page);
        
        cs.beginText();//Begin writing
        
        cs.newLineAtOffset(25, 700);
        cs.setFont(PDType1Font.COURIER_BOLD, 20);
        cs.showText("ARIS3D Report");
        cs.endText();//End writing
        
        report.save("C:/PDF/test.pdf");
        report.close();
    }
}
