package carrental.backend;

/**
 * Handels all user related thing, for the currently logged in user.
 */
public class User {
    enum Role {
        CLIENT,
        EMPLOYEE,
        ADMIN
    };

    Role role = Role.ADMIN;
    String userName = "67";
    int userID = 0;
}
