package movieTckts.interfaces;

public interface PaymentMethod {
    boolean process(double amount);
    String getMethodName();
}
