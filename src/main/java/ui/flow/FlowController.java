package ui.flow;

import ui.core.Screen;

import java.util.ArrayDeque;
import java.util.Deque;

public class FlowController {
    private final Deque<Screen> screenStack;

    public FlowController() {
        screenStack = new ArrayDeque<>();
    }

    public void goTo(Screen screen) {
        if (screen == null) {
            throw new IllegalArgumentException("A tela não pode ser nula.");
        }
        screenStack.push(screen);
        screen.show();
    }

    public void goBack() {
        if (screenStack.size() <= 1) {
            return;
        }

        screenStack.pop();
        Screen previousScreen = screenStack.peek();
        if (previousScreen != null) {
            previousScreen.show();
        }
    }
}
