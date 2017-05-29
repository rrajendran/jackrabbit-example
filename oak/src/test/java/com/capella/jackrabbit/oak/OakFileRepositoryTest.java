package com.capella.jackrabbit.oak;

import org.apache.commons.io.FileUtils;
import org.apache.tika.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jcr.RepositoryException;
import java.io.*;
import java.net.URL;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Ramesh Rajendran
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class OakFileRepositoryTest {

    @Autowired
    private OakFileRepository oakFileRepository;

    @Test
    public void testPdfStore() throws IOException, RepositoryException {
        URL url = OakFileRepositoryTest.class.getClassLoader().getResource("sample.pdf");
        File file = FileUtils.toFile(url);
        InputStream stream = new BufferedInputStream(new FileInputStream(file));
        String path = "hello/pdfs";

        String identifier = oakFileRepository.writeBinaryFile(path, stream, "sample.pdf", "application/pdf");
        System.out.println(identifier);
        assertThat(identifier, is(notNullValue()));

        //InputStream inputStream = jackRabbitOperations.readBinaryFile(path);

        InputStream fileByIdentifier = oakFileRepository.readBinaryFile(identifier);


        IOUtils.contentEquals(fileByIdentifier, OakFileRepositoryTest.class.getClassLoader().getResourceAsStream("sample.pdf"));


        Map<String, Object> properties = oakFileRepository.getProperties(identifier);

        System.out.println(properties);
    }
}