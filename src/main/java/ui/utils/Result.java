package ui.utils;

public class Result<T> {
    private final T value;
    private final String errorMessage;

    private Result(T value, String errorMessage) {
        this.value = value;
        this.errorMessage = errorMessage;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> fail(String errorMessage) {
        return new Result<>(null, errorMessage);
    }

    public boolean isFailure() {
        return errorMessage != null;
    }

    public T getValue() {
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
