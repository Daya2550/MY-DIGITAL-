package DJ.MyDigital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DJ.MyDigital.Model.Farmer;
import DJ.MyDigital.Model.Feedback;
import DJ.MyDigital.Model.HMProduct;
import DJ.MyDigital.Model.HProduct;
import DJ.MyDigital.Model.MProduct;
import DJ.MyDigital.Model.Product;
import DJ.MyDigital.Model.Transaction;
import DJ.MyDigital.service.FarmerService;
import DJ.MyDigital.service.FeedbackService;
import DJ.MyDigital.service.HMProductService;
import DJ.MyDigital.service.HProductService;
import DJ.MyDigital.service.MProductService;
import DJ.MyDigital.service.MerchantService;
import DJ.MyDigital.service.ProductService;
import DJ.MyDigital.service.TransactionService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private MProductService mproductService;


    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HProductService HproductService;

    @Autowired
    private HMProductService HMproductService;

    @GetMapping("/AdminHome")
    public String AdminHomePage(Model model, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }

        List<HMProduct> hmproducts = HMproductService.getAllProducts();
        model.addAttribute("hmproducts", hmproducts);

        List<HProduct> hproducts = HproductService.getAllProducts();
        model.addAttribute("hproducts", hproducts);
        
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        
        List<Farmer> farmers = farmerService.getAllFarmers();
        model.addAttribute("farmers", farmers);
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("merchants", merchantService.getAllMerchants());

        List<MProduct> mproducts = mproductService.getAllProducts();
        model.addAttribute("mproducts", mproducts);
        
        List<Transaction> transactions = transactionService.getAllTransactions();
        model.addAttribute("transactions", transactions);

        return "AdminHomepage";
    }

    @GetMapping("/AdminSign")
    public String AdminSignPage(Model model) {
        return "Adminsign";
    }

    @PostMapping("/Sign")
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        if (email.equals("admin@gmail.com") && password.equals("admin")) {
            session.setAttribute("validate", "true"); // Store admin email in session
            return "redirect:/Admin/AdminHome"; // Redirect to admin dashboard
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "Adminsign"; // Show error message on login page
        }
    }

    @GetMapping("/nopage")
    public String AdminLogout() {
        return "404";
    }
}
