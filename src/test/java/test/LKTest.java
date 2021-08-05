package test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static data.DataGenerator.*;
import static data.DataGenerator.Registration.*;


public class LKTest {

    @BeforeEach
    public void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[name=login]").setValue(registeredUser.getLogin());
        $("[name=password]").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(".heading").shouldHave(Condition.exactText("  Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[name=login]").setValue(notRegisteredUser.getLogin());
        $("[name=password]").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(".notification__content").shouldBe(visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[name=login]").setValue(blockedUser.getLogin());
        $("[name=password]").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(".notification__content").shouldBe(visible).shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[name=login]").setValue(wrongLogin);
        $("[name=password]").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(".notification__content").shouldBe(visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[name=login]").setValue(registeredUser.getLogin());
        $("[name=password]").setValue(wrongPassword);
        $("[data-test-id=action-login]").click();
        $(".notification__content").shouldBe(visible).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
