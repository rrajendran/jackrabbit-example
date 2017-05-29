package com.capella.jackrabbit.oak;

import com.capella.jackrabbit.oak.domain.OakDocument;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author ramesh.rajendran
 */
public interface OakOperations {


    OakDocument getDocumentById(String documentId);


    String saveDocument(InputStream inputStream, String documentName, String mimeType);

    void updateDocumentMetaData(Map<String, String> documentMetaData, String documentId);

    OakDocument getDocumentMetadataById(String documentId);


    void deleteDocumentById(String documentId);


    void setProperties(List<Map<String, String>> properties);


    Map<String, String> getProperty(String propertyKey);
}
