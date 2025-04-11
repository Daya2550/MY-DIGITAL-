package DJ.MyDigital.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DJ.MyDigital.Model.Transaction;
import DJ.MyDigital.repository.TransactionRepository;
@Service
public class TransactionService {
    

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
