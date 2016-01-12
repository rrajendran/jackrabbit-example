package com.capella.jackrabbit;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.security.*;

/**
 * Second hop example. Stores, retrieves, and removes example content.
 */
public class SecondHop {

    /**
     * The main entry point of the example application.
     *
     * @param args command line arguments (ignored)
     * @throws Exception if an error occurs
     */
    public static void main(String[] args) throws Exception {
        Repository repository = new TransientRepository();
        //JackrabbitSession jackrabbitSession = (JackrabbitSession) session;
        //User user = jackrabbitSession.getUserManager().createUser("admin", "admin");
        Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));


        try {
            Node root = session.getRootNode();
            AccessControlManager acm = session.getAccessControlManager();
            AccessControlPolicyIterator it =
                    acm.getApplicablePolicies(root.getPath());

            while (it.hasNext()) {
                AccessControlPolicy acp = it.nextAccessControlPolicy();
                Privilege[] privileges = new Privilege[]{acm.privilegeFromName(Privilege.JCR_WRITE)};

                ((AccessControlList) acp).addAccessControlEntry(new PrincipalImpl(session.getUserID()), privileges);

                acm.setPolicy(root.getPath(), acp);
            }
            // Store content 
            Node hello = root.addNode("hello");
            Node world = hello.addNode("world");
            world.setProperty("message", "Hello, World!");
            session.save();

            // Retrieve content 
            Node node = root.getNode("hello/world");
            System.out.println(node.getPath());
            System.out.println(node.getProperty("message").getString());

            // Remove content 
            root.getNode("hello").remove();
            session.save();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.logout();
        }
    }

} 