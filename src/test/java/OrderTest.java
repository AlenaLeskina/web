
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderTest {

    private final String url = "http://localhost:9999";
    private final SelenideElement name = $("[data-test-id=name] input");
    private final SelenideElement invalidName = $("[data-test-id=name] .input__sub");
    private final SelenideElement phone = $("[data-test-id=phone] input");
    private final SelenideElement invalidPhone = $("[data-test-id=phone] .input__sub");
    private final SelenideElement agreementCheckbox = $("[data-test-id=agreement]");
    private final SelenideElement nextButton = $("button");
    private final SelenideElement successView = $("[data-test-id=order-success]");


    @Test
    void shouldTestOrderSuccess() {
        open(url);
        name.setValue("Алена");
        phone.setValue("+79269999999");
        agreementCheckbox.click();
        nextButton.click();
        successView.shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
    @Test
    void shouldTestNotClickAgreement() {
        open(url);
        name.setValue("Алена Иванова");
        phone.setValue("+79269999999");
        nextButton.click();
        agreementCheckbox.shouldHave(cssClass("input_invalid"));
    }

    @Test
    void shouldTestInvalidName() {
        open(url);
        name.setValue("Alena");
        phone.setValue("+79269999999");
        agreementCheckbox.click();
        nextButton.click();
        invalidName.shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestInvalidNumber() {
        open(url);
        name.setValue("Алена-Иванова");
        phone.setValue("9269999999");
        agreementCheckbox.click();
        nextButton.click();
        invalidPhone.shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestEmptyName() {
        open(url);
        name.setValue("");
        phone.setValue("+79269999999");
        agreementCheckbox.click();
        nextButton.click();
        invalidName.shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestEmptyNumber() {
        open(url);
        name.setValue("АленаИванова");
        phone.setValue("");
        agreementCheckbox.click();
        nextButton.click();
        invalidPhone.shouldHave(exactText("Поле обязательно для заполнения"));
    }


}

