import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class TestCreateCourier {
    private final CourierManager cm = new CourierManager();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void testCorrectCreatingCourier() {
        String login = "testCorrectCreatingCourier";
        String password = "testCorrectCreatingCourier";
        String name = "testCorrectCreatingCourier";

        CreateCourier courierCreation = new CreateCourier(login, password, name);
        LoginCourier courierLogin = new LoginCourier(login, password);

        //create courier right way
        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        //check if courier is created
        String jsonString = cm.loginCourier(courierLogin).getBody().asString();
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);

        //delete courier for next tests
        cm.deleteCurrentCourier(courierId);

    }

    @Test
    public void testCreatingCourierWithEmptyLogin() {
        String password = "testCreatingCourierWithEmptyLogin";
        String name = "testCreatingCourierWithEmptyLogin";

        CreateCourier courierCreation = new CreateCourier("", password, name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    public void testCreatingCourierWithEmptyPassword() {
        String login = "testCreatingCourierWithEmptyPassword";
        String name = "testCreatingCourierWithEmptyPassword";

        CreateCourier courierCreation = new CreateCourier(login, "", name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void testCreatingCourierWithEmptyLoginAndPassword() {
        String name = "testCreatingCourierWithEmptyLoginAndPassword";

        CreateCourier courierCreation = new CreateCourier("", "", name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void testCreatingCourierWithEmptyName() {
        String login = "testCreatingCourierWithEmptyName";
        String password = "testCreatingCourierWithEmptyName";

        CreateCourier courierCreation = new CreateCourier(login, password, "");
        LoginCourier courierLogin = new LoginCourier(login, password);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        //check if courier is created
        resp = cm.loginCourier(courierLogin);
        String jsonString = resp.getBody().asString();
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);

        //delete courier for next tests
        cm.deleteCurrentCourier(courierId);
    }

    @Test
    public void testCreatingCourierWithEmptyLoginAndPasswordAndName() {
        CreateCourier courierCreation = new CreateCourier("", "", "");

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void testCreatingSameCourier() {
        String login = "testCreatingSameCourier";
        String password = "testCreatingSameCourier";
        String name = "testCreatingSameCourier";

        CreateCourier courierCreation = new CreateCourier(login, password, name);
        LoginCourier courierLogin = new LoginCourier(login, password);

        //create courier right way, first time
        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        //check if courier is created
        resp = cm.loginCourier(courierLogin);
        String jsonString = resp.getBody().asString();
        Gson gson = new Gson();
        CourierId courierId = gson.fromJson(jsonString, CourierId.class);

        //create courier right way, second time
        resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        //delete courier for next tests
        cm.deleteCurrentCourier(courierId);
    }
}
