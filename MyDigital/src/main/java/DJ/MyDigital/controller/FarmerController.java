package DJ.MyDigital.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DJ.MyDigital.Model.Farmer;
import DJ.MyDigital.Model.Feedback;
import DJ.MyDigital.Model.HProduct;
import DJ.MyDigital.Model.Product;
import DJ.MyDigital.service.FarmerService;
import DJ.MyDigital.service.HProductService;
import DJ.MyDigital.service.ProductService;
import DJ.MyDigital.webScraping.ScrapingService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Farmer")
public class FarmerController {

    Long farmerId ;
    @Autowired
    private ScrapingService scrapingService;
    @Autowired
    private FarmerService farmerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HProductService HproductService;

    // Farmer Registration
    @GetMapping("/register")
public String showRegistrationForm(Model model) {
    model.addAttribute("farmer", new Farmer());
    return "Farmer_Registration"; // Ensure this file exists
}


@PostMapping("/register")
public String registerFarmer(@ModelAttribute Farmer farmer, Model model) {
    String email = farmer.getMEmail();
    String pass = farmer.getPassword();
    if (!farmerService.emailExists(email)) {
        farmerService.registerFarmer(farmer);
        return "Farmer_Login"; // Ensure this file exists in templates
    } else {
        model.addAttribute("error", "Email already exists. Please use a different email.");
        return "Farmer_Registration"; // Ensure this file exists in templates
    }
}

@GetMapping("/admin/register")
public String showAdminRegistrationForm(Model model) {
    model.addAttribute("admin", new Farmer());  // Using Farmer for now, replace with Admin entity if available
    return "admin-Farmer_Registration";  // Ensure this file exists
}

@PostMapping("/admin/register")
public String registerFarmerAdmin(@ModelAttribute Farmer farmer) {
    farmerService.registerFarmer(farmer);
    return "redirect:/Farmer/farmers";  // Redirect to the GET method that fetches the updated list
}




    // Farmer Login
    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("farmerId") != null) {
            return "redirect:/Farmer/FarmerHome"; // Redirect if already logged in
        }
        return "Farmer_Login";
    }

    @PostMapping("/login")
    public String loginFarmer(@RequestParam String email, @RequestParam String password, 
                              Model model, HttpSession session) {
        Optional<Farmer> farmer = farmerService.loginFarmer(email, password);

        if (farmer.isPresent()) {
            session.setAttribute("farmerId", farmer.get().getId());
            session.setAttribute("farmerName", farmer.get().getMName());
            session.setAttribute("farmerEmail", farmer.get().getMEmail());
            session.setAttribute("totalPrice", farmer.get().getTotalpay());
            session.setAttribute("paymentStautes", farmer.get().getMpayment());
            return "redirect:/Farmer/FarmerHome"; 
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "Farmer_Login";
        }
    }

    // Farmer Home Page
    @GetMapping("/FarmerHome")
    public String FarmerHomePage(Model model, HttpSession session) {
        farmerId = (Long) session.getAttribute("farmerId");

        if (farmerId != null) {
            model.addAttribute("farmerName", session.getAttribute("farmerName"));
            model.addAttribute("farmerEmail", session.getAttribute("farmerEmail"));
            model.addAttribute("products", productService.getProductsByFarmerId(farmerId));
            model.addAttribute("farmerId", farmerId);
            model.addAttribute("payment", session.getAttribute("paymentStautes"));
            model.addAttribute("Total", session.getAttribute("totalPrice"));
            model.addAttribute("commodities", scrapingService.getCommodities());
            model.addAttribute("data", scrapingService.scrapeTableData());
            model.addAttribute("weatherList", scrapingService.getWeatherData());
            return "FarmerHome";
        }
        return "redirect:/Farmer/FarmerHome";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Farmer_Login";
    }

    // Edit Farmer Profile (Self)
    @GetMapping("/self/edit/{id}")
    public String editFarmer(@PathVariable Long id, Model model) {
        Optional<Farmer> farmer = farmerService.getFarmerById(id);
        if (farmer.isPresent()) {
            model.addAttribute("farmer", farmer.get());
            return "edit-farmer"; 
        } else {
            return "redirect:/Farmer/FarmerHome";
        }
    }

    @PostMapping("/self/update/{id}")
    public String updateSelfFarmer(@PathVariable Long id, @ModelAttribute Farmer updatedFarmer) {
        farmerService.updateFarmer(id, updatedFarmer);
        return "redirect:/Farmer/FarmerHome";
    }

    // Update Farmer Profile (Admin)
    @PostMapping("/update/{id}")
    public String updateFarmer(@PathVariable Long id, @ModelAttribute Farmer updatedFarmer) {
        farmerService.updateFarmer(id, updatedFarmer);
        return "redirect:/Farmer/farmers";
    }

    // Delete Farmer
    @PostMapping("/delete/{id}")
    public String deleteFarmer(@PathVariable Long id) { 
        farmerService.deleteFarmer(id); 
        return "redirect:/Farmer/farmers";
    }

    // Admin Edit Farmer Profile
    @GetMapping("/admin/edit/{id}")
    public String adminEditFarmer(@PathVariable Long id, Model model) {
        Optional<Farmer> farmer = farmerService.getFarmerById(id);
        if (farmer.isPresent()) {
            model.addAttribute("farmer", farmer.get());
            return "farmeradmin";
        } else {
            return "redirect:/Farmer/FarmerHome";
        }
    }

    // List All Farmers
    @GetMapping("/farmers")
    public String getFarmersList(Model model, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }

        List<Farmer> farmers = farmerService.getAllFarmers();
        model.addAttribute("farmers", farmers);
        return "farmers-list";
    }

    @GetMapping("/farmer/{farmerId}")
    public String getProductsByFarmer(@PathVariable Long farmerId, Model model) {
        Optional<Farmer> optionalFarmer = farmerService.getFarmerById(farmerId);
        if (optionalFarmer.isPresent()) {
            List<Product> products = productService.getProductsByFarmerId(farmerId);
            model.addAttribute("products", products);
            model.addAttribute("farmer", optionalFarmer.get());
            return "farmer-product-list";
        }
        return "error-page";
    }

    
@GetMapping("/history/self")
public String getMethodName(Model model,HttpSession session) {
    farmerId = (Long) session.getAttribute("farmerId");
    model.addAttribute("products", (List<HProduct>) HproductService.getProductsByFarmerId(farmerId));
    return "productselfhistry";
}

@GetMapping("/Contactus")
public String contactUs(Model model) {
    model.addAttribute("feedback", new Feedback());
    return "Contactus"; 
}

}
