/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.eomcs.app2;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void appHasAGreeting() {
        ServerApp classUnderTest = new ServerApp();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}
