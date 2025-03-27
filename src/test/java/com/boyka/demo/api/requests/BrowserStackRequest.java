package com.boyka.demo.api.requests;

import static io.github.boykaframework.enums.ContentType.JSON;
import static io.github.boykaframework.enums.ContentType.MULTIPART_FORM_DATA;
import static io.github.boykaframework.enums.RequestMethod.GET;
import static io.github.boykaframework.enums.RequestMethod.POST;
import static java.lang.System.getProperty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.nio.file.Path;

import io.github.boykaframework.builders.ApiRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BrowserStackRequest {
    public static ApiRequest recentAppsRequest () {
        return recentAppsRequest (null);
    }

    public static ApiRequest recentAppsRequest (final String appName) {
        final var request = getBrowserStackRequest ().toBuilder ()
            .method (GET)
            .path ("/recent_apps")
            .create ();
        if (isNotEmpty (appName)) {
            request.toBuilder ()
                .path ("/recent_apps/${appName}")
                .pathParam ("appName", appName)
                .create ();
        }
        return request;
    }

    public static ApiRequest uploadApp () {
        return getBrowserStackRequest ().toBuilder ()
            .contentType (MULTIPART_FORM_DATA)
            .method (POST)
            .formBody ("file", Path.of (getProperty ("user.dir"), "src/test/resources/apps/sauce-demo.apk")
                .toString ())
            .formBody ("custom_id", "AndroidDemoApp")
            .create ();
    }

    private static ApiRequest getBrowserStackRequest () {
        return ApiRequest.createRequest ()
            .userName ("${env:BS_USER}")
            .password ("${env:BS_KEY}")
            .contentType (JSON)
            .create ();
    }
}
