package com.fersoft.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Level",
        "Expand",
        "Limit",
        "Query",
        "Full"
})
public record SearchQuery(
        @JsonProperty("Level")
        String level,
        @JsonProperty("Expand")
        Boolean expand,
        @JsonProperty("Limit")
        Integer limit,
        @JsonProperty("Query")
        Map<String, String> query,
        @JsonProperty("Full")
        Boolean full) {
}