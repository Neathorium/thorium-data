package com.neathorium.thorium.core.data.records;

import java.util.function.BiFunction;

public record MethodMessageData(
    BiFunction<String, String, String> FORMATTER,
    String NAMEOF,
    String MESSAGE
) {}
