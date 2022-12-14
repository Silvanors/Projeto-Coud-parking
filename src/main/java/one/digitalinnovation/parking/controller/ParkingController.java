package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags = "ParkingController")
public class ParkingController {

    //@Autowired
    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    //ou injetar a dependência por construtor no controller
    //Maneira de injeção de dependência é a melhor nas últimas versões do spring
    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @ApiOperation("Find all parkings")
    public ResponseEntity<List<ParkingDTO>> findAll() {
    //public List<ParkingDTO> findAll() {

        /**var parking = new Parking();
        parking.setColor("PRETO");
        parking.setLicense("MSS-1111");
        parking.setModel("VW GOL");
        parking.setState("SP");

        return Arrays.asList(parking);
         */

        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        //return result;
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        Parking parking = parkingService.findById(id);
        //List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        ParkingDTO result = parkingMapper.ToParkingDTO(parking);
           return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ParkingDTO>  create(@RequestBody ParkingCreateDTO dto) {
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.create(parkingCreate);
        //List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        var result = parkingMapper.ToParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ParkingDTO>  update(@PathVariable String id, @RequestBody ParkingCreateDTO dto) {
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.update(id, parkingCreate);
        //List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        var result = parkingMapper.ToParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping ("/{id}")
    public ResponseEntity<ParkingDTO>  checkout(@PathVariable String id) {
        Parking parking = parkingService.checkout(id);
        return ResponseEntity.ok(parkingMapper.ToParkingDTO(parking));
    }
}
