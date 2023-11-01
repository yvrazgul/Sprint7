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
import static java.net.HttpURLConnection.*;
import static org.junit.Assert.assertEquals;

public class TestWrongPassword {
    private CourierApiClient courierConfig;
    private Courier courier;
    private int courierId;
    private String courierMessage;
    private int statusCode;

    @Before
    public void setUp() {
        courierConfig = new CourierApiClient();
    }

    @DisplayName("При неправильном пароле, ожидаем ошибку")
    @Description("Проверяем, что нельзя авторизоваться при неправильном пароле")
    @Test
    public void checkNotAuthorizeIncorrectPassword() {
        courier = CourierGenerator.getRandomData();
        ValidatableResponse createResponse = courierConfig.create(courier);
        String initialPassword = courier.getPassword();
        statusCode = createResponse.extract().statusCode();
        assertEquals("The status code is invalid", HTTP_CREATED, statusCode);
        courier.setPassword(CourierGenerator.getRandomPassword().getPassword());

        ValidatableResponse loginResponse = courierConfig.login(CourierLogin.courierLogin(courier));
        statusCode = loginResponse.extract().statusCode();
        courierMessage = loginResponse.extract().path("message");
        assertEquals("The status code is invalid", HTTP_NOT_FOUND, statusCode);
        assertEquals("The login is still enabled", "Учетная запись не найдена", courierMessage);

        courier.setPassword(initialPassword);
        loginResponse = courierConfig.login(CourierLogin.courierLogin(courier));
        statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
    }


    @After
    public void clearDown() {
        courierConfig.delete(courierId);
    }
}
