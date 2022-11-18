package one.digitalinnovation.parking.controller;

import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    //@Autowired
    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    //ou injetar a dependência por construtor no controller
    //Maneira de injeção de dependência é a melhor nas últimas versões do spring
    public ParkingController(ParkingService parkingService, ParkingMapper markingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = markingMapper;
    }

    @GetMapping
    public List<ParkingDTO> findAll() {

        /**var parking = new Parking();
        parking.setColor("PRETO");
        parking.setLicense("MSS-1111");
        parking.setModel("VW GOL");
        parking.setState("SP");

        return Arrays.asList(parking);
         */

        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return result;
    }
}
