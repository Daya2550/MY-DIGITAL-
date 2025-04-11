package DJ.MyDigital.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DJ.MyDigital.Model.Farmer;
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByMEmail(String MEmail);
   
}

