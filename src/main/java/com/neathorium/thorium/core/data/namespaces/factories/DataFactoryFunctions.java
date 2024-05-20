package com.neathorium.thorium.core.data.namespaces.factories;

import com.neathorium.thorium.core.data.records.MethodMessageData;
import com.neathorium.thorium.core.data.constants.CoreDataConstants;
import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.exceptions.constants.ExceptionConstants;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataFactoryFunctions {
    static <T> Data<T> getWith(T object, boolean status, MethodMessageData messageData, Exception exception, String exceptionMessage) {
        final var errorMessage = NullablePredicates.isNull(messageData) ? "Method Message Data was null.\n" : "";
        if (isNotBlank(errorMessage)) {
            throw new IllegalArgumentException(errorMessage);
        }

        final var exceptionNullMessage = ExceptionConstants.EXCEPTION_WAS_NULL;
        final var isExceptionNull = BooleanUtilities.isTrue(NullablePredicates.isNull(exception));

        var message = messageData.MESSAGE();
        if (isExceptionNull) {
            message = exceptionNullMessage + message;
        }

        var localExceptionMessage = exceptionMessage;
        if (isBlank(localExceptionMessage)) {
            localExceptionMessage = isExceptionNull ? exceptionNullMessage : exception.getMessage();
        }

        final var methodMessage = MethodMessageDataFactory.getWith(messageData.NAMEOF(), message);
        return new Data<>(object, status, methodMessage, exception, localExceptionMessage);
    }

    static <T> Data<T> getWith(T object, boolean status, MethodMessageData messageData, Exception exception) {
        return DataFactoryFunctions.getWith(object, status, messageData, exception, exception.getLocalizedMessage());
    }

    static <T> Data<T> getWith(T object, boolean status, MethodMessageData messageData) {
        return DataFactoryFunctions.getWith(object, status, messageData, ExceptionConstants.EXCEPTION);
    }

    static <T> Data<T> getWith(T object, boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        return DataFactoryFunctions.getWith(object, status, MethodMessageDataFactory.getWith(nameof, message), exception, exceptionMessage);
    }

    static <T> Data<T> getWith(T object, boolean status, String nameof, String message, Exception exception) {
        return DataFactoryFunctions.getWith(object, status, MethodMessageDataFactory.getWith(nameof, message), exception);
    }

    static <T> Data<T> getWith(T object, boolean status, String nameof, String message) {
        return DataFactoryFunctions.getWith(object, status, MethodMessageDataFactory.getWith(nameof, message));
    }

    static <T> Data<T> getWith(T object, boolean status, String message, Exception exception, String exceptionMessage) {
        return DataFactoryFunctions.getWith(object, status, MethodMessageDataFactory.getWithMessage(message), exception, exceptionMessage);
    }

    static <T> Data<T> getWith(T object, boolean status, String message, Exception exception) {
        return DataFactoryFunctions.getWith(object, status, MethodMessageDataFactory.getWithMessage(message), exception);
    }

    static <T> Data<T> getWith(T object, boolean status, String message) {
        return DataFactoryFunctions.getWith(object, status, MethodMessageDataFactory.getWithMessage(message));
    }

    static <T> Data<T> getValidWith(T object, String nameof, String message) {
        return DataFactoryFunctions.getWith(object, true, nameof, message);
    }

    static <T> Data<T> getInvalidWith(T object, String nameof, String message) {
        return DataFactoryFunctions.getWith(object, false, nameof, message);
    }

    static <T> Data<T> getInvalidWith(T object, String nameof, String message, Exception exception) {
        return DataFactoryFunctions.getWith(object, false, nameof, message, exception);
    }


    static Data<Boolean> getInvalidBooleanWith(String nameof, String message) {
        return DataFactoryFunctions.getWith(false, false, nameof, message);
    }

    static Data<Boolean> getInvalidBooleanWith(String nameof, String message, Exception exception) {
        return DataFactoryFunctions.getWith(false, false, nameof, message, exception);
    }

    static <T> Data<T> getErrorFunction(T object, String nameof, String message) {
        return DataFactoryFunctions.getInvalidWith(object, nameof, message);
    }

    static <T> Data<T> getErrorFunction(T object, String message) {
        return DataFactoryFunctions.getErrorFunction(object, "getErrorFunction", message);
    }

    static <T> Data<T> replaceNameAndMessage(Data<T> data, String nameof, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), nameof, message, data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceMethodMessageAndName(Data<T> data, String nameof, MethodMessageData messageData) {
        return DataFactoryFunctions.replaceNameAndMessage(data, nameof, messageData.MESSAGE());
    }

    static <T> Data<T> replaceMethodMessageData(Data<T> data, MethodMessageData message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), message, data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceObject(Data<?> data, T object) {
        return DataFactoryFunctions.getWith(object, data.STATUS(), data.MESSAGE(), data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceStatus(Data<T> data, boolean status) {
        return DataFactoryFunctions.getWith(data.OBJECT(), status, data.MESSAGE(), data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), data.MESSAGE(), data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceObjectAndMessage(Data<?> data, T object, String message) {
        return DataFactoryFunctions.getWith(object, data.STATUS(), data.MESSAGE().NAMEOF(), message, data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceObjectStatusAndName(Data<?> data, T object, boolean status, String nameof) {
        return DataFactoryFunctions.getWith(object, status, nameof, data.MESSAGE().MESSAGE(), data.EXCEPTION());
    }

    static <T> Data<T> replaceStatusAndMessage(Data<T> data, boolean status, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), status, data.MESSAGE().NAMEOF(), message, data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceName(Data<T> data, String nameof) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), nameof, data.MESSAGE().MESSAGE(), data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> prependMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), message + data.MESSAGE(), data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> prependMessage(Data<T> data, String nameof, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), nameof, message + data.MESSAGE().MESSAGE(), data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> replaceMessage(Data<T> data, String nameof, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), nameof, message, data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> appendMessage(Data<T> data, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), data.MESSAGE() + message, data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static <T> Data<T> appendMessage(Data<T> data, String nameof, String message) {
        return DataFactoryFunctions.getWith(data.OBJECT(), data.STATUS(), nameof, data.MESSAGE().MESSAGE() + message, data.EXCEPTION(), data.EXCEPTION_MESSAGE());
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message, Exception exception, String exceptionMessage) {
        return NullablePredicates.areNotNull(message, nameof, exception, exceptionMessage) && status ? (
            DataFactoryFunctions.getWith(status, status, nameof, message, exception, exceptionMessage)
        ) : CoreDataConstants.NULL_BOOLEAN;
    }

    static Data<Boolean> getBoolean(boolean status, String message, Exception exception, String exceptionMessage) {
        return DataFactoryFunctions.getWith(status, status, message, exception, exceptionMessage);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message, Exception exception) {
        return DataFactoryFunctions.getWith(status, status, nameof, message, exception);
    }

    static Data<Boolean> getBoolean(boolean status, String message, Exception exception) {
        return DataFactoryFunctions.getWith(status, status, message, exception);
    }

    static Data<Boolean> getBoolean(boolean status, String nameof, String message) {
        return DataFactoryFunctions.getWith(status, status, nameof, message);
    }

    static Data<Boolean> getBoolean(boolean status, String message) {
        return DataFactoryFunctions.getWith(status, status, message);
    }

    static Data<String> getString(String object, String message) {
        return DataFactoryFunctions.getWith(object, isNotBlank(object), message);
    }

    static Data<String> getString(String object, String nameof, String message) {
        return DataFactoryFunctions.getWith(object, isNotBlank(object), nameof, message);
    }

    static Data<String> getString(String object, String message, Exception exception, String exceptionMessage) {
        return DataFactoryFunctions.getWith(object, isNotBlank(object), message, exception, exceptionMessage);
    }

    static Data<Object[]> getArrayWithName(Object[] object, boolean status, String nameof) {
        return NullablePredicates.areNotNull(object, nameof) ? (
            DataFactoryFunctions.getWith(object, status, nameof, "Element was found. Array of length " + object.length + " was constructed.\n")
        ) : CoreDataConstants.NULL_PARAMETER_ARRAY;
    }

    static Data<Object[]> getArrayWithName(Object[] object) {
        return NullablePredicates.isNotNull(object) ? (
            DataFactoryFunctions.getWith(object, true, "getArrayWithName", "Element was found. Array of length " + object.length + " was constructed.\n")
        ) : CoreDataConstants.NULL_PARAMETER_ARRAY;
    }

    static Data<Boolean> getStatusBoolean(Data<?> data) {
        return DataFactoryFunctions.replaceObject(data, data.STATUS());
    }
}
