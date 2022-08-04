package idir.embag.Application.Utility;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public abstract class DataBundler {

    
    public static <K> Map<K,Object> bundleData(K[] keys, Object[] values) {
        Map<K,Object> data = new HashMap<K,Object>();
        for(int i = 0; i < keys.length; i++) {
            data.put(keys[i], values[i]);
        }
        return data;
    }

    public static <K,R> R retrieveValue(Map<K, Object> data, K key) {
        return (R) data.get(key);
    }

    public static <R,K,N> R retrieveNestedValue(Map<K, Object> data, K key, N nestedKey) {
        Map<N,Object> nestedData = (Map<N,Object>)data.get(key);

        return (R) nestedData.get(nestedKey);
    }

    public static <K,N> void bundleNestedData(Map<K,Object> data,K key, N nestedKey, Object value) {
        Map<N,Object> nestedData = new HashMap<N,Object>();
        nestedData.put(nestedKey, value);
        data.put(key, nestedData);
    }

    public static <K> void appendData(Map<K,Object> data,K key ,Object value) {
        data.put(key, value);
    }


}
