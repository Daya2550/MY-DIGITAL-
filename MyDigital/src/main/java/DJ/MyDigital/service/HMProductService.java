package DJ.MyDigital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DJ.MyDigital.Model.HMProduct;
import DJ.MyDigital.repository.HMProductRepository;
import jakarta.transaction.Transactional;

@Service
public class HMProductService {

    @Autowired
    private HMProductRepository HMproductRepository;

    private int currentIndex = -1;
    private List<HMProduct> productList;

    public void saveProduct(HMProduct product) {
        HMproductRepository.save(product);
    }

    public List<HMProduct> getAllProducts() {
        return HMproductRepository.findAll();
    }

    public List<HMProduct> getProductsByMerchantId(Long merchantId) {
        return HMproductRepository.findProductsByMerchantId(merchantId);
    }

    public HMProduct getProductById(Long id) {
        return HMproductRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        HMproductRepository.deleteById(id);
    }
    @Transactional
    public void truncateMProductTable() {
        HMproductRepository.truncateMProduct();
    }
}
