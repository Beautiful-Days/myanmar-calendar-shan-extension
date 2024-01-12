package mmcalendar.shan;

import mmcalendar.*;
import org.junit.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class ShanDateTest {

    private static MyanmarDate myanmarDate;

    // execute before class
    @BeforeClass
    public static void beforeClass() {

        Config.initDefault(
                new Config.Builder()
                        .setCalendarType(CalendarType.ENGLISH)
                        .setLanguage(Language.ENGLISH)
                        .build());

        myanmarDate = MyanmarDate.of(2017, 7, 24);
    }

    // execute after class
    @AfterClass
    public static void afterClass() {
        myanmarDate = null;
        Config.initDefault(
                new Config.Builder()
                        .setCalendarType(CalendarType.ENGLISH)
                        .setLanguage(Language.MYANMAR)
                        .build());
    }

    // execute before test
    @Before
    public void before() {

    }

    // execute after test
    @After
    public void after() {

    }

    @Test
    public void convertTest() {
        ShanDate shanDate = ShanDate.of(LocalDate.now());
        System.out.println(shanDate);
    }

    @Test
    public void convertFromMyanmarDateTest() {
        ShanDate shanDate = ShanDate.of(MyanmarDate.now());
        System.out.println(shanDate);
    }

}
