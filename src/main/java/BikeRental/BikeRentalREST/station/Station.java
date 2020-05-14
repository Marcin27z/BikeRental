package BikeRental.BikeRentalREST.station;

import BikeRental.BikeRentalREST.bike.Bike;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "Stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stationId;

    @NotBlank(message = "Address is required!")
    private String address;

    @OneToMany(mappedBy = "station")
    @JsonIgnore
    private List<Bike> bikeList;
    boolean deleted;

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
