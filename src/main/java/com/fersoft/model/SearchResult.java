package com.fersoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public record SearchResult(
        @JsonProperty("ID")
        String id,
        @JsonProperty("IsStable")
        Boolean stable,
        @JsonProperty("LastUpdate")
        String lastUpdate,
        @JsonProperty("ParentPatient")
        String parentPatient,
        @JsonProperty("Series")
        List<String> series,
        @JsonProperty("Type")
        String type,
        @JsonProperty("MainDicomTags")
        Map<String, DicomTag> mainDicomTags,
        @JsonProperty("PatientMainDicomTags")
        Map<String, DicomTag> patientMainDicomTags) {

    public SearchResult() {
        this(null);
    }

    public SearchResult(String id) {
        this(id, null, null, null, null, null, null, null);
    }

}