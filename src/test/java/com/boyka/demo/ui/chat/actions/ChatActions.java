package com.boyka.demo.ui.chat.actions;

import static com.boyka.demo.ui.chat.pages.ChannelPage.channelPage;
import static com.boyka.demo.ui.chat.pages.HomePage.homePage;
import static io.github.boykaframework.actions.device.DeviceActions.onDevice;
import static io.github.boykaframework.actions.drivers.DriverActions.withDriver;
import static io.github.boykaframework.actions.elements.ClickableActions.withMouse;
import static io.github.boykaframework.actions.elements.ElementActions.onElement;
import static io.github.boykaframework.actions.elements.TextBoxActions.onTextBox;
import static io.github.boykaframework.enums.PlatformType.ANDROID;
import static io.github.boykaframework.manager.ParallelSession.getSession;
import static io.github.boykaframework.manager.ParallelSession.switchPersona;
import static java.time.Duration.ofSeconds;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatActions {
    public static void chatAndVerifyMessage (final String userName, final String message, final String friendUserName) {
        switchPersona (userName);

        onTextBox (channelPage ().getMessageInput ()).enterText (message);

        if (getSession ().getPlatformType () == ANDROID && onDevice ().isKeyboardVisible ()) {
            onDevice ().hideKeyboard ();
        }

        withMouse (channelPage ().getSendMessage ()).click ();
        verifyChat (userName, message);

        switchPersona (friendUserName);
        verifyChat (userName, message);
    }

    public static void joinChannel (final String channel, final String userName) {
        joinChannel (channel, userName, null, true);
    }

    public static void joinChannel (final String channel, final String userName, final String friendUserName) {
        joinChannel (channel, userName, friendUserName, false);
    }

    private static void joinChannel (final String channel, final String userName, final String friendUserName,
        final boolean isFirstParticipant) {
        switchPersona (userName);

        onTextBox (homePage ().getChannel ()).enterText (channel);
        onTextBox (homePage ().getUserName ()).enterText (userName);
        withMouse (homePage ().getJoinChannel ()).click ();

        onElement (channelPage ().getSendMessage ()).verifyIsDisplayed ()
            .isTrue ();

        if (!isFirstParticipant) {
            switchPersona (friendUserName);
            verifyChat (userName, "joined the channel");
        }
    }

    private static void verifyChat (final String userName, final String message) {
        withDriver ().pause (ofSeconds (1));
        onElement (channelPage ().getLastMessageUser ()).verifyText ()
            .isEqualTo (userName);
        onElement (channelPage ().getLastMessage ()).verifyText ()
            .isEqualTo (message);
    }
}
