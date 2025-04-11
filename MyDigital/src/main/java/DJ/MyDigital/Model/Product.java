package DJ.MyDigital.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productname;
    private double charges=0;
    private double finalPrice =0;
    private double weight =0;
    private String variety;
    private int quantity;
    private String bidstatus = "NOT";  // Default value
    private double Totalpay = 0.0;    // Default value
    private double basePrice = 0.0;    // Default value
    private String farmerName;
    private String date;
    private String remark;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)  
    private Farmer farmer;

    // Default Constructor
    public Product() {}

    // Constructor with all fields
    public Product(Long id, String productname, double charges, double finalPrice, double weight, String variety,
                   int quantity, String bidstatus, double Totalpay, double basePrice, 
                   String farmerName, String date, String remark, Farmer farmer) {
        this.id = id;
        this.productname = productname;
        this.charges = charges;
        this.finalPrice = finalPrice;
        this.weight = weight;
        this.variety = variety;
        this.quantity = quantity;
        this.bidstatus = bidstatus;  // Now properly set
        this.Totalpay = Totalpay;    // Now properly set
        this.basePrice = basePrice;  // Now properly set
        this.farmerName = farmerName;
        this.date = date;
        this.remark = remark;
        this.farmer = farmer;
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

    public String getBidstatus() { return bidstatus; }
    public void setBidstatus(String bidstatus) { this.bidstatus = bidstatus; }

    public double getTotalpay() { return Totalpay; }
    public void setTotalpay(double Totalpay) { this.Totalpay = Totalpay; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public String getFarmerName() { return farmerName; }
    public void setFarmerName(String farmerName) { this.farmerName = farmerName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public Farmer getFarmer() { return farmer; }
    public void setFarmer(Farmer farmer) { this.farmer = farmer; }

    

    // Corrected method to get farmer ID
    public Long getFarmerId() {
        return (farmer != null) ? farmer.getId() : null;
    }
}
