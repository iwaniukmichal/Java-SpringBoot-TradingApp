package pl.edu.pw.zpoif.exchangeit.exceptions.AlphaVintageAPI;

public class RequestsNumExceededException extends RuntimeException {
    public RequestsNumExceededException(String message) {
        super(message);
    }
}
