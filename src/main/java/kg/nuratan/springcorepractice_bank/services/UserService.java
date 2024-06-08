package kg.nuratan.springcorepractice_bank.services;

import kg.nuratan.springcorepractice_bank.entities.Account;
import kg.nuratan.springcorepractice_bank.entities.User;
import kg.nuratan.springcorepractice_bank.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User create(String login) {
        if(userRepository.findByLogin(login)!=null) return null;
        User user = new User();
        user.setLogin(login);
        Account defaultAccount = accountService.createAccount(user.getId());
        List<Account> accounts = new ArrayList<>();
        accounts.add(defaultAccount);
        user.setAccounts(accounts);
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
