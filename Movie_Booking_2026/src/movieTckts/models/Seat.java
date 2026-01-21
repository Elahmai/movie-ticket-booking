package movieTckts.models;

public class Seat {
    private char row;
    private int number;
    private boolean isReserved;

    public Seat(char row, int number) {
        this.row = row;
        this.number = number;
        this.isReserved = false;
    }

    public boolean reserve() {
        if (isReserved) return false;
        isReserved = true;
        return true;
    }

    public String getLabel() { return "" + row + number; }
    public boolean isReserved() { return isReserved; }
}