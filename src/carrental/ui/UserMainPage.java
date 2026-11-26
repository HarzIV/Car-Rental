package carrental.ui;

import java.io.Console;

import carrental.backend.User;
import carrental.backend.json.UserRepository;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class UserMainPage {
    public static void frontpage() throws Exception {
        String[] options = { "Auto mieten", "Auto zurückgeben", "Beenden" };
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

                for (int i = 0; i < options.length; i++) {
                    if (i == selected) {
                        g.putString(2, i + 1, "> " + options[i], SGR.REVERSE);
                    } else {
                        g.putString(2, i + 1, "  " + options[i]);
                    }
                }
                screen.refresh();

                KeyStroke key = screen.readInput(); // wartet auf Taste
                KeyType type = key.getKeyType();

                if (type == KeyType.ArrowUp) {
                    selected = (selected - 1 + options.length) % options.length;
                } else if (type == KeyType.ArrowDown) {
                    selected = (selected + 1) % options.length;
                } else if (type == KeyType.Enter) {
                    if (selected == options.length - 1)
                        break;
                    // hier die jeweilige Aktion aufrufen
                } else if (type == KeyType.Escape || type == KeyType.EOF) {
                    break;
                }
            }
        }
    }
}
