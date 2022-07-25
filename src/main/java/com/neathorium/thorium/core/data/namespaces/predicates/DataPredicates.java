package com.neathorium.thorium.core.data.namespaces.predicates;

import com.neathorium.thorium.core.data.records.Data;
import com.neathorium.thorium.exceptions.namespaces.ExceptionFunctions;
import com.neathorium.thorium.java.extensions.namespaces.predicates.BasicPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.GuardPredicates;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.BooleanUtilities;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface DataPredicates {
    static boolean isInitialized(Data<?> data) {
        return NullablePredicates.isNotNull(data);
    }

    static boolean isValid(Data<?> data) {
        return (
            isInitialized(data) &&
            NullablePredicates.isNotNull(data.EXCEPTION()) &&
            isNotBlank(data.EXCEPTION_MESSAGE()) &&
            MethodMessageDataPredicates.isValid(data.MESSAGE())
        );
    }

    static boolean isInvalid(Data<?> data) {
        return !isValid(data);
    }

    static boolean isFalse(Data<?> data) {
        return DataPredicates.isValid(data) && BooleanUtilities.isFalse(data.OBJECT());
    }

    static boolean isTrue(Data<?> data) {
        return DataPredicates.isValid(data) && BooleanUtilities.isTrue(data.OBJECT());
    }

    static boolean isFalseExecution(Data<?> data, int index, int length) {
        return isFalse(data) && BasicPredicates.isSmallerThan(index, length);
    }

    static boolean isTrueExecution(Data<?> data, int index, int length) {
        return isTrue(data) && BasicPredicates.isSmallerThan(index, length);
    }

    static boolean isValidNonFalse(Data<?> data) {
        return isValid(data) && BooleanUtilities.isTrue(data.STATUS());
    }

    static boolean isValidNonFalseAndNonNullContained(Data<?> data) {
        return isValidNonFalse(data) && NullablePredicates.isNotNull(data.OBJECT());
    }

    static boolean isInvalidOrFalse(Data<?> data) {
        return !isValidNonFalse(data);
    }

    static boolean isValidAndFalse(Data<?> data) {
        return isValid(data) && BooleanUtilities.isFalse(data.STATUS());
    }

    static <T> boolean isValidNonFalseAndValidContained(Data<T> data, Predicate<T> validator) {
        return isValidNonFalse(data) && validator.test(data.OBJECT());
    }

    static <T> boolean isValidNonFalseAndValidContained(Data<T> data, Function<T, String> validator) {
        return isValidNonFalse(data) && isNotBlank(validator.apply(data.OBJECT()));
    }

    static <T> Predicate<Data<T>> isValidNonFalseAndValidContains(Function<T, String> validator) {
        return data -> isValidNonFalseAndValidContained(data, validator);
    }

    static <T> Predicate<Data<T>> isValidNonFalseAndValidContains(Predicate<T> validator) {
        return data -> isValidNonFalseAndValidContained(data, validator);
    }

    static boolean isException(Data<?> data) {
        return NullablePredicates.isNotNull(data) && ExceptionFunctions.isException(data.EXCEPTION());
    }

    static boolean areAllInitialized(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isInitialized, data);
    }

    static boolean areAnyInitialized(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isInitialized, data);
    }

    static boolean areNoneInitialized(Data<?>... data) {
        return GuardPredicates.areNone(DataPredicates::isInitialized, data);
    }

    static boolean areAllExceptions(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isException, data);
    }

    static boolean areAnyException(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isException, data);
    }

    static boolean areNoneExceptions(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isException, data);
    }

    static boolean areAllValid(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isValid, data);
    }

    static boolean areAnyValid(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isValid, data);
    }

    static boolean areNoneValid(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isValid, data);
    }

    static boolean areAllInvalid(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isInvalid, data);
    }

    static boolean areAnyInvalid(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isInvalid, data);
    }

    static boolean areNoneInvalid(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isInvalid, data);
    }

    static boolean areAllValidNonFalse(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isValidNonFalse, data);
    }

    static boolean areAnyValidNonFalse(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isValidNonFalse, data);
    }

    static boolean areNoneValidNonFalse(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isValidNonFalse, data);
    }

    static boolean areAllTrue(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isTrue, data);
    }

    static boolean areAnyTrue(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isTrue, data);
    }

    static boolean areNoneTrue(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isTrue, data);
    }

    static boolean areAllFalse(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isFalse, data);
    }

    static boolean areAnyFalse(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isFalse, data);
    }

    static boolean areNoneFalse(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isFalse, data);
    }

    static boolean areAllInvalidOrFalse(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isInvalidOrFalse, data);
    }

    static boolean areAnyInvalidOrFalse(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isInvalidOrFalse, data);
    }

    static boolean areNoneInvalidOrFalse(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isInvalidOrFalse, data);
    }

    static boolean areAllValidAndFalse(Data<?>... data) {
        return GuardPredicates.areAll(DataPredicates::isValidAndFalse, data);
    }

    static boolean areAnyValidAndFalse(Data<?>... data) {
        return GuardPredicates.areAny(DataPredicates::isValidAndFalse, data);
    }

    static boolean areNoneValidAndFalse(Data<?> data) {
        return GuardPredicates.areNone(DataPredicates::isValidAndFalse, data);
    }
}
