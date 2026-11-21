package carrental.backend.json;

import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.FormattingStyle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import carrental.backend.Hash;
import carrental.backend.User;

/**
 * An abtraction layer for working with the users.json
 */ 
public class UserList {
    private static final Path USERS_JSON_PATH = Path.of("data/users.json");
    private static final FormattingStyle JSON_FORMATTING_STYLE = FormattingStyle.PRETTY.withIndent("    ");

    /**
     * Creates a new user in the users.json
     * 
     * @param userName  The username
     * @param password  The password (not hashed)
     * @param role      The role of the new user. This can be {@code User.Role.ADMIN},
     *                  {@code User.Role.EMPLOYEE} or {@code User.Role.CLIENT}
     *
     * @return  Returns {@code true} if the user was successfully created and 
     *          {@code false} if the user already exists or the JSON file was
     *          either not found or wrongly formatted.
     */
    public static boolean create(String userName, String password, User.Role role) {
        String hashedPassword = Hash.sha256(password);

        // creation of the new json user object
        JsonObject newUser = new JsonObject();
        newUser.addProperty("userName", userName);
        newUser.addProperty("hashedPassword", hashedPassword);
        newUser.addProperty("role", role.toString());

        try {
            // reading the JSON file
            String jsonContent = Files.readString(USERS_JSON_PATH);

            // parse
            JsonObject root = JsonParser.parseString(jsonContent).getAsJsonObject();
            JsonArray userArray = root.getAsJsonArray("users");

            for (int i = 0; i < userArray.size(); i++) {
                JsonObject user = userArray.get(i).getAsJsonObject();

                String currentUserName = user.get("userName").getAsString();
                if (currentUserName.equals(userName)) {
                    return false;
                }
            }
            
            userArray.add(newUser);

            Gson gson = new GsonBuilder().setFormattingStyle(JSON_FORMATTING_STYLE).create();

            Files.writeString(USERS_JSON_PATH, gson.toJson(root));
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Searches for a user in the users.json
     * 
     * @param userName  The username
     * @param password  The password (not hashed)
     *
     * @return  Returns a {@code User} object containing the users data if the 
     *          login was successful or {@code null} if the JSON file was
     *          not found, wrongly formatted or the userName or password
     *          was incorrect
     */
    public static User authenticate(String userName, String password) {
        String hashedPassword = Hash.sha256(password);

        User result = null;

        try {
            // reading the JSON file
            String jsonContent = Files.readString(USERS_JSON_PATH);

            // parse
            JsonArray userArray = JsonParser.parseString(jsonContent)
                                            .getAsJsonObject()
                                            .getAsJsonArray("users");

            for (int i = 0; i < userArray.size(); i++) {
                JsonObject user = userArray.get(i).getAsJsonObject();

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
                result.userID = i;
                result.userName = userName;
                        
                String role = user.get("role").toString();
                result.role = User.Role.stringToEnum(role);
            }
        }
        catch (Exception e) {

        }

        return result;
    }

    /**
     * Deletes a user in the users.json
     * 
     * @param id  The users ID
     *
     * @return  Returns {@code true} if the user was successfully removed or {@code false}
     *          if the JSON file was not found, wrongly formatted or the id doesn't exist.
     */
    public static boolean delete(int id) {
        try {
            // reading the JSON file
            String jsonContent = Files.readString(USERS_JSON_PATH);

            // parse
            JsonObject root = JsonParser.parseString(jsonContent).getAsJsonObject();
            JsonArray userArray = root.getAsJsonArray("users");

            if (id < userArray.size()) {
                userArray.remove(id);
                return false;
            }

            Gson gson = new GsonBuilder().setFormattingStyle(JSON_FORMATTING_STYLE).create();
            Files.writeString(USERS_JSON_PATH, gson.toJson(root));
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }
}
