package pl.edu.pw.zpoif.exchangeit.exceptions.tradingActions;

public class NegativeQuantityException extends Exception{

    public NegativeQuantityException(String message) {
        super(message);
    }
}
