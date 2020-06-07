package BikeRental.BikeRentalREST.station;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Station SET deleted = true WHERE stationId = :stationId")
    int setStationDeleted(Long stationId);

    Optional<Station> findByAddress(String address);

    List<Station> findAllByDeletedIsFalse();

    @Query(value = "Select s.station_id, s.address, s.deleted from public.bikes b, public.stations s where b.station_id = s.station_id and b.status = 1 and s.deleted = false",
    nativeQuery = true)
    List<Station> findAllWithAvailableBikes();

    @Query(value = "Select s.station_id, s.address, s.deleted from public.bikes b, public.stations s where b.station_id = s.station_id and b.status = 1 and s.deleted = false and s.address = :address",
            nativeQuery = true)
    List<Station> findAllWithAvailableBikesOnAddress(String address);
}

