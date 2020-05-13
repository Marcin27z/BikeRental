package BikeRental.BikeRentalREST.station;

import BikeRental.BikeRentalREST.bike.Bike;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stationId;

    private String address;

    @OneToMany(mappedBy = "station")
    @JsonIgnore
    private List<Bike> bikeList;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId){
        this.stationId = stationId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public List<Bike> getBikeList() {
        return bikeList;
    }
}
