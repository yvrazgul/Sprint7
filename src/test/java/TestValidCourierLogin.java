import config.CourierApiClient;
import courier_model.Courier;
import courier_model.CourierGenerator;
import courier_model.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestValidCourierLogin {
    private CourierApiClient courierLogin;
    private Courier courier;
    private int courierId;
    private int statusCode;

    @Before
    public void setUp() {
        courierLogin = new CourierApiClient();
    }

    @DisplayName("Авторизация курьера с валидными данными")
    @Description("Проверяем, что курьер может авторизоваться с валидными данными")
    @Test
    public void checkLoginWithCreatedCourier() {
        courier = CourierGenerator.getRandomData();
        ValidatableResponse createResponse = courierLogin.create(courier);
        statusCode = createResponse.extract().statusCode();
        assertEquals("The status code is invalid", HTTP_CREATED, statusCode);

        ValidatableResponse loginResponse = courierLogin.login(CourierLogin.courierLogin(courier));
        statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        assertEquals("The status code is invalid", HTTP_OK, statusCode);
        assertTrue("The courier ID is not provided", courierId != 0);
    }

    @After
    public void clearDown() {
        courierLogin.delete(courierId);
    }
}