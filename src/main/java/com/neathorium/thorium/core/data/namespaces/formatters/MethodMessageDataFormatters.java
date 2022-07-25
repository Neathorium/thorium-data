package com.neathorium.thorium.core.data.namespaces.formatters;

import org.apache.commons.lang3.StringUtils;

public interface MethodMessageDataFormatters {
    static String getMethodMessageDataFormatted(String nameof, String message) {
        final var separator = ": ";
        return StringUtils.startsWithIgnoreCase(message, nameof + separator) ? message : nameof + separator + message;
    }
}
