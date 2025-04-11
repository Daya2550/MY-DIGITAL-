package DJ.MyDigital.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DJ.MyDigital.Model.Farmer;
import DJ.MyDigital.Model.HMProduct;
import DJ.MyDigital.Model.HProduct;
import DJ.MyDigital.Model.MProduct;
import DJ.MyDigital.Model.Merchant;
import DJ.MyDigital.Model.Product;
import DJ.MyDigital.Model.Transaction;
import DJ.MyDigital.repository.FarmerRepository;
import DJ.MyDigital.repository.HMProductRepository;
import DJ.MyDigital.repository.HProductRepository;
import DJ.MyDigital.repository.MProductRepository;
import DJ.MyDigital.repository.MerchantRepository;
import DJ.MyDigital.repository.ProductRepository;
import DJ.MyDigital.service.ProductService;
import DJ.MyDigital.service.TransactionService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Bidding")
public class BiddingController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private HProductRepository HproductRepository;
    @Autowired
    private HMProductRepository HMproductRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MProductRepository mProductRepository;
    private int productIndex = 0; // To track the current product index
    private float transport=5f; 
    private float laber=0.5f; 
    private float service=0.25f; 
    private float weighting=0.1f; 
    private float bidding=2f; 
    private Long currentProductId;

    @GetMapping("/edit")
    public String showUpdatePage(Model model, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }

        List<Product> products = productService.getAllProducts();
        
        if (products.isEmpty()) {
            model.addAttribute("message", "No products available!");
            return "weight";
        }

        if (currentProductId == null) {
            currentProductId = products.get(0).getId();
        }

        Product product = productService.getProductById(currentProductId);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("currentIndex", products.indexOf(product) + 1);
            model.addAttribute("totalProducts", products.size());
        } else {
            model.addAttribute("message", "Product not found!");
        }

        return "weight";
    }

    @PostMapping("/update") 
    public String updateProduct(@ModelAttribute Product product, Model model) {
        try {
            Product existingProduct = productService.getProductById(product.getId());
            if (existingProduct == null) {
                model.addAttribute("error", "Product not found!");
                return "weight";
            }

            // Update values
            existingProduct.setProductname(product.getProductname());
            existingProduct.setCharges(product.getCharges());
            existingProduct.setFinalPrice(product.getFinalPrice());
            existingProduct.setWeight(product.getWeight());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setFarmerName(product.getFarmerName());
            existingProduct.setDate(product.getDate());
            existingProduct.setRemark(product.getRemark());

            productService.updateProduct(existingProduct);
            return "redirect:/Bidding/next";

        } catch (Exception e) {
            model.addAttribute("error", "Error updating product: " + e.getMessage());
            return "weight";
        }
    }

    @GetMapping("/next")
    public String getNextProduct(Model model, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }
        List<Product> products = productService.getAllProducts();
        
        if (products.isEmpty()) {
            model.addAttribute("message", "No products available!");
            return "weight";
        }

        int currentIndex = -1;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(currentProductId)) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == products.size() - 1 || currentIndex == -1) {
            // At the end or invalid ID, start over
            currentProductId = products.get(0).getId();
        } else {
            currentProductId = products.get(currentIndex + 1).getId();
        }

        Product nextProduct = productService.getProductById(currentProductId);
        model.addAttribute("product", nextProduct);
        model.addAttribute("currentIndex", currentIndex + 2);
        model.addAttribute("totalProducts", products.size());

        return "weight";
    }


    @GetMapping("/view")
    public String viewProduct(Model model, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }
        List<Product> products = productRepository.findAll();
        
        if (!products.isEmpty() && productIndex < products.size()) {
            Product currentProduct = products.get(productIndex);
            model.addAttribute("product", currentProduct);
            model.addAttribute("farmer", currentProduct.getFarmer());
            model.addAttribute("totalProducts", products.size());
            model.addAttribute("currentIndex", productIndex + 1);
        } else {
            model.addAttribute("message", "No more products available.");
        }
        return "view-product";
    }


    
    @PostMapping("/next")
    public String nextProduct() {
        List<Product> products = productRepository.findAll();
        
        if (!products.isEmpty() && productIndex < products.size() - 1) {
            productIndex++;
        }
        return "redirect:/Bidding/view";
    }

    @PostMapping("/previous")
    public String previousProduct() {

        if (productIndex > 0) {
            productIndex--;
        }
        return "redirect:/Bidding/view";
    }

    @PostMapping("/saveToMerchant")
    public String saveToMerchant(@RequestParam("merchantId") Long merchantId, @RequestParam("productId") Long productId , @RequestParam("finalPrice") double finalPrice, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<Merchant> merchantOpt = merchantRepository.findById(merchantId);
    
        if (productOpt.isPresent() && merchantOpt.isPresent()) {
            Product product = productOpt.get();
            Merchant merchant = merchantOpt.get();
            Farmer farmer = product.getFarmer(); // Get associated farmer
        // Calculate the charges for the product
        double transportCharges = product.getWeight() * transport ;
        double laberCharges = product.getWeight() * laber;
        double serviceCharges = product.getWeight() * service;
        double weightingCharges = product.getWeight() * weighting ;
        double biddingCharges = product.getWeight() * bidding;
        double FtotalCharges = transportCharges + laberCharges + serviceCharges + weightingCharges ;
        double MtotalCharges =  laberCharges + serviceCharges + weightingCharges + biddingCharges;


            // Create a new MProduct from the Product
            MProduct mProduct = new MProduct();
            mProduct.setProductname(product.getProductname());
            mProduct.setCharges(MtotalCharges);
            mProduct.setFinalPrice(( finalPrice +MtotalCharges));
            mProduct.setWeight(product.getWeight());
            mProduct.setVariety(product.getVariety());
            mProduct.setQuantity(product.getQuantity());
            mProduct.setFarmerName(product.getFarmerName());
            mProduct.setDate(product.getDate());
            mProduct.setRemark(product.getRemark());
            mProduct.setMerchant(merchant); // Link to the specific merchant
            mProductRepository.save(mProduct);
    
            // Update Product details
            product.setCharges(FtotalCharges); // Keeping as is
            product.setFinalPrice((finalPrice)); // Keeping as is
            product.setBidstatus("SOLD"); // Mark as sold
            product.setTotalpay(finalPrice-FtotalCharges); // Set total payment
            productRepository.save(product); // Save updated product
    
            // Update Farmer's Totalpay
            if (farmer != null) {
                Double currentTotalPay = farmer.getTotalpay();
                double totalPayValue = (currentTotalPay != null) ? currentTotalPay : 0.0;
                double newFarmerTotalPay = totalPayValue + (finalPrice - FtotalCharges);
                farmer.setTotalpay(newFarmerTotalPay);
                farmerRepository.save(farmer);
            }
    
            // Update Merchant's Totalpay
            Double currentMerchantTotalPay = merchant.getTotalpay();
            double totalPayValue = (currentMerchantTotalPay != null) ? currentMerchantTotalPay : 0.0;
            double newMerchantTotalPay = totalPayValue + finalPrice + MtotalCharges;
            merchant.setTotalpay(newMerchantTotalPay);
        
       
        Transaction transaction = new Transaction();
        transaction.setFName(farmer.getMName());
        transaction.setFphoneNO(farmer.getPhoneNO());
        transaction.setFproductname(product.getProductname());
        transaction.setFcharges(FtotalCharges);
        transaction.setFfinalPrice(finalPrice-FtotalCharges);
        transaction.setFweight(product.getWeight());
        transaction.setMName(merchant.getMName());
        transaction.setMphoneNO(merchant.getPhoneNO());
        transaction.setMcharges(MtotalCharges);
        transaction.setMfinalPrice(finalPrice + MtotalCharges);
        transaction.setDate(product.getDate());
        double profit = FtotalCharges+MtotalCharges;
        transaction.setProfit(profit);
        transactionService.addTransaction(transaction);


            HProduct hProduct = new HProduct();
            hProduct.setProductname(product.getProductname());
            hProduct.setCharges(FtotalCharges);
            hProduct.setFinalPrice(finalPrice);
            hProduct.setWeight(product.getWeight());
            hProduct.setVariety(product.getVariety());
            hProduct.setQuantity(product.getQuantity());
            hProduct.setFarmerName(product.getFarmerName());
            hProduct.setDate(product.getDate());
            hProduct.setRemark(product.getRemark());
            hProduct.setFarmer(farmer); // Link to the specific farmer
            hProduct.setFinalPrice(finalPrice);
            hProduct.setBidstatus("SOLD"); // Mark as sold
            hProduct.setTotalpay(finalPrice-FtotalCharges); // Set total payment
            hProduct.setBasePrice(product.getBasePrice());
            HproductRepository.save(hProduct);


            HMProduct hmProduct = new HMProduct();
            hmProduct.setProductname(product.getProductname());
            hmProduct.setCharges(MtotalCharges);
            hmProduct.setFinalPrice(finalPrice);
            hmProduct.setWeight(product.getWeight());
            hmProduct.setVariety(product.getVariety());
            hmProduct.setQuantity(product.getQuantity());
            hmProduct.setFarmerName(product.getFarmerName());
            hmProduct.setDate(product.getDate());
            hmProduct.setRemark(product.getRemark());
            hmProduct.setMerchant(merchant); // Link to the specific merchant
            hmProduct.setFinalPrice(finalPrice);
            HMproductRepository.save(hmProduct);

        }
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty() && productIndex < products.size() - 1) {
            productIndex++;
        }
        return "redirect:/Bidding/view";
    }

    @GetMapping("/list")
    public String listTransactions(Model model, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }

        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transaction-list";
    }

}
