package com.neathorium.thorium.core.data.namespaces.formatters;

import org.apache.commons.lang3.StringUtils;

public interface MethodMessageDataFormatters {
    static String getMethodMessageDataFormatted(String nameof, String message) {
        var errorMessage = "";
        if (StringUtils.isBlank(nameof)) {
            errorMessage += "Nameof parameter was blank.\n";
        }

        if (StringUtils.isBlank(message)) {
            errorMessage += "Message parameter was blank.\n";
        }

        if (StringUtils.isNotBlank(errorMessage)) {
            return "";
        }

        final var separator = ": ";
        return StringUtils.startsWithIgnoreCase(message, nameof + separator) ? message : nameof + separator + message;
    }
}
