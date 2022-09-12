import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class TestGetOrderList {

    OrderManager om = new OrderManager();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void testGetOrderList(){
        Response resp = om.getOrderList();
        resp.then().statusCode(200)
                .assertThat().body("orders", Matchers.notNullValue());
    }
}
