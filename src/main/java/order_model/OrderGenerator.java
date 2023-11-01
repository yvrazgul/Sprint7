package order_model;

import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
public class OrderGenerator {

        public static OrderRequest getRandomData() {
            String firstName = RandomStringUtils.randomAlphabetic(3);
            String lastName = RandomStringUtils.randomAlphabetic(3);
            String address = RandomStringUtils.randomAlphabetic(10);
            String metroStation = RandomStringUtils.randomAlphabetic(10);
            String phone = RandomStringUtils.randomNumeric(11);
            int rentTime = ThreadLocalRandom.current().nextInt(1, 365);
            String deliveryDate = LocalDate.of(4, 11, 28).toString();
            String comment = RandomStringUtils.randomAlphabetic(3);
            String[] color = {};
            return new OrderRequest(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        }
}
