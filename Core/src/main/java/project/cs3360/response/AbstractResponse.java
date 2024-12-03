package project.cs3360.response;

import project.cs3360.object.TypeHandler;
import project.cs3360.utils.JSONBuilder;

import java.lang.reflect.Field;

import static project.cs3360.socketserver.utils.Utils.joinString;

public abstract class AbstractResponse {
    private static final String delimiter = "<CA>";
    public String serialize(){
        return joinString(this.getClass().getName(),delimiter,toJSON());
    }

    public static <T extends AbstractResponse> T deserialize(String json) {
        try {
            String[] arr = json.split(delimiter, 3);
            if (arr.length != 2) {
                System.out.println(arr.length);
                System.out.println(json);
                throw new IllegalArgumentException("Invalid JSON format");
            }

            String className = arr[0];
            String jsonObject = arr[1];

            Class<?> clazz = Class.forName(className);
            if (!AbstractResponse.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Class " + className + " is not a subclass of AbstractResponse");
            }

            @SuppressWarnings("unchecked")
            T response = (T) clazz.getDeclaredConstructor().newInstance();

            JSONBuilder jsonBuilder = JSONBuilder.fromJson(jsonObject);

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldValue = jsonBuilder.getObject(field.getName());
                if (fieldValue != null) {
                    field.set(response, TypeHandler.getAdapter(field.getType()).convert(fieldValue));
                }
                field.setAccessible(false);
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON", e);
        }
    }

    public String toJSON(){
        JSONBuilder jsonBuilder = new JSONBuilder();
        for(Field field:this.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                jsonBuilder.addValue(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(false);
        }
        return (jsonBuilder.build());
    }
}