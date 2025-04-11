package DJ.MyDigital.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import DJ.MyDigital.Model.Transaction;
public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    
}