package movieTckts.models;

public class Theatre {
    private String name;
    private String location;
    private Seat[][] seatGrid;
    private final int rows;
    private final int cols;

    public Theatre(String name, String location, int rows, int cols) {
        this.name = name;
        this.location = location;
        this.rows = rows;
        this.cols = cols;
        this.seatGrid = new Seat[rows][cols];
        initializeSeats();
    }

    
    private void initializeSeats() {
        for (int i = 0; i < rows; i++) {
            char rowLetter = (char) ('A' + i);
            for (int j = 0; j < cols; j++) {
                seatGrid[i][j] = new Seat(rowLetter, j + 1);
            }
        }
    }

    public void displayLayout() {
        System.out.println("\n--- " + name + " (" + location + ") ---");
        System.out.println("Seat Layout: [O] = Available, [X] = Booked");
        
        
        System.out.print("   ");
        for (int i = 1; i <= cols; i++) {
            System.out.printf("%02d ", i);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print((char) ('A' + i) + ": ");
            for (int j = 0; j < cols; j++) {
                System.out.print(seatGrid[i][j].isReserved() ? "[X] " : "[O] ");
            }
            System.out.println();
        }
    }

    public Seat getSeat(char row, int number) {
        int rowIndex = Character.toUpperCase(row) - 'A';
        int colIndex = number - 1;

        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
            return seatGrid[rowIndex][colIndex];
        }
        return null; 
    }

    public String getName() { return name; }
}
