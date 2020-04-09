package pw.eiti.apsi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stationId;

    private String address;

    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
    private List<Bike> bikeList;
}
