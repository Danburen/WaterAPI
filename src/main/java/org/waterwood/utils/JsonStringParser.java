package org.waterwood.utils;

import com.google.gson.*;

import java.util.*;

/**
 * A class to parse other value to json
 * <P></P> in order to simply store json
 * @since 1.1.0
 * @author Danburen
 */
public class JsonStringParser {
    private static Gson gson = new Gson();
    static{
        Gson gson = new Gson();
    }

    /**
     * Convert List of HashSet to Json string
     * @param sets list of hashsets
     * @param keyNames key names of each hashset
     * @return json string
     */
    public static String hashSetToJson(List<HashSet<?>> sets,String... keyNames){
        JsonObject jsonObject = new JsonObject();

        for(int i = 0 ; i < Math.min(sets.size(),keyNames.length) ; i++){
            JsonArray jsonArray = new JsonArray();
            for(Object o : sets.get(i)){
                jsonArray.add(new Gson().toJsonTree(o));
            }
            jsonObject.add(keyNames[i],jsonArray);
        }
        return jsonObject.toString();
    }

    /**
     * Convert HashSet to Json Array String
     * @param set hash set
     * @return json array string
     */
    public static String hashSetToJsonArray(Set<?> set){
        return new Gson().toJsonTree(set).toString();
    }

    /**
     * Parse json string to list of string hash map
     * @param json json string
     * @return map of string hash map
     */
    public static Map<String,HashSet<String>> JsonToStringHashMap(String json){
        Map<String,HashSet<String>> map = new HashMap<>();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
            String key = entry.getKey();
            JsonArray value = entry.getValue().getAsJsonArray();

            HashSet<String> set = new HashSet<>();
            for(JsonElement e : value){
                set.add(gson.fromJson(e, String.class));
            }
            map.put(key,set);
        }
        return map;
    }
}
