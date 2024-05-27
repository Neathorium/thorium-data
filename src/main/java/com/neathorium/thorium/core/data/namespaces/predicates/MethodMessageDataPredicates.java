package com.neathorium.thorium.core.data.namespaces.predicates;

import com.neathorium.thorium.core.data.records.MethodMessageData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;
import com.neathorium.thorium.java.extensions.namespaces.utilities.StringUtilities;

public interface MethodMessageDataPredicates {
    static boolean isValid(MethodMessageData data) {
        return NullablePredicates.isNotNull(data) && StringUtilities.isVisible(data.MESSAGE()) && NullablePredicates.isNotNull(data.NAMEOF());
    }
}
