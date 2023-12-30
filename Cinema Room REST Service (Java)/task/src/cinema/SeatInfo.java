package cinema;

public class SeatInfo {
    private int row;
    private int column;
    private int price;
    public SeatInfo(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public boolean equals(Seat seat) {
        if (seat == null) return false;

        if (row != seat.getRow()) return false;
        return column == seat.getColumn();
    }
}
