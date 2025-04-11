package DJ.MyDigital.pdF;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import  com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import DJ.MyDigital.Model.Product;

@Service
public class PDFGeneratorService {

    @SuppressWarnings({"CallToPrintStackTrace", "UseSpecificCatch"})
    public void generateHistoryPdf(OutputStream outputStream, List<Product> products) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Product History Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Table
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);

            addTableHeader(table);
            addRows(table, products);

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Product Name", "Charges", "Final Price", "Weight", "Quantity", "Bid Status", "Total Pay", "Date")
              .forEach(columnTitle -> {
                  PdfPCell header = new PdfPCell();
                  header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                  header.setPhrase(new Phrase(columnTitle));
                  table.addCell(header);
              });
    }

    private void addRows(PdfPTable table, List<Product> products) {
        for (Product product : products) {
            table.addCell(product.getProductname());
            table.addCell(String.valueOf(product.getCharges()));
            table.addCell(String.valueOf(product.getFinalPrice()));
            table.addCell(String.valueOf(product.getWeight()));
            table.addCell(String.valueOf(product.getQuantity()));
            table.addCell(product.getBidstatus());
            table.addCell(String.valueOf(product.getTotalpay()));
            table.addCell(product.getDate());
        }
    }
}
