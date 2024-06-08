package kg.nuratan.springcorepractice_bank.controllers;

import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@Bean
public class ConsoleController {
    public ConsoleController() {}
    public void Listen(){
        System.out.println("Enter command: ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
    }
}
