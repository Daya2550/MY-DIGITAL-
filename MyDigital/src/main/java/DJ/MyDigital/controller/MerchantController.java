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

import DJ.MyDigital.Model.HMProduct;
import DJ.MyDigital.Model.MProduct;
import DJ.MyDigital.Model.Merchant;
import DJ.MyDigital.service.HMProductService;
import DJ.MyDigital.service.MProductService;
import DJ.MyDigital.service.MerchantService;
import DJ.MyDigital.webScraping.ScrapingService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Merchant")
public class MerchantController {

    Long merchantId;

    @Autowired
    private ScrapingService scrapingService;

    @Autowired
    private MProductService productService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private HMProductService HMproductService;


    // Regitration of the Merchant Profile 


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("merchant", new Merchant());
        return "MerchantRegistration";
    }

    @PostMapping("/register")
    public String registerMerchant(@ModelAttribute Merchant merchant, Model model) {
        String email = merchant.getMEmail();
        String pass = merchant.getPassword();
        if (!merchantService.emailExists(email)) {
            merchantService.registerMerchant(merchant);
            return "MerchantLogin";
        } else {
            model.addAttribute("error", "Email already exists. Please use a different email.");
            return "MerchantRegistration";
        }
    }



    //  Merchant Login 

    @GetMapping("/login")
    public String showLoginForm(HttpSession session) {
        if (session.getAttribute("merchantId") != null) {
            return "redirect:/Merchant/MerchantHome"; // Redirect if already logged in
        }
        return "MerchantLogin";
    }

    @PostMapping("/login")
    public String loginMerchant(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        Optional<Merchant> merchant = merchantService.loginMerchant(email, password);

        if (merchant.isPresent()) {
            session.setAttribute("merchantId", merchant.get().getId());
            session.setAttribute("merchantName", merchant.get().getMName());
            session.setAttribute("merchantEmail", merchant.get().getMEmail());
            session.setAttribute("payment", merchant.get().getMpayment());
            session.setAttribute("Total", merchant.get().getTotalpay());
            System.out.println(merchant.get().getTotalpay());
            return "redirect:/Merchant/MerchantHome";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "MerchantLogin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Merchant/login";
    }


    // Merchant Home page 
    @GetMapping("/MerchantHome")
    public String MerchantHomePage(Model model, HttpSession session) {
        merchantId = (Long) session.getAttribute("merchantId");

        if (merchantId != null) {
            model.addAttribute("merchantName", session.getAttribute("merchantName"));
            model.addAttribute("merchantEmail", session.getAttribute("merchantEmail"));
            model.addAttribute("products", productService.getProductsByMerchantId(merchantId));
            model.addAttribute("merchantId", session.getAttribute("merchantId"));
            model.addAttribute("payment", session.getAttribute("payment"));
            model.addAttribute("Total", session.getAttribute("Total"));
            model.addAttribute("commodities", scrapingService.getCommodities());
            model.addAttribute("data", scrapingService.scrapeTableData());
            model.addAttribute("weatherList", scrapingService.getWeatherData());
            return "MerchantHome";
        }
        return "redirect:/Merchant/login";
    }

    
    // All Merchantes list
    @GetMapping("/merchants" )
    public String listMerchants(Model model, HttpSession session) {

        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }


        model.addAttribute("merchants", merchantService.getAllMerchants());
        return "merchant_list";
    }

    

    // Merchant Prifile Update By Admin 
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Merchant> merchant = merchantService.getMerchantById(id);
        if (merchant.isPresent()) {
            model.addAttribute("merchant", merchant.get());
            return "MerchantUpdate";
        }
        return "redirect:/Merchant/merchants";
    }

    @PostMapping("/update/{id}")
    public String updateMerchant(@PathVariable Long id, @ModelAttribute Merchant updatedMerchant) {
        merchantService.updateMerchant(id, updatedMerchant);
        return "redirect:/Merchant/merchants";
    }

     // Merchant Prifile Delete By Admin 
    @PostMapping("/delete/{id}")
    public String deleteMerchant(@PathVariable Long id) {
        merchantService.deleteMerchant(id);
        return "redirect:/Merchant/merchants";
    }

     // Merchant Prifile Update By Self 
    @GetMapping("/self/edit/{id}")
    public String selfEditForm(@PathVariable Long id, Model model) {
        Optional<Merchant> merchant = merchantService.getMerchantById(id);
        if (merchant.isPresent()) {
            model.addAttribute("merchant", merchant.get());
            return "MerchantUpdateSelf";
        }
        return "redirect:/Merchant/MerchantHome";
    }

    @PostMapping("/self/update/{id}")
    public String selfupdateMerchant(@PathVariable Long id, @ModelAttribute Merchant updatedMerchant) {
        merchantService.updateMerchant(id, updatedMerchant);
        return "redirect:/Merchant/MerchantHome";
    }

    // Get Specefic Merchant Productes by the Id 
     @GetMapping("/merchant/{merchantId}")
    public String getProductsByMerchant(@PathVariable Long merchantId, Model model) {
        Optional<Merchant> optionalMerchant = merchantService.getMerchantById(merchantId);
        if (optionalMerchant.isPresent()) {
            List<MProduct> products = productService.getProductsByMerchantId(merchantId);
            model.addAttribute("products", products);
            model.addAttribute("merchant", optionalMerchant.get());
            return "merchant-product-list";
        }
        return "error-page";
    }
    @GetMapping("/history/self")
public String getMethodName(Model model,HttpSession session) {
    merchantId = (Long) session.getAttribute("merchantId");
    model.addAttribute("products", (List<HMProduct>) HMproductService.getProductsByMerchantId(merchantId));
    return "merchant-product-history";
}

    
}
