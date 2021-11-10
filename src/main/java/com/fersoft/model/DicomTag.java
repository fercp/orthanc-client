package com.fersoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DicomTag(@JsonProperty("Name") String name,@JsonProperty("Type")  String type, @JsonProperty("Value") String value) {

}
