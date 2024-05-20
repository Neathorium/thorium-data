package com.neathorium.thorium.core.data.namespaces.factories;

import com.neathorium.thorium.core.data.namespaces.formatters.MethodMessageDataFormatters;
import com.neathorium.thorium.core.data.records.MethodMessageData;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriFunction;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodMessageDataFactory {
    private static MethodMessageData getWithCore(
        TriFunction<BiFunction<String, String, String>, String, String, MethodMessageData> constructor,
        Function<String, IllegalArgumentException> exceptionFormatter,
        BiFunction<String, String, String> formatter,
        String nameof,
        String message
    ) {
        final var methodNameof = "MethodMessageDataFactory.getWithCore";
        final var errorMessage = (
            (NullablePredicates.isNull(constructor) ? "Constructor is null.\n" : "") +
            (NullablePredicates.isNull(formatter) ? "Formatter function is null.\n" : "")
        );
        if (isNotBlank(errorMessage)) {
            throw exceptionFormatter.apply(methodNameof + ":\n" + errorMessage);
        }

        final var localNameof = isNotBlank(nameof) ? nameof : methodNameof;
        final var localMessage = isNotBlank(message) ? message : "Default method message";
        return constructor.apply(formatter, localNameof, localMessage);
    }

    static MethodMessageData getWith(BiFunction<String, String, String> formatter, String nameof, String message) {
        return getWithCore(MethodMessageData::new, IllegalArgumentException::new, formatter, nameof, message);
    }

    static MethodMessageData getWith(String nameof, String message) {
        return getWith(MethodMessageDataFormatters::getMethodMessageDataFormatted, nameof, message);
    }

    static MethodMessageData getWithMessage(String message) {
        return getWith("getWith", message);
    }

    static MethodMessageData getWithDefaults() {
        return getWith(MethodMessageDataFormatters::getMethodMessageDataFormatted, "", "");
    }
}
