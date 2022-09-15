import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class TestDeleteCourier {

    private final CourierManager cm = new CourierManager();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void testCorrectDeletingCourier() {
        String login = "testCorrectDeletingCourier";
        String password = "testCorrectDeletingCourier";
        String name = "testCorrectDeletingCourier";

        CreateCourier courierCreation = new CreateCourier(login, password, name);
        LoginCourier courierLogin = new LoginCourier(login, password);

        //create courier
        cm.courierCreation(courierCreation);

        //delete this courier
        String jsonString = cm.loginCourier(courierLogin).getBody().asString();
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);

        cm.deleteCurrentCourier(courierId)
                .then().statusCode(200)
                .assertThat().body("ok", equalTo(true));

        //check that courier was surely deleted and doesn't exist in databases
        Response resp = cm.loginCourier(courierLogin);
        resp.then().statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

    }
}
