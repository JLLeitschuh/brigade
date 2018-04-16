package com.kmwllc.brigade.utils;

import com.kmwllc.brigade.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by matt on 3/27/17.
 */
public class BrigadeUtils {

    public static Map<String, String> loadPropertiesAsMap(InputStream in) throws IOException {
        Properties props = new Properties();
        props.load(in);
        in.close();
        Map<String, String> output = new HashMap<>();
        for (Object key : props.keySet()) {
            String k = key.toString();
            String propValue = props.getProperty(key.toString());
            if (System.getProperty(k) != null) {
                propValue = System.getProperty(k);
            }

            output.put(k, propValue);
        }
        return output;
    }

    public static String fileToString(InputStream in) throws IOException {
        byte[] bytes = FileUtils.toByteArray(in);
        in.close();
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }
}
