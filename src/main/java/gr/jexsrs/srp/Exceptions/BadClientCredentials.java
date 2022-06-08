package gr.jexsrs.srp.Exceptions;

public class BadClientCredentials extends Exception{
    public BadClientCredentials(String message) {
        super(message);
    }
}
