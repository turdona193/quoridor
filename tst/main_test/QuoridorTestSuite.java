package main_test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    main_test.QuoridorTest.class,
    main_test.OptionsMenuTest.class,
    player_test.PlayerTest.class,
    util_test.GraphTest.class
})
public class QuoridorTestSuite {
}
