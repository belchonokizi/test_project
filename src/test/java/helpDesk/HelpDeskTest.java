package helpDesk;

import core.BaseSeleniumTest;
import helpers.TestValues;
import org.junit.jupiter.api.Test;
import readProperties.ConfigProvider;

import static helpers.StringModifier.getUniqueString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpDeskTest extends BaseSeleniumTest {

    @Test
    public void checkTicket() {
        String title = getUniqueString(TestValues.TEST_TITLE);

        TicketPage ticketPage = new MainPage().createTicket(title, TestValues.TEST_BODY, TestValues.TEST_EMAIL)
                .openLogin()
                .auth(ConfigProvider.DEMO_LOGIN, ConfigProvider.DEMO_PASSWORD)
                .findTicket(title);

        assertTrue(ticketPage.getTitle().contains(title));
        assertEquals(ticketPage.getBody(), TestValues.TEST_BODY);
        assertEquals(ticketPage.getEmail(), TestValues.TEST_EMAIL);

    }
}
