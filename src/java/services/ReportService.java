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

    public void createReport() throws IOException {
        PDDocument report = new PDDocument();
        PDPage page = new PDPage();

        

        PDPageContentStream cs = new PDPageContentStream(report, page);
        
        cs.beginText();//Begin writing
        cs.setLeading(14.5f);
        cs.newLineAtOffset(50, 725);
        cs.setFont(PDType1Font.HELVETICA_BOLD, 25);
        cs.showText("ARIS3D System Report");
        cs.newLine();
        cs.setFont(PDType1Font.HELVETICA, 12);
        cs.showText("3D Printing Intake System");
        cs.endText();//End writing
        cs.close();
        report.addPage(page);
        report.save("C:/PDF/test.pdf");
        report.close();
    }
}
