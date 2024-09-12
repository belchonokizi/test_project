package extensions;

import extensions.callbacks.RestExtension;
import extensions.callbacks.TestSaver;
import extensions.models.Booking;
import extensions.models.User;
import extensions.parameters.RandomUser;
import extensions.parameters.RandomUserResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({RandomUserResolver.class, TestSaver.class, RestExtension.class})
public class ExtensionTest {

    @Test
    public void testWithParam(@RandomUser User testUser) {
        System.out.println(testUser.getName());
        System.out.println(testUser.getJob());
    }

    @Test
    public void failedTest() {
        assertEquals(1, 2);
    }

    @Test
    public void restAssuredConfigTest() {
        Booking booking = Booking.Builder().build();
        System.out.println(booking);
        int actualStatusCode = step("Создание бронирования с валидными параметрами", () ->
                given()
                        .contentType(JSON)
                        .body(booking)
                        .post("/booking")
                        .then()
                        .extract().statusCode()
        );
        step("statusCode ответа эквивалентен 200", () ->
                assertEquals(200, actualStatusCode, "createBooking вернул неверный statusCode")
        );
    }
}
