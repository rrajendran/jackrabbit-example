package com.capella.jackrabbit.oak.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author Ramesh Rajendran
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class OakContentStream implements Serializable {
    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty("mimeType")
    private String mimeType;

    @JsonProperty("length")
    private BigInteger length;

    @JsonProperty("inputStream")
    private InputStream inputStream;

}
