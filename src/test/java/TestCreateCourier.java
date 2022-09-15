import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class TestCreateCourier {
    private final CourierManager cm = new CourierManager();
    private final WebManager webManager = new WebManager();

    @Before
    public void setUp() {
        RestAssured.baseURI = webManager.getBaseURI();
    }

    @Test
    @DisplayName("Create courier with correct data")
    public void testCorrectCreatingCourier() {

        String login = cm.getLogin();
        String password = cm.getPassword();
        String name = cm.getName();

        //create courier right way
        Response resp = cm.courierCreation(new CreateCourier(login, password, name));

        //delete courier for next tests
        cm.deleteCurrentCourier(cm.loginCourier(new LoginCourier(login, password)));

        //chek if there are no mistakes and statusCode is 201
        resp.then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier with empty login")
    public void testCreatingCourierWithEmptyLogin() {
        String password = cm.getPassword();
        String name = cm.getName();

        CreateCourier courierCreation = new CreateCourier("", password, name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier with null login")
    public void testCreatingCourierWithNullLogin() {
        String password = cm.getPassword();
        String name = cm.getName();

        CreateCourier courierCreation = new CreateCourier(null, password, name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier with empty password")
    public void testCreatingCourierWithEmptyPassword() {
        String login = cm.getLogin();
        String name = cm.getName();

        CreateCourier courierCreation = new CreateCourier(login, "", name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier with null password")
    public void testCreatingCourierWithNulPassword() {
        String login = cm.getLogin();
        String name = cm.getName();

        CreateCourier courierCreation = new CreateCourier(login, null, name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier with empty login&password")
    public void testCreatingCourierWithEmptyLoginAndPassword() {
        String name = cm.getName();

        CreateCourier courierCreation = new CreateCourier("", "", name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier with null login&password")
    public void testCreatingCourierWithNullLoginAndPassword() {
        String name = cm.getName();

        CreateCourier courierCreation = new CreateCourier(null, null, name);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier with empty name")
    public void testCreatingCourierWithEmptyName() {
        String login = cm.getLogin();
        String password = cm.getPassword();

        //create courier and get responce of the creation
        Response resp = cm.courierCreation(new CreateCourier(login, password, ""));

        //delete courier for next tests
        cm.deleteCurrentCourier(cm.loginCourier(new LoginCourier(login, password)));

        //read received response
        resp.then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier with null name")
    public void testCreatingCourierWithNullName() {
        String login = cm.getLogin();
        String password = cm.getPassword();

        //get response after courier creation
        Response resp = cm.courierCreation(new CreateCourier(login, password, null));

        //delete courier for next tests
        cm.deleteCurrentCourier(cm.loginCourier(new LoginCourier(login, password)));

        //read response
        resp.then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Create courier with empty data")
    public void testCreatingCourierWithEmptyLoginAndPasswordAndName() {
        CreateCourier courierCreation = new CreateCourier("", "", "");

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create courier with all null data")
    public void testCreatingCourierWithNullLoginAndPasswordAndName() {
        CreateCourier courierCreation = new CreateCourier(null, null, null);

        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(400)
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Create new courier with existing data in DB")
    public void testCreatingSameCourier() {
        String login = cm.getLogin();
        String password = cm.getPassword();
        String name = cm.getName();

        CreateCourier courierCreation = new CreateCourier(login, password, name);
        LoginCourier courierLogin = new LoginCourier(login, password);

        //create courier right way, first time
        Response resp = cm.courierCreation(courierCreation);
        resp.then()
                .statusCode(201)
                .assertThat().body("ok", equalTo(true));

        //create courier right way, second time
        resp = cm.courierCreation(courierCreation);

        //delete courier for next tests
        cm.deleteCurrentCourier(cm.loginCourier(courierLogin));

        resp.then()
                .statusCode(409)
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
