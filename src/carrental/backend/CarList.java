package carrental.backend;

import java.util.ArrayList;

/*
   If you dont like this interface, feel free to change it.
   It just works like a array list. Theres only one instance of it.
   Use getInstance() to get it.
*/

public class CarList extends ArrayList<Car> {
    private CarList() {};

    private static CarList singleton;

    public static CarList getInstance() {
        if (singleton == null) {
            singleton = new CarList();
        }

        return singleton;
    }
}
