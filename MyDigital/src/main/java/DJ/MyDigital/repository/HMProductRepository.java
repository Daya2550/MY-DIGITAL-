package DJ.MyDigital.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import DJ.MyDigital.Model.HMProduct;
import jakarta.transaction.Transactional;


@Repository
public interface HMProductRepository extends JpaRepository<HMProduct, Long> {

    // Custom query to retrieve products by Merchant ID
    @Query("SELECT p FROM HMProduct p WHERE p.merchant.id = :merchantId")
    List<HMProduct> findProductsByMerchantId(@Param("merchantId") Long merchantId);

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE HMproduct", nativeQuery = true)
    void truncateMProduct();
}
