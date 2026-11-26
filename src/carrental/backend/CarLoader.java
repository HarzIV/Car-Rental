package carrental.backend;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public class CarLoader {
    private static class CarList {
        Car[] cars;
    }

    /**
     * @param file
     * @return Car[]
     * @throws IOException
     * 
     * This method loads cars from the cars.json and transforms them into an Array of cars
     */

    public static Car[] load(String file) throws IOException {
        try (Reader reader = Files.newBufferedReader(Path.of(file))) {
            return new Gson().fromJson(reader, CarList.class).cars;
        }
    }
}