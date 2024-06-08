package kg.nuratan.springcorepractice_bank.services;

import jakarta.transaction.Transactional;
import kg.nuratan.springcorepractice_bank.entities.Account;
import kg.nuratan.springcorepractice_bank.entities.User;
import kg.nuratan.springcorepractice_bank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Value("${account.transfer-commission}")
    private int transferCommission;
    @Value("${account.default-amount}")
    private Double defaultAmount;
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(User user) {
        Account account = new Account();
        account.setUser(user);
        account.setBalance(defaultAmount);
        return accountRepository.save(account);
    }
    @Transactional
    public void deposit(Long accountId, Double amount) {
        Account account = accountRepository.getReferenceById(accountId);
        account.setBalance(account.getBalance()+amount);
    }
    @Transactional
    public int withdraw(Long accountId, Double amount) {
        Account account = accountRepository.getReferenceById(accountId);
        if(account.getBalance()-amount<0) return -1;
        account.setBalance(account.getBalance()-amount);
        return 0;
    }
    @Transactional
    public int transferMoney(Long fromId, Long toId, Double amount) {
        Account fromAccount = accountRepository.getReferenceById(fromId);
        Account toAccount = accountRepository.getReferenceById(toId);
        int commission = 0;
        if(fromAccount.getUser()!=toAccount.getUser()) commission=transferCommission;
        if(withdraw(fromId, amount+amount*commission/100)!=-1){
            deposit(toId, amount);
            return 0;
        }
        return -1;
    }
    @Transactional
    public int deleteAccount(Long accountId) {
        if(accountRepository.findById(accountId).get().getUser().getAccounts().size()==1) return -1;
        accountRepository.deleteById(accountId);
        return 0;
    }
}
