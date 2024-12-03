package project.cs3360.socketserver.handler;

public enum Priority {
    LOWEST(0),
    LOW(1),
    NORMAL(2),
    HIGH(3),
    HIGHEST(4);

    private final int slot;

    private Priority(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return this.slot;
    }
}
