package carrental.ui;

import java.io.Console;

import carrental.backend.User;
import carrental.backend.json.UserRepository;

public class LoginManager {
    public static User login() {
        Console console = System.console();

        System.out.print("Username: ");
        String userName = console.readLine();

        System.out.print("Password: ");
        String password = new String(console.readPassword());

        User user = UserRepository.getUser(userName, password);
        if (user == null) {
            System.err.println("Authentication failed.");
        }

        return user;
    }

    public static void signup() {
        
    }
}
