package DJ.MyDigital.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DJ.MyDigital.Model.Product;
import DJ.MyDigital.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> getProductsByFarmerId(Long farmerId) {
        return productRepository.findByFarmerId(farmerId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
       

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }




    public Long getFarmerIdByProductId(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.map(p -> p.getFarmer().getId()).orElse(null);
    }

    @Transactional
    public void truncateProductTable() {
        productRepository.truncateProductTable();
    }

    public List<Product> getHistoryProducts(Long farmerId) {
        return productRepository.findSoldProductsByFarmerId(farmerId); // Fetch only SOLD products
    }

    public List<Product> getSoldProductsByFarmerId(Long farmerId) {
        return productRepository.findSoldProductsByFarmerId(farmerId);
    }

}
