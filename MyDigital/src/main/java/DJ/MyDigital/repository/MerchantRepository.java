package DJ.MyDigital.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DJ.MyDigital.Model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByMEmail(String MEmail);
}

