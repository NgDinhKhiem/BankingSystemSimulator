package project.cs3360.utils;

import java.util.HashMap;
import java.util.Map;

public class JSONBuilder {
    private final Map<String, String> valueMap = new HashMap<>();

    public void addValue(String key, Object value) {
        this.valueMap.put(key, value.toString());
    }

    public String getObject(String key) {
        return valueMap.get(key);
    }

    public String build() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        int size = valueMap.size();
        int count = 0;

        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            jsonBuilder.append("\"").append(escapeString(key)).append("\"").append(":");
            jsonBuilder.append("\"").append(escapeString(value)).append("\"");

            count++;
            if (count < size) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private String escapeString(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private String unescapeString(String input) {
        return input.replace("\\\"", "\"")
                .replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t");
    }

    public void eatJson(String json) {
        valueMap.clear();

        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1); // Remove the outer braces
            String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            for (String pair : pairs) {
                String[] keyValue = pair.split(":(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", 2);
                if (keyValue.length == 2) {
                    String key = unescapeString(keyValue[0].trim().replaceAll("^\"|\"$", ""));
                    String value = unescapeString(keyValue[1].trim().replaceAll("^\"|\"$", ""));
                    valueMap.put(key, value);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid JSON string");
        }
    }

    public static JSONBuilder fromJson(String json) {
        JSONBuilder jsonBuilder = new JSONBuilder();
        jsonBuilder.eatJson(json);
        return jsonBuilder;
    }
}
