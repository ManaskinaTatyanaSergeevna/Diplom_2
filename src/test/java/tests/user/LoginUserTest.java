package tests.user;

import api.model.User;
import api.steps.UserSteps;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

public class LoginUserTest {

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void authorizationTest(){
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setEmail("test-data@yandex.ru");
        user.setPassword("password");
        Response response = userSteps.sendPostRequestApiAuthLogin(user);
        response.then().log().all()
                .assertThat().body("success", Matchers.is(true))
                .and().body("accessToken", Matchers.notNullValue())
                .and().body("refreshToken", Matchers.notNullValue())
                .and().body("user.email", Matchers.is(user.getEmail().toLowerCase(Locale.ROOT)))
                .and().body("user.name", Matchers.is("Username"))
                .and().statusCode(200);
    }

    @Test
    public void authorizationWithoutEmailTest(){
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setPassword("password");
        Response response = userSteps.sendPostRequestApiAuthLogin(user);
        userSteps.checkFailedResponseApiAuthLogin(response);

    }

    @Test
    public void authorizationWithoutPasswordTest(){
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setEmail("test-data@yandex.ru");
        Response response = userSteps.sendPostRequestApiAuthLogin(user);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }

    @Test
    public  void authorizationWithoutEmailAndPasswordTest(){
        UserSteps userSteps = new UserSteps();
        User user = new User();
        Response response = userSteps.sendPostRequestApiAuthLogin(user);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }

    @Test
    public void authorizationWithWrongEmailTest(){
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setEmail("test-datashechka@yandex.ru");
        Response response = userSteps.sendPostRequestApiAuthLogin(user);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }

    @Test
    public void authorizationWithWrongPasswordTest(){
        UserSteps userSteps = new UserSteps();
        User user = new User();
        user.setPassword("lalalatopola");
        Response response = userSteps.sendPostRequestApiAuthLogin(user);
        userSteps.checkFailedResponseApiAuthLogin(response);
    }
}
