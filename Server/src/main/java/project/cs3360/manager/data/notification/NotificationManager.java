package project.cs3360.manager.data.notification;

import project.cs3360.manager.data.notification.noti.AbstractNotification;
import project.cs3360.response.NotificationResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationManager {
    private final Map<String, List<AbstractNotification>> cacheNotification = new HashMap<>();

    public List<AbstractNotification> getNotification(String ID){
        if(cacheNotification.get(ID)==null)
            return new ArrayList<>();
        return cacheNotification.get(ID);
    }

    public NotificationResponse getNotificationResponse(String ID){
        List<AbstractNotification> notifications = getNotification(ID);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for(AbstractNotification notification : notifications){
            stringBuilder.append(notification.toJSON());
        }
        stringBuilder.append("}");
        clearNotification(ID);
        return new NotificationResponse(stringBuilder.toString());
    }

    public void clearNotification(String ID){cacheNotification.remove(ID);}

    public void pushNotification(AbstractNotification notification, String... receiversID){
        for(String receiverID : receiversID){
            addNotification(receiverID, notification);
        }
    }

    private void addNotification(String ID, AbstractNotification notification){
        cacheNotification.computeIfAbsent(ID, k -> new ArrayList<>());
        cacheNotification.get(ID).add(notification);
    }
}
