package kg.nuratan.springcorepractice_bank.services;

import jakarta.transaction.Transactional;
import kg.nuratan.springcorepractice_bank.entities.Account;
import kg.nuratan.springcorepractice_bank.entities.User;
import kg.nuratan.springcorepractice_bank.repositories.UserRepository;
import org.hibernate.Hibernate;
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

    @Transactional
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user!=null) Hibernate.initialize(user.getAccounts());
        return user;
    }
    @Transactional
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    @Transactional
    public User create(String login) {
        if(userRepository.findByLogin(login)!=null) return null;
        User user = new User();
        user.setLogin(login);
        List<Account> accounts = new ArrayList<>();
        user.setAccounts(accounts);
        user = userRepository.save(user);
        Account defaultAccount = accountService.createAccount(user);
        user.addAccount(defaultAccount);
        return userRepository.save(user);
    }
    @Transactional
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        for(User user : users) {Hibernate.initialize(user.getAccounts());}
        return users;
    }
}
