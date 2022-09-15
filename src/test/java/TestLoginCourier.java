import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class TestLoginCourier {

    private final CourierManager cm = new CourierManager();
    private final WebManager webManager = new WebManager();

    @Before
    public void setUp() {
        RestAssured.baseURI = webManager.getBaseURI();
    }

    @Test
    @DisplayName("Login courier with correct data")
    public void testCorrectLogin() {
        String login = cm.getLogin();
        String password = cm.getPassword();
        String name = cm.getName();

        //create courier
        cm.courierCreation(new CreateCourier(login, password, name));

        //get response after login courier
        Response resp = cm.loginCourier(new LoginCourier(login, password));

        //delete courier by ID for next tests
        cm.deleteCurrentCourier(resp);

        //check if courier is created and has ID
        resp.then().statusCode(200)
                .assertThat().body("id", Matchers.instanceOf(Integer.class));
    }


    @Test
    @DisplayName("Login courier with empty login")
    public void testLoginCourierWithEmptyLogin() {
        String password = cm.getPassword();

        //check that courier doesn't exist in databases
        Response resp = cm.loginCourier(new LoginCourier("", password));
        resp.then().statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier with empty password")
    public void testLoginCourierWithEmptyPassword() {
        String login = cm.getLogin();

        //check that courier doesn't exist in databases
        Response resp = cm.loginCourier(new LoginCourier(login, ""));
        resp.then().statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier with empty login&password")
    public void testLoginCourierWithEmptyLoginAndPassword() {

        //check that courier doesn't exist in databases
        Response resp = cm.loginCourier(new LoginCourier("", ""));
        resp.then().statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Login courier with wrong password")
    public void testLoginCourierWithWrongPassword() {
        String login = cm.getLogin();
        String password = cm.getPassword();
        String name = cm.getName();

        //create new courier
        cm.courierCreation(new CreateCourier(login, password, name));

        //sending wrong password
        Response resp = cm.loginCourier(new LoginCourier(login, "WrongPassword"));

        //delete right courier for next tests
        cm.deleteCurrentCourier(cm.loginCourier(new LoginCourier(login, password)));

        resp.then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with wrong login&password")
    public void testLoginCourierWithNonExistingLoginAndPassword() {
        Response resp = cm.loginCourier(new LoginCourier("WrongLogin", "WrongPassword"));
        resp.then().statusCode(404)
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}
