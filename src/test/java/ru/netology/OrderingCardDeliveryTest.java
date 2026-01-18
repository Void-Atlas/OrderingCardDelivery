import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.DataGenerator;
import ru.netology.DataGenerator.UserInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class OrderingCardDeliveryTest {

    private UserInfo user;
    private String firstMeetingDate;
    private String secondMeetingDate;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");

        user = DataGenerator.getRandomUserRu();

        firstMeetingDate = DataGenerator.generateDate(4);
        secondMeetingDate = DataGenerator.generateDate(7);
    }

    @Test
    void shouldSuccessfullyPlanAndThenRescheduleMeeting() {
        fillFormWith(user, firstMeetingDate);
        clickPlanningButton();

        $("[data-test-id=success-notification]")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно запланирована на " + firstMeetingDate), Duration.ofSeconds(15));

        changeDateTo(secondMeetingDate);
        clickPlanningButton();

        $("[data-test-id=replan-notification]")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $$("button").find(Condition.text("Перепланировать")).click();

        $("[data-test-id=success-notification]")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно запланирована на " + secondMeetingDate));
    }

    private void fillFormWith(UserInfo user, String meetingDate) {
        $("[data-test-id=city] input").setValue(user.getCity());
        $$(".menu div").find(Condition.text(user.getCity())).shouldBe(Condition.visible).click();

        $("[data-test-id=date] input")
                .should(Condition.visible)
                .doubleClick()
                .sendKeys(meetingDate);

        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());

        $("[data-test-id=agreement] .checkbox__text").click();
    }

    private void changeDateTo(String newDate) {
        SelenideElement dateField = $("[data-test-id=date] input");
        dateField.should(Condition.visible).doubleClick();
        dateField.sendKeys(newDate);
    }

    private void clickPlanningButton() {
        $$("button")
                .filter(Condition.visible)
                .find(Condition.text("Запланировать"))
                .should(enabled)
                .click();
    }
}