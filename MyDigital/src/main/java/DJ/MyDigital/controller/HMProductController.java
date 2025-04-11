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

import DJ.MyDigital.Model.HMProduct;
import DJ.MyDigital.Model.MProduct;
import DJ.MyDigital.Model.Merchant;
import DJ.MyDigital.service.HMProductService;
import DJ.MyDigital.service.MProductService;
import DJ.MyDigital.service.MerchantService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Merchant/HMproducts")
public class HMProductController {

    @Autowired
    private MProductService productService;

    @Autowired
    private HMProductService HMproductService;

    @Autowired
private HMProductService hMProductService;


    @Autowired
    private MerchantService merchantService;

    // New Product add by Merchant
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product", new MProduct());
        return "Merchantproduct-form";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") MProduct product, HttpSession session) {
        Long merchantId = (Long) session.getAttribute("merchantId");

        if (merchantId != null) {
            Optional<Merchant> optionalMerchant = merchantService.getMerchantById(merchantId);
            optionalMerchant.ifPresent(product::setMerchant);
            productService.saveProduct(product);
            return "redirect:/Merchant/MerchantHome";
        }
        return "error";
    }

    // @GetMapping("/allProducts")
    // public String listAllProducts(Model model, HttpSession session) {
    //     List<HMProduct> products = HMproductService.getAllProducts();
    //     model.addAttribute("products", products);
    //     return "AllMerchantProducts";
    // }
    @GetMapping("/allProducts")
    public String listAllProducts(Model model) {
        List<HMProduct> products = hMProductService.getAllProducts();
        model.addAttribute("products", products);
        return "AllMerchantProducts"; // Ensure you have this template/view
    }
    

    // Update the Product by Merchant
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MProduct product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "MerchantEditProduct";
        }
        return "redirect:/Merchant/products";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute MProduct updatedProduct) {
        MProduct existingProduct = productService.getProductById(updatedProduct.getId());
        if (existingProduct != null) {
            updatedProduct.setMerchant(existingProduct.getMerchant()); // Retain merchant reference
            productService.saveProduct(updatedProduct);
        }
        return "redirect:/Merchant/MerchantHome";
    }

    // Delete the Product
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/Merchant/MerchantHome";
    }

    // Admin: View products of a specific Merchant
    @GetMapping("/merchant/{merchantId}")
    public String getProductsByMerchant(@PathVariable Long merchantId, Model model) {
        Optional<Merchant> optionalMerchant = merchantService.getMerchantById(merchantId);
        if (optionalMerchant.isPresent()) {
            List<MProduct> products = productService.getProductsByMerchantId(merchantId);
            model.addAttribute("products", products);
            model.addAttribute("merchant", optionalMerchant.get());
            return "MerchantProductAdminUpdate";
        }
        return "error";
    }

    // Admin: View all products
    @GetMapping("/admin")
    public String listProducts(Model model, HttpSession session) {
        if (session.getAttribute("validate") == null) {
            return "redirect:/Admin/AdminSign"; // Redirect to sign-in page if not logged in
        }

        List<HMProduct> hmproducts = HMproductService.getAllProducts();
        model.addAttribute("products", hmproducts);
        return "MerchantProductListAdmin";
    }

    // Admin: Edit a single product
    @GetMapping("/admin/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        MProduct product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "MerchantProductAdminUpdate";
        }
        return "redirect:/Merchant/products/admin";
    }

    @PostMapping("/admin/update")
    public String updateAdminProduct(@ModelAttribute MProduct product) {
        MProduct existingProduct = productService.getProductById(product.getId());
        if (existingProduct != null) {
            product.setMerchant(existingProduct.getMerchant()); // Retain merchant reference
            productService.saveProduct(product);
        }
        return "redirect:/Merchant/products/admin";
    }

    // Admin: Delete a single product
    @GetMapping("/admin/delete/{id}")
    public String deleteProductAdmin(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/Merchant/products/admin";
    } 

    // Admin: Truncate the Mproduct table   /Merchant/products/truncate
    @GetMapping("/truncate")
    public String truncateMProductTable() {
        productService.truncateMProductTable();
        return "redirect:/Merchant/products/admin";
    }
}
