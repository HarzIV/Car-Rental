package carrental.backend;

import java.util.ArrayList;

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
