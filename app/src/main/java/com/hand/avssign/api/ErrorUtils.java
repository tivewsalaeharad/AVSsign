package com.hand.avssign.api;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    private static final String ERROR_MESSAGE = "Код HTTP: %d\n Ошибка: %s\n Код ошибки: %d";

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                ApiFactory.buildRetrofit()
                        .responseBodyConverter(APIError.class, new Annotation[0]);
        APIError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }

    public static String errorMessage(Response<?> response) {
        try {
            APIError error = parseError(response);
            return String.format(Locale.getDefault(), ERROR_MESSAGE, response.code(), error.message(), error.status());
        } catch (com.google.gson.JsonSyntaxException e) {
            return String.format(Locale.getDefault(), ERROR_MESSAGE, response.code(), response.errorBody().toString(), 0);
        }
    }
}