package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class OrderingCardDeliveryTest {
}

class registrationTest{

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldRegisterByDeliverCard() {
        String planningDate = generateDate(4, "dd.MM.yyyy");

        Selenide.open("http://localhost:9999");
        SelenideElement form = $$("form").find(Condition.visible);
        form.$("[data-test-id='city'] input").setValue("Каза");
        $$("div.popup__content div").find(Condition.text("Казань")).click();
        form.$("[data-test-id='date'] input").should(Condition.visible)
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        form.$("[data-test-id='name'] input").setValue("Петров Иван");
        form.$("[data-test-id='phone'] input").setValue("+79000000000");
        form.$("[data-test-id='agreement']").click();
        $$("button").filter(Condition.visible).find(Condition.text("Запланировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification']").should(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).should(Condition.visible);
        form.$("[data-test-id='date'] input").should(Condition.visible)
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $$("button").filter(Condition.visible).find(Condition.text("Перепланировать")).click();
        $("[data-test-id='success-notification']").should(Condition.visible, Duration.ofSeconds(15));
        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(15));
    }
}