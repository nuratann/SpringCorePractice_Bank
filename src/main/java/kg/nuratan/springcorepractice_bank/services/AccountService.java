package kg.nuratan.springcorepractice_bank.services;

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
    private final UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public Account createAccount(Long userId) {
        Account account = new Account();
        User user = userService.findById(userId);
        account.setUser(user);
        account.setBalance(defaultAmount);
        return accountRepository.save(account);
    }

    public void deposit(Long accountId, Double amount) {
        Account account = accountRepository.getReferenceById(accountId);
        account.setBalance(account.getBalance()+amount);
    }

    public int withdraw(Long accountId, Double amount) {
        Account account = accountRepository.getReferenceById(accountId);
        if(account.getBalance()-amount<0) return -1;
        account.setBalance(account.getBalance()-amount);
        return 0;
    }

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

    public int deleteAccount(Long accountId) {
        if(accountRepository.findById(accountId).get().getUser().getAccounts().size()==1) return -1;
        accountRepository.deleteById(accountId);
        return 0;
    }
}
