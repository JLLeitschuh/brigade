package com.kmwllc.brigade.config2;

import com.kmwllc.brigade.config2.json.JsonConfigFactory;
import com.kmwllc.brigade.config2.legacyXML.LegacyXMLConfigFactory;

import java.io.Reader;

public interface ConfigFactory {
    String JSON = "json";
    String LEGACY_XML = "legacy_xml";

    static ConfigFactory instance(String type) throws ConfigException {
        switch (type) {
            case JSON:
                return new JsonConfigFactory();
            case LEGACY_XML:
                return new LegacyXMLConfigFactory();
            default:
                throw new ConfigException("Unknown ConfigFactory type");
        }
    }

    WorkflowConfig2 deserializeWorkflow(Reader r) throws ConfigException;
    ConnectorConfig2 deserializeConnector(Reader r) throws ConfigException;
}