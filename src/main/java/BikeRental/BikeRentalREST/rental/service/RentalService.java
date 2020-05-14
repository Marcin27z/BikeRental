package BikeRental.BikeRentalREST.rental.service;

import BikeRental.BikeRentalREST.rental.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    List<Rental> getRentals();
    List<Rental> getRentalsByUserId(Long userId);
    void addNewRental(Rental rental);
    Optional<Rental> getOpenRentalByUserId(Long userId);
    boolean endRental(Long userId);
}
