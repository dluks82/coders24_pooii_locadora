package exceptions;

public class DataInputInterruptedException extends RuntimeException {
    public DataInputInterruptedException() {
        super("Data input interrupted by the user.");
    }
}
