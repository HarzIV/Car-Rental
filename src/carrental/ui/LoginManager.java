package carrental.ui;

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import carrental.backend.User;
import carrental.backend.Hash;

public class LoginManager {
    private static final String USERS_JSON_PATH = "data/users";

    public static User login() {
        Console console = System.console();

        System.out.print("Username: ");
        String userName = console.readLine();

        System.out.print("Password: ");
        String password = new String(console.readPassword());

        getUser(userName, password);

        if (getUser(userName, password) != null) {
            System.out.println("User found!");
        }

        return null;
    }

    public static void createUser(String userName, String password, User.Role role) {
        String hashedPassword = Hash.sha256(password);

        JsonObject newUser = new JsonObject();
        newUser.addProperty("userName", userName);
        newUser.addProperty("hashedPassword", hashedPassword);
        newUser.addProperty("role", role.toString());
        newUser.addProperty("userID", getNextFreeID());

        try {
            Path path = Path.of(USERS_JSON_PATH);
            // reading the JSON file
            String jsonContent = Files.readString(path);

            // parse
            JsonObject root = JsonParser.parseString(jsonContent).getAsJsonObject();
            
            JsonArray userArray = root.getAsJsonArray("users");

            userArray.add(newUser);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String updatedJson = gson.toJson(root);

            Files.writeString(path, updatedJson);
        }
        catch (Exception e) {
            System.err.println("Json file containing the users is either not correctly formatted or cannot be found.");
        }
    }

    private static int getNextFreeID() {
        try {
            // reading the JSON file
            String jsonContent = Files.readString(Path.of(USERS_JSON_PATH));

            // parse
            JsonObject root = JsonParser.parseString(jsonContent).getAsJsonObject();
            
            JsonArray userArray = root.getAsJsonArray("users");
            
            return userArray.size();
        }
        catch (Exception e) {
            System.err.println("Json file containing the users is either not correctly formatted or cannot be found.");
        }

        return 0;
    }

    public static User getUser(String userName, String password) {
        String hashedPassword = Hash.sha256(password);

        User result = null;

        try {
            // reading the JSON file
            String jsonContent = Files.readString(Path.of(USERS_JSON_PATH));

            // parse
            JsonObject root = JsonParser.parseString(jsonContent).getAsJsonObject();
            
            JsonArray userArray = root.getAsJsonArray("users");

            for (JsonElement element : userArray) {
                JsonObject user = element.getAsJsonObject();

                String currentUserName = user.get("userName").getAsString();
                if (!currentUserName.equals(userName)) {
                    // current user is not the one
                    continue;
                }
                    
                // compare hashed password
                String currentHashedPassword = user.get("hashedPassword").getAsString();
                if (!currentHashedPassword.equals(hashedPassword)) {
                    // wrong password
                    break;
                }

                // assign the user
                result = new User();
                result.userID = user.get("userID").getAsInt();
                result.userName = userName;
                        
                String role = user.get("role").toString();
                result.role = switch(role) {
                    case "Admin" -> User.Role.ADMIN;
                    case "Employee" -> User.Role.EMPLOYEE;
                    case "Client" -> User.Role.CLIENT;
                    default -> User.Role.CLIENT;
                };
            }
        }
        catch (Exception e) {
            System.err.println("Json file containing the users is either not correctly formatted or cannot be found.");
        }

        return result;
    }
}
