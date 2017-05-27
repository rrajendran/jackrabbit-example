package com.capella.jackrabbit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import javax.jcr.security.Privilege;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static javax.jcr.Property.JCR_DATA;
import static javax.jcr.nodetype.NodeType.NT_FILE;
import static javax.jcr.nodetype.NodeType.NT_RESOURCE;

/**
 * Second hop example. Stores, retrieves, and removes example content.
 */

@Component
public class JackRabbitOperations {
    @Autowired
    @Qualifier("repository")
    private Repository repository;

    @Autowired
    private Session session;

    @Autowired
    @Qualifier("rootNode")
    private Node rootNode;

    public JackRabbitOperations() throws RepositoryException {

    }


    /**
     * Add text to node
     *
     * @param message
     * @throws RepositoryException
     */
    public void addText(String path, String message) throws RepositoryException {
        AccessControlModule.modifyNodeAccess(session, rootNode, Privilege.JCR_WRITE);
        // Store content
        final Node hello;
        if (!rootNode.hasNode(path)) {
            hello = rootNode.addNode(path);
        } else {
            hello = rootNode.getNode(path);
        }

        hello.setProperty("message", message);
        session.save();
    }

    /**
     * Retrive stored text
     *
     * @return
     * @throws RepositoryException
     */
    public String retrieveText(String path) throws RepositoryException {
        // Retrieve content
        if (rootNode.hasNode(path)) {
            Node node = rootNode.getNode(path);
            return node.getProperty("message").getString();
        }
        return null;
    }

    /**
     * Remove Node
     *
     * @throws RepositoryException
     */
    public void removeNode(String path) throws RepositoryException {
        if (rootNode.hasNode(path)) {
            Node node = rootNode.getNode(path);
            node.remove();
            session.save();
        }

        System.out.println("******" + rootNode.hasNode(path));

    }

    /**
     * Store binary file
     *
     * @throws RepositoryException
     * @throws FileNotFoundException
     */
    public String writeBinaryFile(String path, InputStream inputStream) throws RepositoryException, FileNotFoundException {
        Node folder;

        if (!rootNode.hasNode(path)) {
            folder = rootNode.addNode(path);
        } else {
            folder = rootNode.getNode(path);
        }

        Node nodeFile = folder.addNode("Article.pdf", NT_FILE);
        Node content = nodeFile.addNode(Property.JCR_CONTENT, NT_RESOURCE);
        Binary binary = session.getValueFactory().createBinary(inputStream);

        content.setProperty(Property.JCR_DATA, binary);
        content.setProperty(Property.JCR_MIMETYPE, "application/pdf");
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
} 