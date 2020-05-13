package BikeRental.BikeRentalREST.rental.service;

import BikeRental.BikeRentalREST.rental.Rental;

import java.util.List;

public interface RentalService {
    List<Rental> getRentals();
    List<Rental> getRentalsByUserId(Long userId);
    void addNewRental(Rental rental);
}
