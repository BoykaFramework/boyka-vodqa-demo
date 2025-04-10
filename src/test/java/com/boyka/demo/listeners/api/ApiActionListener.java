/*
 * MIT License
 *
 * Copyright (c) 2024, Boyka Framework
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package com.boyka.demo.listeners.api;

import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;
import static java.text.MessageFormat.format;
import static java.util.Optional.ofNullable;
import static org.testng.Reporter.log;

import io.github.boykaframework.actions.interfaces.listeners.api.IApiActionsListener;
import io.github.boykaframework.builders.ApiResponse;

/**
 * API Actions listener.
 *
 * @author Wasiq Bhamla
 * @since 06-Apr-2023
 */
public class ApiActionListener implements IApiActionsListener {
    @Override
    public void onExecute (final ApiResponse response) {
        final var request = response.getRequest ();
        step (format ("Executing [{0}] API request", request.getMethod ()));
        addAttachment ("Status Code", Integer.toString (response.getStatusCode ()));
        ofNullable (request.getBody ()).ifPresent (v -> {
            addAttachment ("Request Body", v);
            log (v);
        });
        ofNullable (response.getBody ()).ifPresent (v -> {
            addAttachment ("Response Body", v);
            log (v);
        });
    }
}
