package DJ.MyDigital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DJ.MyDigital.Model.Farmer;
import DJ.MyDigital.repository.FarmerRepository;

@Service
public class FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    public Farmer registerFarmer(Farmer farmer) {
        return farmerRepository.save(farmer);
    }

   

    public Optional<Farmer> loginFarmer(String email, String password) {
        return farmerRepository.findByMEmail(email)
                .filter(farmer -> farmer.getPassword().equals(password));
    }

 
    public Optional<Farmer> getFarmerById(Long farmerId) {
        return farmerRepository.findById(farmerId);
    }

    public void updateFarmer(Long id, Farmer updatedFarmer) {
        Optional<Farmer> existingMerchant = farmerRepository.findById(id);
        if (existingMerchant.isPresent()) {
            Farmer farmer = existingMerchant.get();
            farmer.setMName(updatedFarmer.getMName());
            farmer.setPassword(updatedFarmer.getPassword());
            farmer.setDocument(updatedFarmer.getDocument());
            farmer.setMEmail(updatedFarmer.getMEmail());
            farmer.setPhoneNO(updatedFarmer.getPhoneNO());
            farmer.setAddress(updatedFarmer.getAddress());
            farmer.setBankAccountNO(updatedFarmer.getBankAccountNO());
            farmer.setMpayment(updatedFarmer.getMpayment());
            farmer.setTotalpay(updatedFarmer.getTotalpay());
            farmerRepository.save(farmer);
        }
    }

    public void deleteFarmer(Long id) {
        farmerRepository.deleteById(id);
    }
    
    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll(); // Retrieve all farmers
    }

 
    public boolean emailExists(String email) {
        return farmerRepository.findByMEmail(email).isPresent();
    }
    
}
