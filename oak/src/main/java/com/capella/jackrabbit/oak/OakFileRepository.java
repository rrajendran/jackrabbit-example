package com.capella.jackrabbit.oak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static javax.jcr.Property.JCR_DATA;
import static javax.jcr.nodetype.NodeType.NT_FILE;
import static javax.jcr.nodetype.NodeType.NT_RESOURCE;

/**
 * @author Ramesh Rajendran
 */
@Component
public class OakFileRepository {

    private Session session;

    @Autowired
    public OakFileRepository(Session session) {
        this.session = session;
    }

    /**
     * Store binary file
     *
     * @throws RepositoryException
     * @throws FileNotFoundException
     */
    public String writeBinaryFile(String path, InputStream inputStream, String documentName, String mimeType) throws RepositoryException {
        Node folder;
        Node rootNode = session.getRootNode();
        if (!rootNode.hasNode(path)) {
            folder = rootNode.addNode(path);
        } else {
            folder = rootNode.getNode(path);
        }

        Node nodeFile = folder.addNode(documentName, NT_FILE);
        Node content = nodeFile.addNode(Property.JCR_CONTENT, NT_RESOURCE);
        content.setProperty(Property.JCR_MIMETYPE, mimeType);

        Binary binary = session.getValueFactory().createBinary(inputStream);
        content.setProperty(Property.JCR_DATA, binary);

        return content.getIdentifier();

    }

    /**
     * Read binary file
     *
     * @throws RepositoryException
     */
    public InputStream readBinaryFile(String identifier) throws RepositoryException {
        Node nodeByIdentifier = session.getNodeByIdentifier(identifier);
        Binary binary = nodeByIdentifier.getProperty(JCR_DATA).getBinary();
        return binary.getStream();
    }


    public Map<String, Object> getProperties(String uuid) throws RepositoryException {
        Node nodeByIdentifier = session.getNodeByIdentifier(uuid);
        PropertyIterator properties = nodeByIdentifier.getProperties();
        Map<String, Object> map = new HashMap<>();
        while (properties.hasNext()) {
            Property property = properties.nextProperty();
            if (property.getName().equals("ho:name"))
                map.put(property.getName(), property.getString());
        }
        return map;
    }
}
