package com.boyka.demo.ui.sauce.actions;

import static com.boyka.demo.ui.sauce.pages.HomePage.homePage;
import static com.boyka.demo.ui.sauce.pages.LoginPage.loginPage;
import static io.github.boykaframework.actions.elements.ClickableActions.withMouse;
import static io.github.boykaframework.actions.elements.ElementActions.onElement;
import static io.github.boykaframework.actions.elements.FingerActions.withFinger;
import static io.github.boykaframework.actions.elements.TextBoxActions.onTextBox;
import static io.github.boykaframework.enums.PlatformType.WEB;
import static io.github.boykaframework.manager.ParallelSession.getSession;

import io.github.boykaframework.enums.PlatformType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoginActions {
    private static final PlatformType PLATFORM_TYPE = getSession ().getPlatformType ();

    public static void verifyLogin (final String username, final String password, final boolean isValid) {
        verifyLogin (username, password, isValid, null);
    }

    public static void verifyLogin (final String username, final String password, final boolean isValid,
        final String expectedMessage) {
        onTextBox (loginPage ().getUserName ()).enterText (username);
        onTextBox (loginPage ().getPassword ()).enterText (password);
        withMouse (loginPage ().getLogin ()).click ();

        if (isValid) {
            onElement (homePage ().getMenu ()).verifyIsDisplayed ()
                .isTrue ();
        } else {
            onElement (loginPage ().getMessage ()).verifyText ()
                .isEqualTo (expectedMessage);
        }
    }

    public static void verifyLogout () {
        if (PLATFORM_TYPE == WEB) {
            withMouse (homePage ().getMenu ()).click ();
        } else {
            withFinger (homePage ().getMenu ()).tap ();
        }
        withMouse (homePage ().getLogout ()).click ();
        onElement (loginPage ().getUserName ()).verifyIsDisplayed ()
            .isTrue ();
    }
}
