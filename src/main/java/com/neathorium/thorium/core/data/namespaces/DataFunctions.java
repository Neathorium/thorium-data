package com.neathorium.thorium.core.data.namespaces;

import com.neathorium.thorium.core.data.namespaces.factories.MethodMessageDataFactory;
import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.records.MethodMessageData;
import com.neathorium.thorium.exceptions.exception.ArgumentNullException;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface DataFunctions {
    static <T> T getObject(Data<T> data) {
        return NullablePredicates.isNotNull(data) ? data.OBJECT() : null;
    }

    static <T> boolean getStatus(Data<T> data) {
        return NullablePredicates.isNotNull(data) && data.STATUS();
    }

    static <T> MethodMessageData getMethodMessageData(Data<T> data) {
        return NullablePredicates.isNull(data) ? MethodMessageDataFactory.getWithDefaults() : data.MESSAGE();
    }

    static <T> String getNameof(Data<T> data) {
        final var methodMessageData = DataFunctions.getMethodMessageData(data);
        final var status = NullablePredicates.isNotNull(methodMessageData) && StringUtilities.isVisible(methodMessageData.NAMEOF());
        return status ? methodMessageData.NAMEOF() : "";
    }

    static <T> String getMessage(Data<T> data) {
        final var methodMessageData = DataFunctions.getMethodMessageData(data);
        final var status = NullablePredicates.isNotNull(methodMessageData) && StringUtilities.isVisible(methodMessageData.MESSAGE());
        return status ? methodMessageData.MESSAGE() : "";
    }

    static <T> BiFunction<String, String, String> getFormatter(Data<T> data) {
        final var methodMessageData = DataFunctions.getMethodMessageData(data);
        final var status = NullablePredicates.isNotNull(methodMessageData) && NullablePredicates.isNotNull(methodMessageData.FORMATTER());
        return status ? methodMessageData.FORMATTER() : (first, second) -> "";
    }

    static String getFormattedMessage(Data data) {
        if (NullablePredicates.isNull(data)) {
            return "";
        }

        final BiFunction<String, String, String> formatter = DataFunctions.getFormatter(data);
        final var message = DataFunctions.getMessage(data);
        final var nameof = DataFunctions.getNameof(data);
        return formatter.apply(nameof, message);
    }

    static String getFormattedMessageWithConditionalExceptionMessage(Data data, Predicate<Data> condition) {
        var message = DataFunctions.getFormattedMessage(data);
        if (condition.test(data)) {
            message += "\tAn exception has occurred: " + data.EXCEPTION_MESSAGE() + ".\n";
        }

        return message;
    }

    static String getFormattedMessageWithExceptionMessage(Data data) {
        return DataFunctions.getFormattedMessageWithConditionalExceptionMessage(data, DataPredicates::isException);
    }

    static String getFormattedMessageWithExceptionMessageOnNonValidData(Data data) {
        final Predicate<Data> predicate = DataPredicates::isInvalidOrFalse;
        return DataFunctions.getFormattedMessageWithConditionalExceptionMessage(data, predicate.and(DataPredicates::isException));
    }

    static String getNameIfAbsent(Data<?> data, String nameof) {
        final var localNameof = StringUtilities.isVisible(nameof) ? nameof: "DataFunctions.getNameIfAbsent";
        var name = "";
        if (NullablePredicates.isNotNull(data)) {
            name = data.MESSAGE().NAMEOF();
            if (StringUtilities.uncontains(name, localNameof)) {
                name = localNameof + ": " + name;
            }
        } else {
            name = localNameof;
        }

        return name;
    }

    static <T> String getMessageFromData(T object) {
        final var nameof = "DataFunctions.getMessageFromData";
        if (NullablePredicates.isNull(object)) {
            return nameof + ": Object object was null.\n";
        }

        return ((object instanceof Data<?> data) ? DataFunctions.getFormattedMessage(data) : (nameof + ": " + object));
    }

    static <T> String getStatusMessageFromData(T object) {
        final var nameof = "DataFunctions.getMessageFromData";
        if (NullablePredicates.isNull(object)) {
            return nameof + "Object object was null.\n";
        }

        return (object instanceof Data<?> data) ? DataFunctions.getFormattedMessageWithExceptionMessageOnNonValidData(data) : (nameof + ": " + object);
    }

    private static void throwIfNullCore(String nameof, Data<?> data) {
        final var localNameof = StringUtilities.isVisible(nameof) ? nameof : "DataFunctions.throwIfNullCore";
        if (NullablePredicates.isNotNull(data)) {
            return;
        }

        throw new ArgumentNullException(localNameof + ": Data parameter was null.\n");
    }

    private static void throwIfExceptionCore(String nameof, Data<?> data) {
        final var localNameof = StringUtilities.isVisible(nameof) ? nameof : "DataFunctions.throwIfExceptionCore";
        final var exception = data.EXCEPTION();
        if (ExceptionFunctions.isNonException(exception)) {
            return;
        }

        throw new RuntimeException(localNameof + ": " + data.EXCEPTION_MESSAGE(), exception);
    }

    static void throwIfException(Data<?> data) {
        final var nameof = "DataFunctions.throwIfException";
        DataFunctions.throwIfNullCore(nameof, data);
        DataFunctions.throwIfExceptionCore(nameof, data);
    }

    static void throwIfInvalid(Data<?> data) {
        final var nameof = "DataFunctions.throwIfInvalid";
        DataFunctions.throwIfNullCore(nameof, data);
        if (DataPredicates.isInvalidOrFalse(data)) {
            DataFunctions.throwIfException(data);
            throw new IllegalStateException(nameof + ": " + getFormattedMessage(data));
        }
    }

}
