package DJ.MyDigital.bill;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import DJ.MyDigital.Model.Farmer;
import DJ.MyDigital.Model.MProduct;
import DJ.MyDigital.Model.Merchant;
import DJ.MyDigital.Model.Product;
import DJ.MyDigital.repository.FarmerRepository;
import DJ.MyDigital.repository.MProductRepository;
import DJ.MyDigital.repository.MerchantRepository;
import DJ.MyDigital.repository.ProductRepository;

@RestController
public class BillController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FarmerRepository farmerRepository;


    @Autowired
    private MProductRepository mproductRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @GetMapping("/generate-bill")
public ResponseEntity<Resource> generateBill(@RequestParam Long farmerId) throws IOException {
    double totalPay = 0.0;

    Farmer farmer = farmerRepository.findById(farmerId).orElse(null);
    if (farmer == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    List<Product> products = productRepository.findByFarmer(farmer);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(outputStream);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    PdfFont bold = PdfFontFactory.createFont("Times-Bold");
    DecimalFormat df = new DecimalFormat("#,##0.00");

    document.add(new Paragraph("MyDigital")
            .setFont(bold).setFontSize(18).setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Fruit Commission Agent").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Market Yard, Solapur").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Phone No: 8010094034").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Email: mydigital@gmail.com").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("--------------------------------------------------"));

    document.add(new Paragraph("Farmer ID: " + farmer.getId()));
    document.add(new Paragraph("Farmer: " + farmer.getMName()));
    document.add(new Paragraph("Farmer Phone No: " + farmer.getPhoneNO()));

    document.add(new Paragraph("Date: " + java.time.LocalDate.now()));

    document.add(new Paragraph("--------------------------------------------------"));

    Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2, 2, 2})).useAllAvailableWidth();
    table.addHeaderCell("Qty");
    table.addHeaderCell("Product");
    table.addHeaderCell("Weight");
    table.addHeaderCell("Charges (₹)");
    table.addHeaderCell("Price (₹)");
    table.addHeaderCell("Total (₹)");

    for (Product product : products) {
        double total = product.getWeight() * product.getFinalPrice();
        table.addCell("1");
        table.addCell(product.getProductname());
        table.addCell(df.format(product.getWeight()));
        table.addCell(df.format(product.getCharges()));
        table.addCell(df.format(product.getFinalPrice()));
        table.addCell(df.format(product.getTotalpay()));
        totalPay += product.getTotalpay();
    }

    document.add(table);
    document.add(new Paragraph("--------------------------------------------------"));
    document.add(new Paragraph("Total : ₹" + df.format(totalPay)).setFont(bold));
    document.add(new Paragraph("--------------------------------------------------"));
    document.add(new Paragraph("Thank you!"));

    document.close();

    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bill_" + farmerId + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .contentLength(resource.contentLength())
            .body(resource);
}




@GetMapping("/generate-bill/merchant")
public ResponseEntity<Resource> generate(@RequestParam Long merchantId) throws IOException {
    double totalPay = 0.0;

    Merchant merchant = merchantRepository.findById(merchantId).orElse(null);
    if (merchant == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    List<MProduct> mproducts = mproductRepository.findByMerchant(merchant);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(outputStream);
    PdfDocument pdf = new PdfDocument(writer);
    Document document = new Document(pdf);

    PdfFont bold = PdfFontFactory.createFont("Times-Bold");
    DecimalFormat df = new DecimalFormat("#,##0.00");

    document.add(new Paragraph("MyDigital")
            .setFont(bold).setFontSize(18).setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Fruit Commission Agent").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Market Yard, Solapur").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Phone No: 8010094034").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("Email: mydigital@gmail.com").setTextAlignment(TextAlignment.CENTER));
    document.add(new Paragraph("--------------------------------------------------"));

    document.add(new Paragraph("Merchant ID: " + merchant.getId()));
    document.add(new Paragraph("Merchant: " + merchant.getMName()));
    document.add(new Paragraph("Merchant Phone No: " + merchant.getPhoneNO()));

    document.add(new Paragraph("Date: " + java.time.LocalDate.now()));

    document.add(new Paragraph("--------------------------------------------------"));

    Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2, 2})).useAllAvailableWidth();
    table.addHeaderCell("Qty");
    table.addHeaderCell("Product");
    table.addHeaderCell("Weight");
    table.addHeaderCell("Charges (₹)");
    table.addHeaderCell("Total (₹)");

    for (MProduct mproduct : mproducts) {
        double total = mproduct.getWeight() * mproduct.getFinalPrice();
        table.addCell("1");
        table.addCell(mproduct.getProductname());
        table.addCell(df.format(mproduct.getWeight()));
        table.addCell(df.format(mproduct.getCharges()));
        table.addCell(df.format(mproduct.getFinalPrice()));
        totalPay += mproduct.getFinalPrice();
    }

    document.add(table);
    document.add(new Paragraph("--------------------------------------------------"));
    document.add(new Paragraph("Merchant Pay : ₹" + df.format(totalPay)).setFont(bold));
    document.add(new Paragraph("--------------------------------------------------"));
    document.add(new Paragraph("Thank you!"));

    document.close();

    ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; merchant=bill_" + merchantId + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .contentLength(resource.contentLength())
            .body(resource);
}
}
