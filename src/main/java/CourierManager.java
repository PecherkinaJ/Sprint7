import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class CourierManager {
    private final WebManager webManager = new WebManager();
    private final String[] logins = {"bestLogin", "worstLogin", "justALogin", "noLogin", "testLogin", "funnyLogin", "sadLogin"};
    private final String[] passwords = {"bestPass", "Password", "pAssworD", "YouShallNotPass", "boringpassword", "123456798", "qwerty"};
    private final String[] names = {"NoName", "HaveName", "JustAName", "Nameless", "BestName", "Sam", "Dean"};

    @Step("Get one of the logins")
    public String getLogin(){
        return logins[new Random().nextInt(logins.length)];
    }

    @Step("Get one of the passwords")
    public String getPassword(){
        return passwords[new Random().nextInt(passwords.length)];
    }
    @Step("Get one of the names")
    public String getName(){
        return names[new Random().nextInt(names.length)];
    }

    @Step("Create courier")
    public Response courierCreation(CreateCourier courier) {
        String createAPI = webManager.getCreateCourierAPI();
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(createAPI);
    }

    @Step("Delete courier")
    public Response deleteCurrentCourier(Response resp) {
        String deleteAPI = webManager.getDeleteCourierAPI();
        CourierId courier;

        //parse ID from response to next deletion
        try {
            String jsonString = resp.getBody().asString();
            Gson gson = new Gson();
            courier = gson.fromJson(jsonString, CourierId.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return given()
                .body(courier)
                .when()
                .delete(deleteAPI, courier.getId());
    }

    @Step("Login courier")
    public Response loginCourier(LoginCourier courier) {
        String loginAPI = webManager.getLoginCourierAPI();
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(loginAPI);
    }
}
