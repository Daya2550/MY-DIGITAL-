package DJ.MyDigital.Model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "farmer")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String MName;
    private String MEmail;
    private String password;
    private String phoneNO;
    private String address;
    private String document;
    private String BankAccountNO;
    private String Mpayment = "NOT";  // Default value
    private double Totalpay = 0.0;    // Default value

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;  // Corrected reference to products
    
    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HProduct> Hproducts;  // Corrected reference to products
    // Constructors
    public Farmer() {}

    public Farmer(Long id, String MName, String MEmail, String password, String phoneNO, String address, 
                    String document, String BankAccountNO, String Mpayment, double Totalpay, List<Product> products) {
        this.id = id;
        this.MName = MName;
        this.MEmail = MEmail;
        this.password = password;
        this.phoneNO = phoneNO;
        this.address = address;
        this.document = document;
        this.BankAccountNO = BankAccountNO;
        this.Mpayment = Mpayment;
        this.Totalpay = Totalpay;
        this.products = products;
    }

    public List<HProduct> getHproducts() {
        return Hproducts;
    }

    public void setHproducts(List<HProduct> hproducts) {
        Hproducts = hproducts;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMName() { return MName; }
    public void setMName(String MName) { this.MName = MName; }

    public String getMEmail() { return MEmail; }
    public void setMEmail(String MEmail) { this.MEmail = MEmail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNO() { return phoneNO; }
    public void setPhoneNO(String phoneNO) { this.phoneNO = phoneNO; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }

    public String getBankAccountNO() { return BankAccountNO; }
    public void setBankAccountNO(String bankAccountNO) { BankAccountNO = bankAccountNO; }

    public String getMpayment() { return Mpayment; }
    public void setMpayment(String Mpayment) { this.Mpayment = Mpayment; }

    public double getTotalpay() { return Totalpay; }
    public void setTotalpay(double Totalpay) { this.Totalpay = Totalpay; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public Object getBasePrice() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBasePrice'");
    }
}
