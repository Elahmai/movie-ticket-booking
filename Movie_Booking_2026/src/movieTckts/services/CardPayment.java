package movieTckts.services;
import movieTckts.interfaces.PaymentMethod;

public class CardPayment implements PaymentMethod {
    @Override
    public boolean process(double amount) {
        System.out.println("Processing Kshs " + amount + " via Bank Card...");
        return true; 
    }
    @Override
    public String getMethodName() { return "Bank Card"; }
}