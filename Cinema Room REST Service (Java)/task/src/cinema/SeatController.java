package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
class Token {
    public Token() {
    }

    UUID token;

    public Token(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

}
@RestController
public class SeatController {
    Cinema cinema = new Cinema(9, 9);

    @GetMapping("/seats")
    public Cinema getSeats() {

        return cinema;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> bookTicket(@RequestBody Seat seat) {
        int row = seat.getRow();
        int col = seat.getColumn();

        if (row < 1 || row > cinema.getTotal_rows() || col < 1 || col > cinema.getTotal_columns()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < cinema.getAvailable_seats().size(); i++) {
            SeatInfo availableSeat = cinema.getAvailable_seats().get(i);

            if (availableSeat.equals(seat)) {
                BookedSeat bookedSeat = new BookedSeat(UUID.randomUUID(), availableSeat);
                cinema.getBookedSeatList().add(bookedSeat);
                cinema.getAvailable_seats().remove(i);
                return new ResponseEntity<>(bookedSeat, HttpStatus.OK);
            }
        }

//        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ticket has been already purchased!");
        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Token token) {
        List<BookedSeat> bookedSeatList = cinema.getBookedSeatList();

        for (BookedSeat bookedSeat : bookedSeatList) {
            if (bookedSeat.getToken().equals(token.getToken())) {
                bookedSeatList.remove(bookedSeat);
                cinema.getAvailable_seats().add(bookedSeat.getTicket());
                return new ResponseEntity<>(Map.of("returned_ticket", bookedSeat.getTicket()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStatistics(@RequestParam(required = false) String password) {
        if (password != null && password.equals("super_secret")) {
            Map<String, Integer> statistics = new HashMap<>();

            int currentIncome = 0;

            for (BookedSeat bookedSeat : cinema.getBookedSeatList()) {
                currentIncome += bookedSeat.getTicket().getPrice();
            }

            int numberOfAvailableSeats = cinema.getAvailable_seats().size();
            int numberOfPurchasedTickets = cinema.getBookedSeatList().size();

            statistics.put("current_income", currentIncome);
            statistics.put("number_of_available_seats", numberOfAvailableSeats);
            statistics.put("number_of_purchased_tickets", numberOfPurchasedTickets);

            return new ResponseEntity<>(statistics, HttpStatus.OK);
        }

        else return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.valueOf(401));
    }

}
