package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private int total_rows;
    private int total_columns;
    List<SeatInfo> available_seats;




    @JsonIgnore
    List<BookedSeat> bookedSeatList;
    public List<BookedSeat> getBookedSeatList() {
        return bookedSeatList;
    }
    public void setBookedSeatList(List<BookedSeat> bookedSeatList) {
        this.bookedSeatList = bookedSeatList;
    }

    public Cinema(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = new ArrayList<>();
        for (int i = 1; i <= total_rows; i++) {
            for (int j = 1; j <= total_columns; j++) {
                SeatInfo seatInfo = new SeatInfo(i, j, i <=4 ? 10 : 8);
                available_seats.add(seatInfo);
            }
        }
        this.bookedSeatList = new ArrayList<>();
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<SeatInfo> getAvailable_seats() {
        return available_seats;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public void setAvailable_seats(List<SeatInfo> available_seats) {
        this.available_seats = available_seats;
    }
}