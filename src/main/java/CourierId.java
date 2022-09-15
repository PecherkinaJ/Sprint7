import io.qameta.allure.Step;

public class CourierId {
    private String id;

    @Step("Get courier ID")
    public String getId() {
        return id;
    }

    @Step("Set courier ID")
    public void setId(String id) {
        this.id = id;
    }
}
