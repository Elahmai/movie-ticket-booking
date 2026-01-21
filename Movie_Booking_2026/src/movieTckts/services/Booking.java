package movieTckts.services;

import movieTckts.models.*;
import movieTckts.interfaces.PaymentMethod;
import java.util.List;

public class Booking {
    private User user;
    private Movie movie;
    private List<Seat> selectedSeats;

    public Booking(User user, Movie movie, List<Seat> selectedSeats) {
        this.user = user;
        this.movie = movie;
        this.selectedSeats = selectedSeats;
    }

    public boolean processBooking(PaymentMethod method, double pricePerTicket) {
        
        if (selectedSeats == null || selectedSeats.isEmpty()) {
            System.out.println("❌ Error: No seats selected.");
            return false;
        }

        
        for (Seat s : selectedSeats) {
            if (s.isReserved()) {
                System.out.println("❌ Error: Seat " + s.getLabel() + " is already taken.");
                return false;
            }
        }

        
        double total = selectedSeats.size() * pricePerTicket;
        if (method.process(total)) {
            
            for (Seat s : selectedSeats) {
                s.reserve(); 
            }
            displayConfirmation(total, method.getMethodName());
            return true;
        }

        System.out.println("❌ Payment failed. Booking aborted.");
        return false;
    }

    private void displayConfirmation(double total, String methodName) {
        System.out.println("\n===============================");
        System.out.println("✅ BOOKING SUCCESSFUL!");
        System.out.println("Customer: " + user.getName());
        System.out.println("Movie: " + movie.getTitle());
        System.out.println("Total Paid: Kshs " + total + " via " + methodName);
        System.out.println("===============================");
    }
}