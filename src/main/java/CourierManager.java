import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierManager {

    public Response courierCreation(CreateCourier courier) {
        String createAPI = "/api/v1/courier";
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(createAPI);
    }

    public Response deleteCurrentCourier(CourierId courier) {
        String deleteAPI = "/api/v1/courier/{id}";
        return given()
                .body(courier)
                .when()
                .delete(deleteAPI, courier.getId());
    }

    public Response loginCourier(LoginCourier courier) {
        String loginAPI = "/api/v1/courier/login";
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(loginAPI);
    }
}
