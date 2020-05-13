package BikeRental.BikeRentalREST.rental.service;

import BikeRental.BikeRentalREST.rental.Rental;
import BikeRental.BikeRentalREST.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rentalService")
public class RentalServiceImpl implements RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public List<Rental> getRentals() {
        return this.rentalRepository.findAll();
    }

    @Override
    public List<Rental> getRentalsByUserId(Long userId) {
        return this.rentalRepository.findByUserid(userId);
    }

    @Override
    public void addNewRental(Rental rental) {
        this.rentalRepository.save(rental);
    }
}
