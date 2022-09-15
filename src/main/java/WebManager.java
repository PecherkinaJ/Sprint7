import io.qameta.allure.Step;
import io.restassured.RestAssured;

public class WebManager {

    private String baseURI = "http://qa-scooter.praktikum-services.ru/";

    private String createCourierAPI = "/api/v1/courier";
    private String deleteCourierAPI = "/api/v1/courier/{id}";
    private String loginCourierAPI = "/api/v1/courier/login";
    private String createOrderAPI = "/api/v1/orders";
    private String orderListAPI = "/api/v1/orders";

    @Step("Get CreateCourierAPI")
    public String getCreateCourierAPI() {
        return createCourierAPI;
    }

    @Step("Set CreateCourierAPI")
    public void setCreateCourierAPI(String createCourierAPI) {
        this.createCourierAPI = createCourierAPI;
    }

    @Step("Get main URI")
    public String getBaseURI() {
        return baseURI;
    }

    @Step("Set main URI")
    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    @Step("Get DeleteCourierAPI")
    public String getDeleteCourierAPI() {
        return deleteCourierAPI;
    }

    @Step("Set LoginCourierAPI")
    public void setDeleteCourierAPI(String deleteCourierAPI) {
        this.deleteCourierAPI = deleteCourierAPI;
    }

    @Step("Get LoginCourierAPI")
    public String getLoginCourierAPI() {
        return loginCourierAPI;
    }

    @Step("Set LoginCourierAPI")
    public void setLoginCourierAPI(String loginCourierAPI) {
        this.loginCourierAPI = loginCourierAPI;
    }

    @Step("Get CreateOrderAPI")
    public String getCreateOrderAPI() {
        return createOrderAPI;
    }

    @Step("Set CreateOrderAPI")
    public void setCreateOrderAPI(String createOrderAPI) {
        this.createOrderAPI = createOrderAPI;
    }

    @Step("Get OrderListAPI")
    public String getOrderListAPI() {
        return orderListAPI;
    }

    @Step("Set OrderListAPI")
    public void setOrderListAPI(String orderListAPI) {
        this.orderListAPI = orderListAPI;
    }
}
