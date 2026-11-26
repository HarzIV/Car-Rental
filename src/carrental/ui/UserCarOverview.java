package carrental.ui;

import java.io.Console;

import carrental.backend.User;
import carrental.backend.json.UserRepository;
import carrental.backend.Car;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class UserCarOverview {
    public static boolean ListCars(Car[] carList) throws Exception{
        int selected = 0;

        try (Screen screen = new DefaultTerminalFactory()
                .setForceTextTerminal(true)
                .createScreen()) {
            screen.startScreen();
            screen.setCursorPosition(null); // Cursor ausblenden

            while (true) {
                screen.doResizeIfNecessary();
                screen.clear();
                TextGraphics g = screen.newTextGraphics();

                for (int i = 0; i < carList.length; i++) {
                    if (i == selected) {
                        g.putString(2, i + 1, "> " + carList[i].name, SGR.REVERSE);
                    } else {
                        g.putString(2, i + 1, "  " + carList[i].name);
                    }
                }
                screen.refresh();

                KeyStroke key = screen.readInput(); // wartet auf Taste
                KeyType type = key.getKeyType();

                if (type == KeyType.ArrowUp) {
                    selected = (selected - 1 + carList.length) % carList.length;
                } else if (type == KeyType.ArrowDown) {
                    selected = (selected + 1) % carList.length;
                } else if (type == KeyType.Enter) {
                    if (selected == carList.length - 1)
                        break;
                    // hier die jeweilige Aktion aufrufen
                } else if (type == KeyType.Escape || type == KeyType.EOF) {
                    break;
                }
            }
        }

        return true;
    }
}
