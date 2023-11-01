package courier_model;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

        public static Courier getRandomData() {
            String login = RandomStringUtils.randomAlphabetic(10);
            String password = RandomStringUtils.randomAlphabetic(10);
            String firstName = RandomStringUtils.randomAlphabetic(10);
            return new Courier(login, password, firstName);
        }

        public static Courier getRandomLoginAndPassword() {
            String login = RandomStringUtils.randomAlphabetic(10);
            String password = RandomStringUtils.randomAlphabetic(10);
            return new Courier(login, password);
        }

        public static Courier getRandomPassword() {
            Courier courier = new Courier();
            String password = RandomStringUtils.randomAlphabetic(5);
            courier.setPassword(password);
            return courier;
        }

        public static Courier getRandomLogin() {
            Courier courier = new Courier();
            String login = RandomStringUtils.randomAlphabetic(5);
            courier.setLogin(login);
            return courier;
        }
}
