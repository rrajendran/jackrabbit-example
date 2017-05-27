package com.capella.jackrabbit;

import org.apache.jackrabbit.core.security.principal.PrincipalImpl;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.security.*;

/**
 * Created by ramesh on 12/01/2016.
 */
public class AccessControlModule {


    public static void modifyNodeAccess(Session session, Node node, String privilege) throws RepositoryException {
        AccessControlManager acm = session.getAccessControlManager();
        AccessControlPolicyIterator it =
                acm.getApplicablePolicies(node.getPath());

        while (it.hasNext()) {
            AccessControlPolicy acp = it.nextAccessControlPolicy();
            Privilege[] privileges = new Privilege[]{acm.privilegeFromName(privilege)};

            ((AccessControlList) acp).addAccessControlEntry(new PrincipalImpl(session.getUserID()), privileges);

            acm.setPolicy(node.getPath(), acp);
        }
    }
}
