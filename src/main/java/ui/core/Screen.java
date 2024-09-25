package ui.core;

import ui.flow.FlowController;

public abstract class Screen {
    protected final FlowController flowController;

    public Screen(FlowController flowController) {
        this.flowController = flowController;
    }

    public abstract void show();
}
