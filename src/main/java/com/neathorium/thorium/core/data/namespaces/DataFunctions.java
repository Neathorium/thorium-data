package com.neathorium.thorium.core.data.namespaces;

import com.neathorium.thorium.core.data.namespaces.predicates.DataPredicates;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.core.data.records.MethodMessageData;
import com.neathorium.thorium.exceptions.exception.ArgumentNullException;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;
import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFunctions {
    static <T> T getObject(Data<T> data) {
        return data.OBJECT();
    }

    static <T> boolean getStatus(Data<T> data) {
        return data.STATUS();
    }

    static <T> MethodMessageData getMethodMessageData(Data<T> data) {
        return data.MESSAGE();
    }

    static String getFormattedMessage(Data data) {
        final var message = data.MESSAGE();
        return message.FORMATTER().apply(message.NAMEOF(), message.MESSAGE());
    }

    static String getNameIfAbsent(Data<?> data, String nameof) {
        var name = "";
        if (NullablePredicates.isNotNull(data)) {
            name = data.MESSAGE().NAMEOF();
            if (StringUtilities.uncontains(name, nameof)) {
                name = nameof + ": " + name;
            }
        } else {
            name = isNotBlank(nameof) ? nameof : "getNameIfAbsent";
        }

        return name;
    }

    static <T> String getMessageFromData(T object) {
        if (NullablePredicates.isNull(object)) {
            return "Object object was null.\n";
        }

        if (BooleanUtilities.isFalse(object instanceof Data)) {
            return String.valueOf(object);
        }

        final var data = ((Data<?>) object);
        return getFormattedMessage(data);
    }

    static <T> String getStatusMessageFromData(T object) {
        if (NullablePredicates.isNull(object)) {
            return "Object object was null.\n";
        }

        if (!(object instanceof Data)) {
            return String.valueOf(object);
        }

        final var data = (Data<?>) object;
        final var messageData = data.MESSAGE();
        var message = messageData.FORMATTER().apply(messageData.NAMEOF(), messageData.MESSAGE());
        if (DataPredicates.isInvalidOrFalse(data) && DataPredicates.isException(data)) {
            message += "\tAn exception has occurred: " + data.EXCEPTION_MESSAGE() + ".\n";
        }

        return message;
    }

    private static void throwIfNullCore(String name, Data<?> data) {
        if (NullablePredicates.isNotNull(data)) {
            return;
        }

        final var nameof = isNotBlank(name) ? name : "throwIfNullCore";
        throw new ArgumentNullException(nameof + ": Data parameter was null.\n");
    }

    private static void throwIfExceptionCore(String name, Data<?> data) {
        final var exception = data.EXCEPTION();
        if (ExceptionFunctions.isNonException(exception)) {
            return;
        }

        final var nameof = isNotBlank(name) ? name : "throwIfExceptionCore";
        throw new RuntimeException(nameof + ": " + data.EXCEPTION_MESSAGE(), exception);
    }

    static void throwIfException(Data<?> data) {
        final var nameof = "throwIfException";
        throwIfNullCore(nameof, data);
        throwIfExceptionCore(nameof, data);
    }

    static void throwIfInvalid(Data<?> data) {
        final var nameof = "throwIfInvalid";
        throwIfNullCore(nameof, data);
        if (DataPredicates.isInvalidOrFalse(data)) {
            throwIfException(data);
            throw new IllegalStateException(nameof + ": " + getFormattedMessage(data));
        }
    }
}
