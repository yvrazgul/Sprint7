import config.CourierApiClient;
import courier_model.Courier;
import courier_model.CourierGenerator;
import courier_model.CourierLogin;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;

public class TestNotExistingCourierLogin {
    private CourierApiClient courierLogin;
    private Courier courier;
    private int courierId;
    private int statusCode;

    @Before
    public void setUp() {
        courierLogin = new CourierApiClient();
    }

    @DisplayName("При авторизации несуществующего или удаленного курьера, ожидаем ошибку")
    @Description("Проверяем, что нельзя авторизоваться под существующим курьером")
    @Test
    public void checkLoginNonExistentCourier() {
        courier = CourierGenerator.getRandomData();
        ValidatableResponse createResponse = courierLogin.create(courier);
        statusCode = createResponse.extract().statusCode();
        assertEquals("The status code is invalid", HTTP_CREATED, statusCode);

        ValidatableResponse loginResponse= courierLogin.login(CourierLogin.courierLogin(courier));
        statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");

        ValidatableResponse deleteResponse = courierLogin.delete(courierId);
        statusCode = deleteResponse.extract().statusCode();
        assertEquals("The status code is invalid", HTTP_OK, statusCode);

        loginResponse = courierLogin.login(CourierLogin.courierLogin(courier));
        statusCode = loginResponse.extract().statusCode();
        String courierMessage = loginResponse.extract().path("message");
        assertEquals("The status code is invalid", HTTP_NOT_FOUND, statusCode);
        assertEquals("The login is still enabled", "Учетная запись не найдена", courierMessage);
    }
}