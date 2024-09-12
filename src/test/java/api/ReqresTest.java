package api;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Feature("Api Regres Pojo")
public class ReqresTest {
    private static final String URL = "https://reqres.in/";

    /**
     * 1. Получить список пользователей со второй страница на сайте https://reqres.in/
     * 2. Убедиться что id пользователей содержаться в их avatar;
     * 3. Убедиться, что email пользователей имеет окончание reqres.in;
     */
    @Test
    public void checkAvatarAndIdTest() {
        Specifications.install(Specifications.requestSpec(URL),
                Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(user -> assertTrue(user.getAvatar()
                .contains(String.valueOf(user.getId()))));

        assertTrue(users.stream()
                .allMatch(user -> user.getEmail().endsWith("@reqres.in")));

    }

    /**
     * 1. Используя сервис https://reqres.in/ протестировать регистрацию пользователя в системе
     * 2. Тест для успешной регистрации
     */
    @Test
    @DisplayName("Success registration")
    public void successRegTest() {
        Specifications.install(Specifications.requestSpec(URL),
                Specifications.responseSpecOK200());
        Integer UserId = 4;
        String UserPassword = "QpwL5tke4Pnpja7X4";
        Specifications.install(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(SuccessReg.class);
        assertNotNull(successReg.getId());
        assertNotNull(successReg.getToken());
        assertEquals(UserId, successReg.getId());
        assertEquals(UserPassword, successReg.getToken());
    }

    @Test
    public void unSuccessRegTest() {
        Specifications.install(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        Register peopleSecond = new Register("sydney@fife", "");
        UnSuccessReg unSuccessUserReg = given()
                .body(peopleSecond)
                .when()
                .post("/api/register")
                .then()  //.assertThat().statusCode(400) проверить статус ошибки, если не указана спецификация
                .log().body()
                .extract().as(UnSuccessReg.class);
        assertNotNull(unSuccessUserReg.getError());
        assertEquals("Missing password", unSuccessUserReg.getError());
    }

    /**
     * Используя сервис https://reqres.in/ убедиться, что операция LIST<RESOURCE> возвращает данные,
     * отсортированные по годам.
     */
    @Test
    public void checkSortedYearsTest() {
        Specifications.install(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<ColorsData> data = given()
                .when()
                .get("/api/unknown")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> dataYears = data.stream().map(ColorsData::getYear).toList();
        List<Integer> sortedDataYears = dataYears.stream().sorted().toList();
        assertEquals(dataYears, sortedDataYears);
    }

    /**
     * Тест 4.1
     * Используя сервис https://reqres.in/ попробовать удалить второго пользователя и сравнить статус-код
     */
    @Test
    public void deleteUserTest() {
        Specifications.install(Specifications.requestSpec(URL), Specifications.responseSpec(204));
        given().when().delete("/api/users/2")
                .then()
                .log().all();
    }


}
