package com.capella.jackrabbit.oak.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Ramesh Rajendran
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class OakDocument implements Serializable {
    @JsonProperty("id")
    private String id;

    @JsonProperty("oakContentStream")
    private OakContentStream oakContentStream;

}
