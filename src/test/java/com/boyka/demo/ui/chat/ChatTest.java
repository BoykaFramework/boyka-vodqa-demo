package com.boyka.demo.ui.chat;

import static com.boyka.demo.ui.chat.actions.ChatActions.chatAndVerifyMessage;
import static com.boyka.demo.ui.chat.actions.ChatActions.joinChannel;
import static io.github.boykaframework.actions.drivers.WindowActions.onWindow;
import static io.github.boykaframework.enums.PlatformType.ANDROID;
import static io.github.boykaframework.enums.PlatformType.WEB;
import static io.github.boykaframework.manager.ParallelSession.clearAllSessions;
import static io.github.boykaframework.manager.ParallelSession.createSession;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ChatTest {
    private static final String ANDROID_USER = "Faisal Khatri";
    private static final String CHANNEL      = "Boyka Framework";
    private static final String DESKTOP_USER = "Wasiq Bhamla";

    @AfterMethod
    public void afterMethod (final ITestResult result) {
        if (!result.isSuccess ()) {
            onWindow ().takeScreenshot ();
        }
    }

    @BeforeTest
    public void setUpTest () {
        createSession (DESKTOP_USER, WEB, "test_chat_web");
        createSession (ANDROID_USER, ANDROID, "test_chat_android");
    }

    @AfterTest
    public void tearDownTest () {
        clearAllSessions ();
    }

    @Test
    public void testChat () {
        joinChannel (CHANNEL, DESKTOP_USER);
        joinChannel (CHANNEL, ANDROID_USER, DESKTOP_USER);

        chatAndVerifyMessage (DESKTOP_USER, "Hi from Desktop Web", ANDROID_USER);
        chatAndVerifyMessage (ANDROID_USER, "Hi from Android Web", DESKTOP_USER);
    }
}
