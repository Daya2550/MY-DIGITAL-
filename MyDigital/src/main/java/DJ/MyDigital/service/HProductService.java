package DJ.MyDigital.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DJ.MyDigital.Model.HProduct;
import DJ.MyDigital.repository.HProductRepository;
import jakarta.transaction.Transactional;

@Service
public class HProductService {

    @Autowired
    private HProductRepository HproductRepository;

    public void saveProduct(HProduct product) {
        HproductRepository.save(product);
    }

    public List<HProduct> getAllProducts() {
        return HproductRepository.findAll();
    }


    

    public HProduct getProductById(Long id) {
        return HproductRepository.findById(id).orElse(null);
    }
       

    public void deleteProduct(Long id) {
        HproductRepository.deleteById(id);
    }

    public void updateProduct(HProduct product) {
        HproductRepository.save(product);
    }




    public Long getFarmerIdByProductId(Long productId) {
        Optional<HProduct> product = HproductRepository.findById(productId);
        return product.map(p -> p.getFarmer().getId()).orElse(null);
    }

    @Transactional
    public void truncateProductTable() {
        HproductRepository.truncateProductTable();
    }

    public List<HProduct> getHistoryProducts(Long farmerId) {
        return HproductRepository.findSoldProductsByFarmerId(farmerId); // Fetch only SOLD products
    }

    public List<HProduct> getSoldProductsByFarmerId(Long farmerId) {
        return HproductRepository.findSoldProductsByFarmerId(farmerId);
    }

    public List<HProduct> getProductsByFarmerId(Long farmerId) {
        return HproductRepository.findByFarmerId(farmerId); // assuming this returns a List<HProduct>
    }
    

}
