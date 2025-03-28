package com.boyka.demo.ui.sauce.actions;

import static com.boyka.demo.ui.sauce.pages.CheckoutInfoPage.checkoutInfoPage;
import static com.boyka.demo.ui.sauce.pages.CheckoutPage.checkoutPage;
import static com.boyka.demo.ui.sauce.pages.CheckoutReviewPage.checkoutReviewPage;
import static com.boyka.demo.ui.sauce.pages.CheckoutSuccessPage.successPage;
import static com.boyka.demo.ui.sauce.pages.HomePage.homePage;
import static io.github.boykaframework.actions.elements.ClickableActions.withMouse;
import static io.github.boykaframework.actions.elements.ElementActions.onElement;
import static io.github.boykaframework.actions.elements.FingerActions.withFinger;
import static io.github.boykaframework.actions.elements.TextBoxActions.onTextBox;
import static io.github.boykaframework.enums.PlatformType.WEB;
import static io.github.boykaframework.enums.SwipeDirection.UP;
import static io.github.boykaframework.manager.ParallelSession.getSession;
import static java.text.MessageFormat.format;
import static java.util.Objects.isNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.boyka.demo.ui.sauce.pages.components.ProductItem;
import io.github.boykaframework.enums.PlatformType;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.datafaker.Faker;

@UtilityClass
public class HomePageActions {
    private static final NumberFormat FORMAT        = new DecimalFormat ("$0.##");
    private static final PlatformType PLATFORM_TYPE = getSession ().getPlatformType ();

    public static void verifyProductPurchase (final String productName) {
        final var price = verifyProductAddToCart (productName);
        verifyProductCheckout (price);
        fillCheckoutInfo ();
        verifyReviewPage (price);
        verifySuccessPage ();
    }

    private static void fillCheckoutInfo () {
        final var faker = new Faker ();
        onTextBox (checkoutInfoPage ().getFirstName ()).enterText (faker.name ()
            .firstName ());
        onTextBox (checkoutInfoPage ().getLastName ()).enterText (faker.name ()
            .lastName ());
        onTextBox (checkoutInfoPage ().getPostalCode ()).enterText (faker.address ()
            .postcode ());
        withMouse (checkoutInfoPage ().getContinueButton ()).click ();
    }

    private static ProductItem findProductItem (final String name) {
        final var pageCount = PLATFORM_TYPE == WEB ? 1 : 3;
        for (var page = 1; page <= pageCount; page++) {
            final var items = onElement (homePage ().getProductItems ()
                .getTitles ()).itemList ();
            for (var index = 0; index < items.size (); index++) {
                if (items.get (index)
                    .equalsIgnoreCase (name)) {
                    return homePage ().getProductItems ()
                        .toBuilder ()
                        .name (name)
                        .index (index)
                        .create ();
                }
            }
            if (PLATFORM_TYPE != WEB) {
                withFinger ().swipe (UP);
            }
        }
        return null;
    }

    @SneakyThrows
    private static double getPrice (final String price) {
        return FORMAT.parse (price)
            .doubleValue ();
    }

    private static String getPrice (final double price) {
        return FORMAT.format (price);
    }

    private static String verifyProductAddToCart (final String productName) {
        final var item = findProductItem (productName);
        if (!isNull (item)) {
            if (PLATFORM_TYPE == WEB) {
                withMouse (item.getAddToCart ()).click ();
            } else {
                withFinger (item.getDragHandle ()).dragTo (homePage ().getDropZone ());
            }
            onElement (homePage ().getCartCount ()).verifyText ()
                .isEqualTo ("1");
            return onElement (item.getPrice ()).getText ();
        }
        return null;
    }

    private static void verifyProductCheckout (final String price) {
        withMouse (homePage ().getCart ()).click ();
        onElement (checkoutPage ().getCartPrice ()).verifyText ()
            .isEqualTo (price);
        withMouse (checkoutPage ().getCheckoutButton ()).click ();
    }

    private static void verifyReviewPage (final String price) {
        final var formattedPrice = getPrice (price);
        final var tax = formattedPrice * 7.99 / 100;
        final var expectedPrice = getPrice (formattedPrice + tax);
        onElement (checkoutReviewPage ().getTotalPrice (expectedPrice)).verifyText ()
            .isEqualTo (format ("Total: {0}", expectedPrice));
        if (PLATFORM_TYPE != WEB) {
            withFinger ().swipe (UP);
        }
        withMouse (checkoutReviewPage ().getFinishButton ()).click ();
    }

    private static void verifySuccessPage () {
        final var expectedHeader = PLATFORM_TYPE == WEB ? "Thank you for your order!" : "THANK YOU FOR YOU ORDER";

        if (PLATFORM_TYPE != WEB) {
            onElement (successPage ().getBackHome ()).verifyIsDisplayed ()
                .isTrue ();
        }
        
        onElement (successPage ().getSuccessHeader ()).verifyText ()
            .isEqualTo (expectedHeader);
        onElement (successPage ().getSuccessMessage ()).verifyText ()
            .isEqualTo ("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        withMouse (successPage ().getBackHome ()).click ();
        onElement (homePage ().getProductItemsContainer ()).verifyIsDisplayed ()
            .isTrue ();
    }
}
