package pw.eiti.apsi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pw.eiti.apsi.service.BikeRentalService;
import pw.eiti.apsi.util.BikeRentalMappings;
import pw.eiti.apsi.util.ViewNames;

@Slf4j
@Controller
public class WebController {

    // == fields ==
    private final BikeRentalService bikeRentalService;

    // == constructors ==
    @Autowired
    public WebController(BikeRentalService bikeRentalService){
        this.bikeRentalService = bikeRentalService;
    }

    // == request methods ==
    @GetMapping(BikeRentalMappings.HOME)
    public String home(Model model){
        log.info("model = {}", model);
        return ViewNames.HOME;
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "index";
    }
}
