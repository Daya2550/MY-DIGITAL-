package DJ.MyDigital.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import DJ.MyDigital.Model.MProduct;
import DJ.MyDigital.Model.Merchant;
import jakarta.transaction.Transactional;


@Repository
public interface MProductRepository extends JpaRepository<MProduct, Long> {

    // Custom query to retrieve products by Merchant ID
    @Query("SELECT p FROM MProduct p WHERE p.merchant.id = :merchantId")
    List<MProduct> findProductsByMerchantId(@Param("merchantId") Long merchantId);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE Mproduct", nativeQuery = true)
    void truncateMProduct();
    List<MProduct> findByMerchant(Merchant merchant);
}



