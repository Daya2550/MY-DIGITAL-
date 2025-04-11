package DJ.MyDigital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DJ.MyDigital.Model.MProduct;
import DJ.MyDigital.repository.MProductRepository;
import jakarta.transaction.Transactional;

@Service
public class MProductService {

    @Autowired
    private MProductRepository productRepository;

    private int currentIndex = -1;
    private List<MProduct> productList;

    public void saveProduct(MProduct product) {
        productRepository.save(product);
    }

    public List<MProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public List<MProduct> getProductsByMerchantId(Long merchantId) {
        return productRepository.findProductsByMerchantId(merchantId);
    }

    public MProduct getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Transactional
    public void truncateMProductTable() {
        productRepository.truncateMProduct();
    }
}



