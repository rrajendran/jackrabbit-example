package com.capella.jackrabbit.oak;

import org.apache.jackrabbit.oak.jcr.Jcr;
import org.apache.jackrabbit.oak.segment.SegmentNodeStore;
import org.apache.jackrabbit.oak.segment.SegmentNodeStoreBuilders;
import org.apache.jackrabbit.oak.segment.file.FileStoreBuilder;
import org.apache.jackrabbit.oak.segment.file.InvalidFileStoreVersionException;
import org.springframework.stereotype.Component;

import javax.jcr.Credentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.File;
import java.io.IOException;

/**
 * @author Ramesh Rajendran
 */
@Component
public class OakSessionFactory {
    private OakSessionFactory() {
    }

    public OakSessionFactory create() {
        return new OakSessionFactory();
    }

    public Session getSession(String path, Credentials credentials) throws IOException, InvalidFileStoreVersionException, RepositoryException {
        org.apache.jackrabbit.oak.segment.file.FileStore fs = FileStoreBuilder.fileStoreBuilder(new File(path)).build();
        SegmentNodeStore ns = SegmentNodeStoreBuilders.builder(fs).build();
        Repository repo = new Jcr(ns).createRepository();

        return repo.login(credentials);
    }
}
