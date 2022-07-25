package com.neathorium.thorium.core.data.constants;

import com.neathorium.thorium.core.data.namespaces.factories.DataFactoryFunctions;
import com.neathorium.thorium.core.data.records.Data;


public abstract class CoreDataConstants {
    public static final Data<Boolean> NULL_BOOLEAN = DataFactoryFunctions.getInvalidBooleanWith("nullBoolean", "nullBoolean data.\n");
    public static final Data<Boolean> DATA_PARAMETER_WAS_NULL = DataFactoryFunctions.getInvalidBooleanWith("dataParameterWasNull", "Data parameter was null.\n");
    public static final Data<Boolean> PARAMETERS_NULL_BOOLEAN = DataFactoryFunctions.getInvalidBooleanWith("parametersNullBoolean", "Parameters was null.\n");

    public static final Data<Integer> NULL_INTEGER = DataFactoryFunctions.getInvalidWith(0, "nullInteger", "nullInteger data.\n");

    public static final Data<Object> NULL_OBJECT = DataFactoryFunctions.getInvalidWith(CoreConstants.STOCK_OBJECT, "nullObject", "null object data.\n");

    public static final Data<Object[]> NULL_PARAMETER_ARRAY = DataFactoryFunctions.getInvalidWith(CoreConstants.EMPTY_OBJECT_ARRAY, "nullParameterArray", "Null Parameter Array.\n");

    public static final Data<String> NULL_STRING = DataFactoryFunctions.getInvalidWith("", "nullString", "nullString data.\n");

    public static final Data<Void> NULL_VOID = DataFactoryFunctions.getInvalidWith(null, "nullVoid", "Void data.\n");
}
