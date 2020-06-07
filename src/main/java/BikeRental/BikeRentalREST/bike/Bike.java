package BikeRental.BikeRentalREST.bike;

import BikeRental.BikeRentalREST.rental.Rental;
import BikeRental.BikeRentalREST.station.Station;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Bikes")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bikeId;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToMany(mappedBy = "bike", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Rental> rentalList;

    @JsonProperty
    public Long getBikeId() {
        return bikeId;
    }

    @JsonIgnore
    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public List<Rental> getRentalList() {
        return rentalList;
    }

    public void setRentalList(List<Rental> rentalList) {
        this.rentalList = rentalList;
    }

    @JsonIgnore
    public boolean isActive(){
        return status != Status.REMOVED;
    }
}
