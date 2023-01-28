package tests.user;

import api.model.User;
import api.steps.UserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ChangeUserDataTest {

    private String name;
    private String email;
    private String password;
    private UserSteps userSteps;
    private User user;
    private String accessToken;


    @Before
    public void setUp() throws InterruptedException {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        name = RandomStringUtils.randomAlphanumeric(4, 20);
        email = RandomStringUtils.randomAlphanumeric(6, 10) + "@yandex.ru";
        password = RandomStringUtils.randomAlphanumeric(10, 20);
        userSteps = new UserSteps();
        user = new User(name, email, password);
        Response resp = userSteps.sendPostRequestApiAuthRegister(user);
        accessToken = JsonPath.from(resp.getBody().asString()).get("accessToken");
        Thread.sleep(200);
    }


    @Test
    @DisplayName("Изменение имени пользователя с авторизацией.")
    @Description("Изменение имени пользователя с авторизацией. Проверка успешного ответа сервера.")
    public void changeUserNameWithAuthorizationTest() {
        String newName = "Tatyana";
        User changeUser = new User();
        changeUser.setName(newName);
        user.setName(newName);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, email, newName);
    }


    @Test
    @DisplayName("Изменение E-mail пользователя с авторизацией.")
    @Description("Изменение E-mail пользователя с авторизацией. Проверка успешного ответа сервера.")
    public void changeUserEmailWithAuthorizationTest() {
        String newEmail = "123" + email;
        User changeUser = new User();
        changeUser.setEmail(newEmail);
        user.setEmail(newEmail);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, name);
    }

    @Test
    @DisplayName("Изменение пароля пользователя с авторизацией.")
    @Description("Изменение пароля пользователя с авторизацией. Проверка успешного ответа сервера.")
    public void changeUserPasswordWithAuthorizationTest() {
        String newPassword = "diplomNa100ballov";
        User changeUser = new User();
        changeUser.setPassword(newPassword);
        user.setPassword(newPassword);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, email, name);
    }

    @Test
    @DisplayName("Изменение имени и E-mail пользователя с авторизацией.")
    @Description("Изменение имени и E-mail пользователя с авторизацией. Проверка успешного ответа сервера.")
    public void changeUserNameAndEmailWithAuthorizationTest() {
        String newEmail = "234" + email;
        String newName = "TatyanaSergeevna";
        User changeUser = new User();
        changeUser.setEmail(newEmail);
        changeUser.setName(newName);
        user.setEmail(newEmail);
        user.setName(newName);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, newName);
    }

    @Test
    @DisplayName("Изменение имени и пароля пользователя с авторизацией.")
    @Description("Изменение имени и пароля пользователя с авторизацией. Проверка успешного ответа сервера.")
    public void changeUserNameAndPasswordWithAuthorizationTest() {
        String newPassword = "HappyNewYear!!!AMneSdachiDiploma";
        String newName = "TatyanaSergeevna";
        User changeUser = new User();
        changeUser.setPassword(newPassword);
        changeUser.setName(newName);
        user.setPassword(newPassword);
        user.setName(newName);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, email, newName);
    }

    @Test
    @DisplayName("Изменение E-mail и пароля пользователя с авторизацией.")
    @Description("Изменение E-mail и пароля пользователя с авторизацией. Проверка успешного ответа сервера.")
    public void changeUserEmailAndPasswordWithAuthorizationTest() {
        String newPassword = "YaObyazatel'noSpravlus'!";
        String newEmail = "567" + email;
        User changeUser = new User();
        changeUser.setPassword(newPassword);
        changeUser.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, name);
    }

    @Test
    @DisplayName("Изменение всех данных пользователя с авторизацией.")
    @Description("Изменение всех данных пользователя с авторизацией. Проверка успешного ответа сервера.")
    public void changeAllUserFieldsWithAuthorizationTest() {
        String newPassword = "Zdorov'yaSamoeGlavnoe";
        String newName = "ManaskinaTatyanaSergeevna";
        String newEmail = "acb" + email;
        User changeUser = new User(newName, newEmail, newPassword);
        user = changeUser;
        Response response = userSteps.sendPatchRequestWithAuthorizationApiAuthUser(changeUser, accessToken);
        userSteps.checkSuccessResponseApiAuthUser(response, newEmail, newName);
    }


    @Test
    @DisplayName("Изменение имени пользователя без авторизации.")
    @Description("Изменение имени пользователя без авторизации. Проверка неуспешного ответа сервера.")
    public void changeUserNameWithoutAuthorizationTest() {
        String newName = "Tatyana";
        User changeUser = new User();
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    @DisplayName("Изменение E-mail пользователя без авторизации.")
    @Description("Изменение E-mail пользователя без авторизации. Проверка неуспешного ответа сервера.")
    public void changeUserEmailWithoutAuthorizationTest() {
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        User changeUser = new User();
        changeUser.setName(newEmail);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    @DisplayName("Изменение пароля пользователя без авторизации.")
    @Description("Изменение пароля пользователя без авторизации. Проверка неуспешного ответа сервера.")
    public void changeUserPasswordWithoutAuthorizationTest() {
        String newPassword = "diplomNa100ballov";
        User changeUser = new User();
        changeUser.setName(newPassword);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    @DisplayName("Изменение имени E-mail пользователя без авторизации.")
    @Description("Изменение имени и E-mail пользователя без авторизации. Проверка неуспешного ответа сервера.")
    public void changeUserNameAndEmailWithoutAuthorizationTest() {
        String newName = "TatyanaSergeevna";
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        User changeUser = new User();
        changeUser.setName(newName);
        changeUser.setEmail(newEmail);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    @DisplayName("Изменение имени и пароля пользователя без авторизации.")
    @Description("Изменение имени и пароля пользователя без авторизации. Проверка неуспешного ответа сервера.")
    public void changeUserNameAndPasswordWithoutAuthorizationTest() {
        String newPassword = "diplomNa100ballov";
        String newName = "TatyanaSergeevna";
        User changeUser = new User();
        changeUser.setName(newPassword);
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    @DisplayName("Изменение E-mail и пароля пользователя без авторизации.")
    @Description("Изменение E-mail и пароля пользователя без авторизации. Проверка неуспешного ответа сервера.")
    public void changeUserEmailAndPasswordWithoutAuthorizationTest() {
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        String newPassword = "diplomNa100ballov";
        User changeUser = new User();
        changeUser.setName(newEmail);
        changeUser.setPassword(newPassword);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @Test
    @DisplayName("Изменение всех данных пользователя без авторизации.")
    @Description("Изменение всех данных пользователя без авторизации. Проверка неуспешного ответа сервера.")
    public void changeAllUserFieldsWithoutAuthorizationTest() {
        String newEmail = "hochyChobiMenyaPohvalili@yandex.ru";
        String newPassword = "diplomNa100ballov";
        String newName = "TatyanaSergeevna";
        User changeUser = new User();
        changeUser.setName(newEmail);
        changeUser.setPassword(newPassword);
        changeUser.setName(newName);
        Response response = userSteps.sendPatchRequestWithoutAuthorizationApiAuthUser(changeUser);
        userSteps.checkFailedResponseApiAuthUser(response);
    }

    @After
    public void deleteRandomUser() {
        given().log().all()
                .header("Content-Type", "application/json")
                .body(user)
                .delete("/api/auth/user");
    }


}
