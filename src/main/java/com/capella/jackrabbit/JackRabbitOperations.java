package com.capella.jackrabbit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import javax.jcr.security.Privilege;
import java.io.*;

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
    public void writeBinaryFile(String path, InputStream inputStream) throws RepositoryException, FileNotFoundException {
        Node folder = null;

        if (!rootNode.hasNode(path)) {
            folder = rootNode.addNode(path);
        } else {
            folder = rootNode.getNode(path);
        }

        Node nodeFile = folder.addNode("Article.pdf", "nt:file");
        Node content = nodeFile.addNode("jcr:content", "nt:resource");
        Binary binary = session.getValueFactory().createBinary(inputStream);
        content.setProperty("jcr:data", binary);
        content.setProperty("jcr:mimeType", "application/pdf");

    }

    /**
     * Read binary file
     *
     * @throws RepositoryException
     * @throws IOException
     */
    public void readBinaryFile(String path) throws RepositoryException, IOException {
        Node node = rootNode.getNode(path);
        Node file = node.getNode("Article.pdf");
        Node content = file.getNode("jcr:content");
        String contentPath = content.getPath();
        Binary bin = session.getNode(contentPath).getProperty("jcr:data").getBinary();

        InputStream stream = bin.getStream();
        File f = new File("target/Alfresco_E0_Training.pdf");

        OutputStream out = new FileOutputStream(f);
        byte buf[] = new byte[1024];
        int len;
        while ((len = stream.read(buf)) > 0)
            out.write(buf, 0, len);
        out.close();
        stream.close();
        System.out.println("\nFile is created...................................");

    }
} 