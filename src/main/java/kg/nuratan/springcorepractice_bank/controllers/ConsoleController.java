package kg.nuratan.springcorepractice_bank.controllers;

import kg.nuratan.springcorepractice_bank.entities.User;
import kg.nuratan.springcorepractice_bank.services.AccountService;
import kg.nuratan.springcorepractice_bank.services.UserService;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleController {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public ConsoleController(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    public void Listen(){
        while(true){
            System.out.println("Enter command: ");
            String command = scanner.nextLine();
            switch (command) {
                case "USER_CREATE":
                    createUser(); break;
                case "SHOW_ALL_USERS":
                    showAllUsers(); break;
                case "ACCOUNT_CREATE":
                    accountCreate(); break;
                case "ACCOUNT_CLOSE":
                    accountClose(); break;
                case "ACCOUNT_DEPOSIT":
                    accountDeposit(); break;
                case "ACCOUNT_TRANSFER":
                    accountTransfer(); break;
                case "ACCOUNT_WITHDRAW":
                    accountWithdraw(); break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    public void createUser(){
        System.out.println("Enter login: ");
        String login = scanner.nextLine();
        System.out.println("User created: " + userService.create(login));
    }

    public void showAllUsers(){
        List<User> users = userService.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    public void accountCreate(){
        System.out.println("Enter userId: ");
        String userId = scanner.nextLine();
        accountService.createAccount(userService.findById(Long.parseLong(userId)));
    }

    public void accountClose(){
        System.out.println("Enter accountId: ");
        String accountId = scanner.nextLine();
        accountService.deleteAccount(Long.parseLong(accountId));
    }

    public void accountDeposit(){
        System.out.println("Enter accountId: ");
        String accountId = scanner.nextLine();
        System.out.println("Enter amount: ");
        String amount = scanner.nextLine();
        accountService.deposit(Long.parseLong(accountId), Double.parseDouble(amount));
    }

    public void accountTransfer(){
        System.out.println("Enter from accountId: ");
        Long fromId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter to accountId: ");
        Long toId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount: ");
        Double amount = Double.parseDouble(scanner.nextLine());
        accountService.transferMoney(fromId, toId, amount);
    }

    private void accountWithdraw(){
        System.out.println("Enter accountId: ");
        Long accountId = Long.parseLong(scanner.nextLine());
        System.out.println("Enter amount: ");
        Double amount = Double.parseDouble(scanner.nextLine());
        accountService.withdraw(accountId, amount);
    }
}
