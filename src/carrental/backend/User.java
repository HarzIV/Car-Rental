package carrental.backend;

/**
 * Handels all user related thing, for the currently logged in user.
 */
public class User {
    public enum Role {
        CLIENT,
        EMPLOYEE,
        ADMIN;

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder(name().toLowerCase());
            result.setCharAt(0, Character.toUpperCase(name().charAt(0)));

            return result.toString();
        }
    };

    public Role role = Role.ADMIN;
    public String userName = "67";
    public int userID = 0;
}
