package DJ.MyDigital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DJ.MyDigital.Model.Merchant;
import DJ.MyDigital.repository.MerchantRepository;

@Service
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public Merchant registerMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Optional<Merchant> loginMerchant(String email, String password) {
        return merchantRepository.findByMEmail(email)
                .filter(merchant -> merchant.getPassword().equals(password));
    }

 
    public Optional<Merchant> findById(Long merchantId) {
        return merchantRepository.findById(merchantId);
    }
    

    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    public Optional<Merchant> getMerchantById(Long id) {
        return merchantRepository.findById(id);
    }

    public void updateMerchant(Long id, Merchant updatedMerchant) {
        Optional<Merchant> existingMerchant = merchantRepository.findById(id);
        if (existingMerchant.isPresent()) {
            Merchant merchant = existingMerchant.get();
            merchant.setMName(updatedMerchant.getMName());
            merchant.setMEmail(updatedMerchant.getMEmail());
            merchant.setPhoneNO(updatedMerchant.getPhoneNO());
            merchant.setAddress(updatedMerchant.getAddress());
            merchant.setBankAccountNO(updatedMerchant.getBankAccountNO());
            merchant.setMpayment(updatedMerchant.getMpayment());
            merchant.setPassword(updatedMerchant.getPassword());
            merchantRepository.save(merchant);
        }
    }

    public void deleteMerchant(Long id) {
        merchantRepository.deleteById(id);
    }


    public boolean emailExists(String email) {
        return merchantRepository.findByMEmail(email).isPresent();
    }
}
