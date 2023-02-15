package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Badge {

    @JsonProperty("votes")
    private String votes;

    @JsonProperty("attachmentsByType")
    private AttachmentsByType attachments;
}
