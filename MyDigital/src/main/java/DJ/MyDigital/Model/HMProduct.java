package DJ.MyDigital.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "HMproduct")
public class HMProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productname;
    private double charges;
    private double finalPrice;
    private double weight;
    private String variety;
    private int quantity;
    private String farmerName;
    private String date;
    private String remark;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)  // Corrected foreign key reference
    private Merchant merchant;

    // Constructors
    public HMProduct() {}

    public HMProduct(Long id, String productname, double charges, double finalPrice, double weight, String variety,
                   int quantity, String farmerName, String date, String remark, Merchant merchant) {
        this.id = id;
        this.productname = productname;
        this.charges = charges;
        this.finalPrice = finalPrice;
        this.weight = weight;
        this.variety = variety;
        this.quantity = quantity;
        this.farmerName = farmerName;
        this.date = date;
        this.remark = remark;
        this.merchant = merchant;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductname() { return productname; }
    public void setProductname(String productname) { this.productname = productname; }

    public double getCharges() { return charges; }
    public void setCharges(double charges) { this.charges = charges; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getVariety() { return variety; }
    public void setVariety(String variety) { this.variety = variety; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }
}
