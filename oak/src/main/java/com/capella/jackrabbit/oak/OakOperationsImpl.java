package com.capella.jackrabbit.oak;

import com.capella.jackrabbit.oak.domain.OakDocument;
import com.capella.jackrabbit.oak.exception.DocumentManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Ramesh Rajendran
 */
@Component
public class OakOperationsImpl implements OakOperations {

    @Autowired
    private OakFileRepository oakFileRepository;

    @Value("${jackrabbit.oak.documents.path}")
    private String documentPath;

    @Override
    public OakDocument getDocumentById(String documentId) {
        return null;
    }

    @Override
    public String saveDocument(InputStream inputStream, String documentName, String mimeType) {
        try {
            return oakFileRepository.writeBinaryFile(documentPath, inputStream, documentName, mimeType);
        } catch (Exception e) {
            throw new DocumentManagementException("Save document failed:", e);
        }
    }

    @Override
    public void updateDocumentMetaData(Map<String, String> documentMetaData, String documentId) {

    }

    @Override
    public OakDocument getDocumentMetadataById(String documentId) {
        return null;
    }

    @Override
    public void deleteDocumentById(String documentId) {

    }

    @Override
    public void setProperties(List<Map<String, String>> properties) {

    }

    @Override
    public Map<String, String> getProperty(String propertyKey) {
        return null;
    }
}
