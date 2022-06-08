package gr.jexsrs.srp.Exceptions;

public class BadServerCredentials extends Exception{
    public BadServerCredentials(String message) {
        super(message);
    }
}
