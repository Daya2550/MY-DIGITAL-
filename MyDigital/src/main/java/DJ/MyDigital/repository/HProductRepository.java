package DJ.MyDigital.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import DJ.MyDigital.Model.HProduct;
import jakarta.transaction.Transactional;
@Repository
public interface HProductRepository extends JpaRepository<HProduct, Long> {

    // Custom query to retrieve products by Merchant ID
    
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE product", nativeQuery = true)
    void truncateProductTable();

    @Query("SELECT p FROM HProduct p WHERE p.farmer.id = :farmerId AND p.bidstatus = 'SOLD'")
    List<HProduct> findSoldProductsByFarmerId(@Param("farmerId") Long farmerId);

    @Query("SELECT p FROM HProduct p WHERE p.farmer.id = :farmerId")
    List<HProduct> findByFarmerId(@Param("farmerId") Long farmerId);

    
}
