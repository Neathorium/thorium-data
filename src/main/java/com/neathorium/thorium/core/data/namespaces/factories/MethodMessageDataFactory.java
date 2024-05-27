package com.neathorium.thorium.core.data.namespaces.factories;

import com.neathorium.thorium.core.data.namespaces.formatters.MethodMessageDataFormatters;
import com.neathorium.thorium.core.data.records.MethodMessageData;
import com.neathorium.thorium.java.extensions.interfaces.functional.TriFunction;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;

import java.util.function.BiFunction;
import java.util.function.Function;

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
        if (StringUtilities.isVisible(errorMessage)) {
            throw exceptionFormatter.apply(methodNameof + ":\n" + errorMessage);
        }

        final var localNameof = StringUtilities.isVisible(nameof) ? nameof : methodNameof;
        final var localMessage = StringUtilities.isVisible(message) ? message : "Default method message";
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
