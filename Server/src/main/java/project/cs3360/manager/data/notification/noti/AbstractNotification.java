package project.cs3360.manager.data.notification.noti;

import project.cs3360.utils.JSONBuilder;

import java.lang.reflect.Field;
import java.util.UUID;

public abstract class AbstractNotification {
    private final UUID uuid = UUID.randomUUID();
    private final String type;

    protected AbstractNotification() {
        this.type = this.getClass().getSimpleName();
    }

    public String toJSON(){
        JSONBuilder jsonBuilder = new JSONBuilder();
        for(Field field:this.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                jsonBuilder.addValue(field.getName(), field.get(this).toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(false);
        }
        return (jsonBuilder.build());
    }
}
