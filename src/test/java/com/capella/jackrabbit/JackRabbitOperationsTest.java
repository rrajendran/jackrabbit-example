package com.capella.jackrabbit;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jcr.RepositoryException;
import java.io.*;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ramesh on 14/01/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JackRabbitOperationsTest {

    @Autowired
    private JackRabbitOperations jackRabbitOperations;

    @org.junit.Test
    public void testAddText() throws Exception {
        jackRabbitOperations.addText("hello", "Test message");
        String text = jackRabbitOperations.retrieveText("hello");
        assertThat(text, is("Test message"));
    }

    @Test
    public void testPdfStore() throws IOException, RepositoryException {
        URL url = JackRabbitOperations.class.getClassLoader().getResource("sample.pdf");
        File file = FileUtils.toFile(url);
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        String path = "hello/pdfs";

        jackRabbitOperations.writeBinaryFile(path, stream);
        jackRabbitOperations.readBinaryFile(path);

        File f = new File("target/Alfresco_E0_Training.pdf");
        assertThat(f.length(), is(3003160L));
    }


}