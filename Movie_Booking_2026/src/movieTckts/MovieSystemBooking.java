package movieTckts;

import movieTckts.models.*;
import movieTckts.services.*;
import movieTckts.interfaces.PaymentMethod;
import movieTckts.database.UserDAO; // Ensure this is imported
import java.util.*;

public class MovieSystemBooking {
    private static Scanner scanner = new Scanner(System.in);
    private static Theatre theater;
    private static List<Movie> movieCatalog = new ArrayList<>();
    // Note: userDatabase list is kept for compatibility but is no longer the primary source
    private static List<User> userDatabase = new ArrayList<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        initializeData();
        
        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else {
                runMainMenu();
            }
        }
    }

    private static void initializeData() {
        theater = new Theatre("Century Cinemax", "Sarit Centre", 5, 10);
        
        movieCatalog.add(new Movie("Inception", "Sci-Fi", 148, "English", 8.5));
        movieCatalog.add(new Movie("The Dark Knight", "Action", 152, "English", 9.0));
        movieCatalog.add(new Movie("Moana 2", "Animation", 100, "English", 7.8));
        movieCatalog.add(new Movie("Gladiator II", "Epic/Action", 150, "English", 8.2));

        // Testing data - still works for local testing
        userDatabase.add(new User("Test User", "test@mail.com", "0700111222", "1234"));
    }

    private static void showAuthMenu() {
        System.out.println("\n--- MOVIE SYSTEM GATE ---");
        System.out.println("1. Register\n2. Login\n3. Exit");
        System.out.print("Select: ");
        int choice = getIntInput();

        switch (choice) {
            case 1 -> handleRegistration();
            case 2 -> handleLogin();
            case 3 -> System.exit(0);
        }
    }

    private static void runMainMenu() {
        System.out.println("\nHi, " + currentUser.getName() + "! What would you like to do?");
        System.out.println("1. View Available Movies");
        System.out.println("2. Book a Ticket");
        System.out.println("3. Logout");
        System.out.print("Choice: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1 -> displayMovies();
            case 2 -> startBookingFlow();
            case 3 -> currentUser = null;
        }
    }

    private static void displayMovies() {
        System.out.println("\n--- CURRENTLY SHOWING ---");
        for (int i = 0; i < movieCatalog.size(); i++) {
            Movie m = movieCatalog.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " (" + m.getDuration() + " mins)");
        }
    }

    private static void startBookingFlow() {
        displayMovies();
        System.out.print("Select Movie Number: ");
        int movieChoice = getIntInput() - 1;

        if (movieChoice < 0 || movieChoice >= movieCatalog.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        Movie selectedMovie = movieCatalog.get(movieChoice);

        theater.displayLayout();
        System.out.print("\nEnter Row (A-E): ");
        char row = scanner.next().toUpperCase().charAt(0);
        System.out.print("Enter Seat Number (1-10): ");
        int seatNum = getIntInput();

        Seat selectedSeat = theater.getSeat(row, seatNum);
        if (selectedSeat == null || selectedSeat.isReserved()) {
            System.out.println("❌ Seat unavailable or invalid.");
            return;
        }

        System.out.println("\n--- PAYMENT OPTIONS ---");
        System.out.println("1. Bank Card (Visa/Mastercard)");
        System.out.println("2. Mobile Money (M-Pesa/Airtel)");
        System.out.print("Select Method: ");
        int payChoice = getIntInput();

        PaymentMethod paymentMethod;
        if (payChoice == 1) {
            paymentMethod = new CardPayment();
        } else if (payChoice == 2) {
            paymentMethod = new MobileBankingPayment();
        } else {
            System.out.println("Invalid payment option.");
            return;
        }

        List<Seat> seats = new ArrayList<>();
        seats.add(selectedSeat);
        
        Booking booking = new Booking(currentUser, selectedMovie, seats);
        booking.processBooking(paymentMethod, 800.0);
    }

    // --- DATABASE ADJUSTMENTS HERE ---

    private static void handleRegistration() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Phone: "); String phone = scanner.nextLine();
        System.out.print("Password: "); String pass = scanner.nextLine();
        
        User newUser = new User(name, email, phone, pass);
        
        // Change: Save to DB via DAO
        if (UserDAO.saveUser(newUser)) {
            userDatabase.add(newUser); // Optional: keep local list in sync
            System.out.println("✅ Registered in Database! Please Login.");
        } else {
            System.out.println("❌ Registration failed in database.");
        }
    }

    private static void handleLogin() {
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Password: "); String pass = scanner.nextLine();
        
        // Change: Authenticate via Database DAO
        User userFromDb = UserDAO.loginUser(email, pass);
        
        if (userFromDb != null) {
            currentUser = userFromDb;
            System.out.println("✅ Login Successful from Database!");
        } else {
            System.out.println("❌ Login Failed. Invalid database credentials.");
        }
    }

    private static int getIntInput() {
        try {
            int val = scanner.nextInt();
            scanner.nextLine(); 
            return val;
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}