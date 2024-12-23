package project.cs3360.object;

import project.cs3360.response.object.TransactionData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TypeHandler {
    private static final Map<Class<?>, TypeAdapter<?>> adapters = new HashMap<>();

    static {
        adapters.put(String.class, String::valueOf);
        adapters.put(Integer.class, Integer::valueOf);
        adapters.put(int.class, Integer::valueOf);
        adapters.put(Double.class, Double::valueOf);
        adapters.put(double.class, Double::valueOf);
        adapters.put(Boolean.class, Boolean::valueOf);
        adapters.put(boolean.class, Boolean::valueOf);
        adapters.put(UUID.class, UUID::fromString);
        adapters.put(TransactionData.class, TransactionData::fromString);
        adapters.put(TransactionData[].class, TransactionData::fromArrayString);
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeAdapter<T> getAdapter(Class<T> type) {
        return (TypeAdapter<T>) adapters.get(type);
    }
}
