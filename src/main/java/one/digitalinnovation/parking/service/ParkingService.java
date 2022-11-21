package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }
    //private static Map<String, Parking> parkingMap = new HashMap();

    /**
    static {
        var id = getUUID();
        var id1= getUUID();
        Parking parking = new Parking(id, "MD-1111", "SC", "CELTA", "PRETO");
        Parking parking1 = new Parking(id1, "WAS-1234", "SP", "VW GOL", "VERMELHO");
        parkingMap.put(id, parking);
        parkingMap.put(id1, parking1);
    }
     */

@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll(){
        return parkingRepository.findAll();
        //return parkingMap.values().stream().collect(Collectors.toList());

    }


    private static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }
@Transactional(readOnly = true)
    public Parking findById(String id) {
        return parkingRepository.findById(id).orElseThrow(()->
                new ParkingNotFoundException(id));
        /**Parking parking = parkingMap.get(id);
        if(parking == null) {
            throw new ParkingNotFoundException(id);
        }
        //return parkingMap.get(id);
        return parking*/
    }

@Transactional
    public Parking create(Parking parkingCreate) {
    //try
        //open connetion
        //transaction.begin
                String uuid = getUUID();
                parkingCreate.setId(uuid);
                parkingCreate.setEnterDate(LocalDateTime.now());
                //parkingMap.put(uuid, parkingCreate);
                parkingRepository.save(parkingCreate);
        //transaction.commit
    //catch
        //transaction.rolback
        return parkingCreate;
    }
@Transactional
    public void delete(String id) {
        findById(id);
        //parkingMap.remove(id);
        parkingRepository.deleteById(id);
    }
@Transactional
    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setState(parkingCreate.getState());
        parking.setModel(parkingCreate.getModel());
        parking.setLicense(parkingCreate.getLicense());
        parkingRepository.save(parking);
        //parkingMap.replace(id, parking);
        return parking;

    }

    public Parking checkout(String id) {
        //recuperar o estacionamento
        //atualizar a data de saída
        //calcular o valor
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        parkingRepository.save(parking);

        return parking;
    }
}
