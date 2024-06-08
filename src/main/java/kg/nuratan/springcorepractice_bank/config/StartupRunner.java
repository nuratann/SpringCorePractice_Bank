package kg.nuratan.springcorepractice_bank.config;

import kg.nuratan.springcorepractice_bank.controllers.ConsoleController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    private final ConsoleController consoleController;

    public StartupRunner(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @Override
    public void run(String... args) throws Exception {
        consoleController.Listen();
    }
}
