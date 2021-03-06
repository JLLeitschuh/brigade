package com.kmwllc.brigade;

import com.kmwllc.brigade.config.ConfigException;
import com.kmwllc.brigade.config.ConnectorConfig;
import com.kmwllc.brigade.config.JsonHandlerConfig;
import com.kmwllc.brigade.config.json.JsonConnectorConfig;
import com.kmwllc.brigade.config.json.JsonJsonHandlerConfig;
import com.kmwllc.brigade.connector.json.JsonPathJsonHandler;
import com.kmwllc.brigade.document.Document;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by matt on 3/27/17.
 */
public class JsonPathJsonHandlerTest {

    //@Test
    public void testTemp() {
        ConnectorConfig jcc = new JsonConnectorConfig("test",
                "com.kwmllc.brigade.connector.JsonConnector");
        JsonHandlerConfig jhc = new JsonJsonHandlerConfig();
        jhc.setStringParam("docPath", "$.faqs");
        Map<String, String> fieldPaths = new HashMap<>();
        fieldPaths.put("field1", "$.field1");
        fieldPaths.put("field2", "$.field2");
        jhc.setMapParam("fieldPaths", fieldPaths);
        jcc.setObjectParam("jsonHandler", jhc);
        List<String> idFields = new ArrayList<>();
        idFields.add("faqid");
        jcc.setListParam("idFields", idFields);
        StringWriter sw = new StringWriter();
        try {
            jcc.serialize(sw);
        } catch (ConfigException e) {
            e.printStackTrace();
            fail();
        }
        System.out.println(sw.toString());
        sw = new StringWriter();
        ConnectorConfig connectorConfig = null;
        try {
            connectorConfig = jcc.deserialize(new StringReader(sw.toString()));
        } catch (ConfigException e) {
            e.printStackTrace();
            fail();
        }
        System.out.println(connectorConfig.getClass());
    }

    @Test
    public void testJsonPath() {
        JsonPathJsonHandler handler = new JsonPathJsonHandler();
        InputStream in = JsonPathJsonHandlerTest.class.getClassLoader().getResourceAsStream("test.json");
        handler.setDocPath("$.faqs");
        Map<String, String> fieldPathMap = new HashMap<>();
        fieldPathMap.put("faqid", "$.faqid");
        fieldPathMap.put("owner", "$.owner.name");
        fieldPathMap.put("keywords", "$.keywords..name");
        handler.setFieldPaths(fieldPathMap);
        handler.setIdPattern("fa_%s");
        List<String> idFieldList = new ArrayList<>();
        idFieldList.add("faqid");
        handler.setIdFields(idFieldList);

        List<Document> docs = null;
        try {
            docs = handler.parseJson(new InputStreamReader(in));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(8, docs.size());
        Document d1 = docs.get(0);
        assertEquals("Meghan Dolan", d1.getField("owner").get(0));
        assertEquals(4, d1.getField("keywords").size());
        assertEquals(true, d1.getField("keywords").contains("pharmaceuticals"));
        assertEquals("fa_101657", d1.getId());
    }
}
