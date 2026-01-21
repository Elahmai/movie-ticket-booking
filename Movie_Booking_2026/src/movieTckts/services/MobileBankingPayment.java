package movieTckts.services;

import movieTckts.interfaces.PaymentMethod;

public class MobileBankingPayment implements PaymentMethod {
    
    @Override
    public boolean process(double amount) {
        
        System.out.println("[Mobile Gateway] Initiating transaction of Kshs " + amount + "...");
        

        System.out.println("✅ Payment Received! Transaction ID: TXN" + System.currentTimeMillis());
        return true; 
    }

    @Override
    public String getMethodName() {
        return "Mobile Banking (M-Pesa/Airtel Money)";
    }
}