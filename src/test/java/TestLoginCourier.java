import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class TestLoginCourier {

    private final CourierManager cm = new CourierManager();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void testCorrectLogin() {
        String login = "testCorrectLogin";
        String password = "testCorrectLogin";
        String name = "testCorrectLogin";

        CreateCourier courierCreation = new CreateCourier(login, password, name);
        LoginCourier courierLogin = new LoginCourier(login, password);

        //create courier
        cm.courierCreation(courierCreation);

        //check if courier is created
        Response resp = cm.loginCourier(courierLogin);
        resp.then().statusCode(200);
        String jsonString = resp.getBody().asString();
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);

        //check if courierId is not empty or null
        MatcherAssert.assertThat(courierId.getId(), !courierId.getId().isEmpty());

        //delete courier by ID for next tests
        cm.deleteCurrentCourier(courierId);
    }


    @Test
    public void testLoginCourierWithEmptyLogin() {
        String password = "testLoginCourierWithEmptyLogin";

        LoginCourier courierLogin = new LoginCourier("", password);

        //check that courier doesn't exist in databases
        Response resp = cm.loginCourier(courierLogin);
        resp.then().statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void testLoginCourierWithEmptyPassword() {
        String login = "testLoginCourierWithEmptyPassword";

        LoginCourier courierLogin = new LoginCourier(login, "");

        //check that courier doesn't exist in databases
        Response resp = cm.loginCourier(courierLogin);
        resp.then().statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void testLoginCourierWithEmptyLoginAndPassword() {
        LoginCourier courierLogin = new LoginCourier("", "");

        //check that courier doesn't exist in databases
        Response resp = cm.loginCourier(courierLogin);
        resp.then().statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void testLoginCourierWithWrongLogin() {
        String login = "testLoginCourierWithWrongLogin";
        String password = "testLoginCourierWithWrongLogin";
        String name = "testLoginCourierWithWrongLogin";

        CreateCourier courierCreation = new CreateCourier(login, password, name);
        cm.courierCreation(courierCreation);
        LoginCourier rightCourierLogin = new LoginCourier(login, password);

        //sending wrong login
        LoginCourier wrongCourierLogin = new LoginCourier("WrongLogin", password);

        Response resp = cm.loginCourier(wrongCourierLogin);
        resp.then().statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

        //delete for next tests
        resp = cm.loginCourier(rightCourierLogin);
        resp.then().statusCode(200);
        String jsonString = resp.getBody().asString();
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);
        cm.deleteCurrentCourier(courierId);
    }

    @Test
    public void testLoginCourierWithWrongPassword() {
        String login = "testLoginCourierWithWrongPassword";
        String password = "testLoginCourierWithWrongPassword";
        String name = "testLoginCourierWithWrongPassword";

        CreateCourier courierCreation = new CreateCourier(login, password, name);
        cm.courierCreation(courierCreation);
        LoginCourier rightCourierLogin = new LoginCourier(login, password);

        //sending wrong password
        LoginCourier wrongCourierLogin = new LoginCourier(login, "WrongPassword");

        Response resp = cm.loginCourier(wrongCourierLogin);
        resp.then().statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

        //delete for next tests
        resp = cm.loginCourier(rightCourierLogin);
        resp.then().statusCode(200);
        String jsonString = resp.getBody().asString();
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);
        cm.deleteCurrentCourier(courierId);
    }

    @Test
    public void testLoginCourierWithNonExistingLoginAndPassword() {
        String login = "testLoginCourierWithNonExistingLoginAndPassword";
        String password = "testLoginCourierWithNonExistingLoginAndPassword";

        LoginCourier courierLogin = new LoginCourier(login, password);

        Response resp = cm.loginCourier(courierLogin);
        resp.then().statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }


}
