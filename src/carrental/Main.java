package carrental;

import carrental.backend.CarList;
import carrental.ui.LoginManager;
import carrental.ui.UserCarOverview;
import carrental.ui.UserMainPage;
import carrental.backend.CarLoader;
import carrental.ui.UserCarOverview;;

public class Main {
    public static void main(String[] args) throws Exception {
        UserCarOverview.ListCars(CarLoader.load("data/cars.json"));
    }
}
