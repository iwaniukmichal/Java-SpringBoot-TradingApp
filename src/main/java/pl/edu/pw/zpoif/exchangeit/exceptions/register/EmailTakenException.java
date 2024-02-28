package pl.edu.pw.zpoif.exchangeit.exceptions.register;

public class EmailTakenException extends Exception{
    public EmailTakenException(String message) {
        super(message);
    }
}
