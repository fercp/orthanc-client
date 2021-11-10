package com.fersoft;

import com.fersoft.model.SearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestOrhancClient {
    private static final String BASE_URL = "https://demo.orthanc-server.com/";
    private static final String PATIENT_NAME = "BRAINIX";
    private OrthancClient orhancClient;

    @BeforeEach
    void setUp() {
        orhancClient = new OrthancClient(BASE_URL);
    }

    @Test
    void testSearchPatients() {
        Map<String, String> dicomTags = Map.of("PatientName", "BRAINIX");
        List<SearchResult> result = orhancClient.search(dicomTags, true);
        assertThat(result.size(), is(equalTo(1)));
        assertThat(result.get(0).patientMainDicomTags().get("0010,0010").value(), is(equalTo(PATIENT_NAME)));
    }

    @Test
    void testGetDicomArchive() throws IOException {
        Map<String, String> dicomTags = Map.of("PatientName", "BRAINIX");
        List<SearchResult> result = orhancClient.search(dicomTags, true);
        orhancClient.getImages(result.get(0).id(), "/tmp");
        assertTrue(Path.of("/tmp", result.get(0).id() + ".zip").toFile().exists());
    }
}
