package ru.netology.request;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RequestTest {

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\UserSK\\Desktop\\chromedriver.exe");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should submit request")
    void shouldSubmitRequest() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Сергей Холод");
        form.$("[data-test-id=phone] input").setValue("+79193333333");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        $("[data-test-id=order-success]").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if field name is empty")
    void shouldReturnErrorIfEmptyName() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79193333333");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        String expected = "Поле обязательно для заполнения";
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if name entered with english letters")
    void shouldReturnErrorIfNameIsInEnglish() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Sergey Kholod");
        form.$("[data-test-id=phone] input").setValue("+79193333333");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if name entered with digits")
    void shouldReturnErrorIfNameWithDigits() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Сергей Холод 2211");
        form.$("[data-test-id=phone] input").setValue("+79193333333");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if field phone is empty")
    void shouldReturnErrorIfEmptyPhone() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Сергей Холод");
        form.$("[data-test-id=phone] input").setValue("");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        //$("[data-test-id=phone].input_invalid .input__sub").should(exist);
        String expected = "Поле обязательно для заполнения";
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if phone number is wrong")
    void shouldReturnErrorIfWrongPhone() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Сергей Холод");
        form.$("[data-test-id=phone] input").setValue("+7919333");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79193333333.";
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if phone number is entered with russian letters")
    void shouldReturnErrorIfNameWithRussianLetters() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Сергей Холод");
        form.$("[data-test-id=phone] input").setValue("чёрненький такой");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79193333333.";
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if phone number is entered with english letters")
    void shouldReturnErrorIfNameWithEnglishLetters() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Сергей Холод");
        form.$("[data-test-id=phone] input").setValue("chernenkiy takoy");
        form.$(".checkbox__box").click();
        form.$("button.button").click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79193333333.";
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText(expected));
    }

    @Test
    @DisplayName("Should return error if checkbox agreement is empty")
    void shouldReturnErrorIfNotClickedAgreement() {
        SelenideElement form = $("form.form");
        form.$("[data-test-id=name] input").setValue("Сергей Холод");
        form.$("[data-test-id=phone] input").setValue("+79193333333");
        form.$("button.button").click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        $("[data-test-id=agreement].input_invalid").shouldHave(exactText(expected));
    }
}