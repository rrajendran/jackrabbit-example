package com.capella.jackrabbit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.jcr.*;

/**
 * Second hop example. Stores, retrieves, and removes example content.
 */

@Component
public class JackRabbitOperations {
    public static final String NODE_PATH = "hello/world";
    @Autowired
    private Repository repository;
    @Autowired
    private Session session;
    @Autowired
    @Qualifier("rootNode")
    private Node rootNode;

    public JackRabbitOperations() throws RepositoryException {

    }


    public void addText(String message) throws RepositoryException {
        //AccessControlModule.modifyNodeAccess(session, rootNode, Privilege.JCR_WRITE);
        // Store content
        final Node hello;
        if (!rootNode.hasNode(NODE_PATH)) {
            hello = rootNode.addNode(NODE_PATH);
        } else {
            hello = rootNode.getNode(NODE_PATH);
        }

        hello.setProperty("message", message);
        session.save();
    }

    public String retrieveText() throws RepositoryException {
        // Retrieve content
        if (rootNode.hasNode(NODE_PATH)) {
            Node node = rootNode.getNode("hello/world");
            return node.getProperty("message").getString();
        }
        return null;
    }


    public void removeNode() throws RepositoryException {
        NodeIterator nodes = rootNode.getNodes();
        while (nodes.hasNext()) {
            Node node = nodes.nextNode();
            node.remove();
        }
        session.save();
    }


} 