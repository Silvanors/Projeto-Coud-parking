package one.digitalinnovation.parking.controller;

import io.restassured.RestAssured;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTestIT extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        //System.out.println(randomPort);
        RestAssured.port = randomPort;


    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth()
                .basic("user", "Dio@123456")
                .when()
                .get("/parking")
                .then()
                //.statusCode(200)
                .statusCode(HttpStatus.OK.value());
                //.extract().response().body().prettyPrint(); //para verificar se está retornando algum dado
                //.body("license[0]", Matchers.equalTo("MD-1111"));

    }

    @Test
    void whenCreateThenCheckIsCreared() {

        var createDTO = new ParkingCreateDTO();
        createDTO.setColor("AMARELO");
        createDTO.setLicense("MRT-6666");
        createDTO.setModel("Brasília");
        createDTO.setState("SP");

        RestAssured.given()
                .when()
                .auth().basic("user", "Dio@123456")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                //.statusCode(200)
                .statusCode(HttpStatus.CREATED.value())

                //.extract().response().body().prettyPrint(); //para verificar se está retornando algum dado
                .body("license", Matchers.equalTo("MRT-6666"))
                .body("color", Matchers.equalTo("ROSA"));

    }
}