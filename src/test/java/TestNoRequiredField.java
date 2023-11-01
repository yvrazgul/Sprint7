import config.CourierApiClient;
import courier_model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import static java.net.HttpURLConnection.*;

@RunWith(Parameterized.class)
public class TestNoRequiredField {
    private final String login;
    private final String password;
    private boolean isCourierCreated;
    private CourierApiClient courierCreate;
    private Courier courier;
    private int statusCode;

    public TestNoRequiredField(String login, String password, boolean isCourierCreated) {
        this.login = login;
        this.password = password;
        this.isCourierCreated = isCourierCreated;
    }

    @Parameterized.Parameters()
    public static Object[][] getData() {
        return new Object[][]{
                {RandomStringUtils.randomAlphabetic(3), null, false},
                {null, RandomStringUtils.randomAlphabetic(3), false},
                {null, null, false}
        };
    }

    @Before
    public void setUp() {
        courierCreate = new CourierApiClient();
    }

    @DisplayName("При отсутсвии обязательных полей, ожидаем ошибку в ответе")
    @Description("Проверяем, что курьер не создается, при отсутствии обязательных полей")
    @Test
    public void createCourierNegativeData() {
        courier = new Courier();
        courier.setLoginAndPassword(this.login, this.password);
        ValidatableResponse createResponse = courierCreate.create(courier);
        statusCode = createResponse.extract().statusCode();
        String courierMessage = createResponse.extract().path("message");

        assertEquals("The status code is invalid", HTTP_BAD_REQUEST, statusCode);
        assertFalse("The courier is created", isCourierCreated);
        assertEquals("The courier is created", "Недостаточно данных для создания учетной записи", courierMessage);
    }

}
