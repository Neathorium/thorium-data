package com.neathorium.thorium.core.data.namespaces.predicates;

import com.neathorium.thorium.core.data.records.MethodMessageData;
import com.neathorium.thorium.java.extensions.namespaces.predicates.NullablePredicates;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MethodMessageDataPredicates {
    static boolean isValid(MethodMessageData data) {
        return NullablePredicates.isNotNull(data) && isNotBlank(data.MESSAGE()) && NullablePredicates.isNotNull(data.NAMEOF());
    }
}
