package com.neathorium.thorium.core.data.namespaces.formatters;

import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;
import org.apache.commons.lang3.StringUtils;

public interface MethodMessageDataFormatters {
    static String getMethodMessageDataFormatted(String nameof, String message) {
        var errorMessage = "";
        if (StringUtilities.isInvisible(nameof)) {
            errorMessage += "Nameof parameter was blank.\n";
        }

        if (StringUtilities.isInvisible(message)) {
            errorMessage += "Message parameter was blank.\n";
        }

        if (StringUtilities.isVisible(errorMessage)) {
            return "";
        }

        final var separator = ": ";
        return StringUtils.startsWithIgnoreCase(message, nameof + separator) ? message : nameof + separator + message;
    }
}
