package pw.eiti.apsi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class BikeRentalServiceImpl implements BikeRentalService {

    // == fields ==

    // == constructors ==
    @Autowired
    public BikeRentalServiceImpl() {
    }

    // == init ==
    @PostConstruct
    public void init(){
        log.info("init service");
    }

    // == public methods ==
}
