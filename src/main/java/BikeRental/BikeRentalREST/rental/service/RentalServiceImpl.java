package BikeRental.BikeRentalREST.rental.service;

import BikeRental.BikeRentalREST.rental.Rental;
import BikeRental.BikeRentalREST.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Rental> getOpenRentalByUserId(Long userId) {
        return this.rentalRepository.findOpenRental(userId);
    }

    @Override
    public boolean endRental(Long userId) {
        Optional<Rental> rental = this.rentalRepository.findOpenRental(userId);
        if(rental.isPresent()){
            rental.get().setReturnDate(LocalDateTime.now());
            long minutes = Duration.between(rental.get().getRentalDate(), rental.get().getReturnDate()).toMinutes();
            rental.get().setPrice(new BigDecimal(minutes*60));
            this.rentalRepository.save(rental.get());
            return true;
        }else{
            return false;
        }
    }
}
