import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
//import io.qameta.allure.Step;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.*;

public class CreatingCourierTestClass {

    private String login = "fastCourier";
    private String password = "faster";
    private String name = "fastCourier";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void testCorrectCreatingCourier(){
        CreateCourier courierCreation = new CreateCourier(login, password, name);
        LoginCourier courierLogin = new LoginCourier(login, password);

        courierCreation(courierCreation);

        String jsonString = loginCourier(courierLogin);
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);
        deleteCurrentCourier(courierId)
                .then().statusCode(200)
                .assertThat().body("ok", equalTo(true));

        loginCourierNonExisting(courierLogin);
    }

    public void courierCreation(CreateCourier courier){
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));
    }

 //   @Step("Deleting current courier through /api/v1/courier/:id")
    public Response deleteCurrentCourier(CourierId courier){
        return given()
                .body(courier)
                .when()
                .delete("/api/v1/courier/{id}", courier.getId());
    }

 //   @Step("Check if courier is in the system and get his ID through /api/v1/courier/login")
    public String loginCourier(LoginCourier courier){
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(200);
        return response.getBody().asString();
    }
    public String loginCourierNonExisting(LoginCourier courier){
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(404);
        return response.getBody().asString();
    }
}
