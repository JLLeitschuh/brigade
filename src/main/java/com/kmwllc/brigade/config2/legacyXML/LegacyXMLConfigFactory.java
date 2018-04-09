package com.kmwllc.brigade.config2.legacyXML;

import com.kmwllc.brigade.config2.ConfigException;
import com.kmwllc.brigade.config2.ConfigFactory;
import com.kmwllc.brigade.config2.ConnectorConfig2;
import com.kmwllc.brigade.config2.WorkflowConfig2;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class LegacyXMLConfigFactory implements ConfigFactory {
  private static Map<String, String> legacyReplacements = new HashMap<>();

  static {
    legacyReplacements.put("com.kmwllc.brigade.config.ConnectorConfig",
            "com.kmwllc.brigade.config2.legacyXML.LegacyXMLConnectorConfig");
    legacyReplacements.put("com.kmwllc.brigade.config.WorkflowConfig",
            "com.kmwllc.brigade.config2.legacyXML.LegacyXMLWorkflowConfig");
    legacyReplacements.put("com.kmwllc.brigade.config.StageConfig",
            "com.kmwllc.brigade.config2.legacyXML.LegacyXMLStageConfig");
  }

  @Override
  public WorkflowConfig2 deserializeWorkflow(Reader r) throws ConfigException {
    return new LegacyXMLWorkflowConfig().deserialize(r);
  }

  @Override
  public ConnectorConfig2 deserializeConnector(Reader r) throws ConfigException {
    return new LegacyXMLConnectorConfig().deserialize(r);
  }

  @Override
  public WorkflowConfig2 deserializeWorkflow(String s) throws ConfigException {
    for (Map.Entry<String, String> e : legacyReplacements.entrySet()) {
      s = s.replaceAll(e.getKey(), e.getValue());
    }
    return deserializeWorkflow(new StringReader(s));
  }

  @Override
  public ConnectorConfig2 deserializeConnector(String s) throws ConfigException {
    for (Map.Entry<String, String> e : legacyReplacements.entrySet()) {
      s = s.replaceAll(e.getKey(), e.getValue());
    }
    return deserializeConnector(new StringReader(s));
  }
}
