package com.kmwllc.brigade.stage;

import java.util.List;

import com.kmwllc.brigade.config.StageConfig;
import com.kmwllc.brigade.document.Document;

public class DeleteField extends AbstractStage {

  private String fieldName = "field_to_delete";

  @Override
  public void startStage(StageConfig config) {
    fieldName = config.getProperty("fieldName", fieldName);
  }

  @Override
  public List<Document> processDocument(Document doc) {
    // TODO Auto-generated method stub
    if (doc.hasField(fieldName)) {
      doc.removeField(fieldName);
    }
    return null;
  }

  @Override
  public void stopStage() {
    // TODO Auto-generated method stub

  }

  @Override
  public void flush() {
    // TODO Auto-generated method stub

  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

}
