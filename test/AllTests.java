package test;

import org.junit.platform.suite.api.SelectClasses;
import model.TestLibrary;
import model.BorrowerTest;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ DataControllerTest.class, LibrarianTest.class, PaseBookTest.class, UserTest.class, TestLibrary.class, BorrowerTest.class})
public class AllTests {

}
