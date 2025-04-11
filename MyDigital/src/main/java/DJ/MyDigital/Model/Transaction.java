package DJ.MyDigital.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "transaction")
public class Transaction {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String FName;
    private String FphoneNO;
    private String Fproductname;
    private double Fcharges;
    private double FfinalPrice;
    private double Fweight ;
    private String MName;
    private String MphoneNO;
    private double Mcharges;
    private double MfinalPrice;
    private String date;
    private double profit;
    public Transaction(Long id, String fName, String fphoneNO, String fproductname, double fcharges, double ffinalPrice,
            double fweight, String mName, String mphoneNO, double mcharges, double mfinalPrice, String date,
            double profit) {
        this.id = id;
        FName = fName;
        FphoneNO = fphoneNO;
        Fproductname = fproductname;
        Fcharges = fcharges;
        FfinalPrice = ffinalPrice;
        Fweight = fweight;
        MName = mName;
        MphoneNO = mphoneNO;
        Mcharges = mcharges;
        MfinalPrice = mfinalPrice;
        this.date = date;
        this.profit = profit;
    }
    public Transaction() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFName() {
        return FName;
    }
    public void setFName(String fName) {
        FName = fName;
    }
    public String getFphoneNO() {
        return FphoneNO;
    }
    public void setFphoneNO(String fphoneNO) {
        FphoneNO = fphoneNO;
    }
    public String getFproductname() {
        return Fproductname;
    }
    public void setFproductname(String fproductname) {
        Fproductname = fproductname;
    }
    public double getFcharges() {
        return Fcharges;
    }
    public void setFcharges(double fcharges) {
        Fcharges = fcharges;
    }
    public double getFfinalPrice() {
        return FfinalPrice;
    }
    public void setFfinalPrice(double ffinalPrice) {
        FfinalPrice = ffinalPrice;
    }
    public double getFweight() {
        return Fweight;
    }
    public void setFweight(double fweight) {
        Fweight = fweight;
    }
    public String getMName() {
        return MName;
    }
    public void setMName(String mName) {
        MName = mName;
    }
    public String getMphoneNO() {
        return MphoneNO;
    }
    public void setMphoneNO(String mphoneNO) {
        MphoneNO = mphoneNO;
    }
    public double getMcharges() {
        return Mcharges;
    }
    public void setMcharges(double mcharges) {
        Mcharges = mcharges;
    }
    public double getMfinalPrice() {
        return MfinalPrice;
    }
    public void setMfinalPrice(double mfinalPrice) {
        MfinalPrice = mfinalPrice;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getProfit() {
        return profit;
    }
    public void setProfit(double profit2) {
        this.profit = profit2;
    }


    
}
