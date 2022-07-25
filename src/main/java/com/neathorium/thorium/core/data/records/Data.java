package com.neathorium.thorium.core.data.records;

/**
 * The {@code Data} class is a classic data class; the heart of the command/pipe pattern associated.
 * Used for holding multiple return values from a function, as well, meaning essentially a named tuple.
 *
 * The class can hold an exception, and its message as optional parameters.
 *
 * @param <T>
 */
/**
 * Allocates a new {@code Data} instance, so that it represents a:
 * - T type Object,
 * - its status during any kind of execution,
 * - a {@code MethodMessageData} instance describing status,
 * - an exception that might've occurred, or needs to be passed,
 * - an exception message, that is either of the passed, or an overridden.
 *
 * @param OBJECT
 *        A {@code T} type.
 * @param STATUS
 *        A {@code boolean}.
 * @param MESSAGE
 *        A {@code MethodMessageData}.
 * @param EXCEPTION
 *        A {@code Exception} instance, if any exception occurred, or needs to be passed along the object.
 * @param EXCEPTION_MESSAGE
 *        A {@code String} of the exception, or an overriding message for the exception.

 */
public record Data<T> (T OBJECT, boolean STATUS, MethodMessageData MESSAGE, Exception EXCEPTION, String EXCEPTION_MESSAGE) {}
