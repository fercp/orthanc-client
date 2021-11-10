package com.fersoft;

import com.fersoft.model.SearchQuery;
import com.fersoft.model.SearchResult;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 * Rest client for Orthanc API
 * <p>
 * For open api spec see https://api.orthanc-server.com/
 *
 * @author ferat capar
 */

public class OrthancClient {
    private static final String QUERY_PATH = "/tools/find";
    private static final String DICOM_ARCHIVE_PATH = "/patients/%s/archive";

    private final WebTarget webTarget;

    public OrthancClient(String baseUrl) {
        this(ClientBuilder.newClient().target(baseUrl));
    }

    public OrthancClient(WebTarget webTarget) {
        this.webTarget = webTarget;
    }

    /**
     * @param dicomTags search parameters
     * @param expand    for id only set false
     * @return query result
     */
    public List<SearchResult> search(Map<String, String> dicomTags, boolean expand) {
        WebTarget searchPath = webTarget.path(QUERY_PATH);
        Invocation.Builder invocationBuilder = searchPath.request(MediaType.APPLICATION_JSON);
        SearchQuery query = new SearchQuery("Study",
                expand,
                100,
                dicomTags,
                true);
        Response response
                = invocationBuilder
                .post(Entity.entity(query, MediaType.APPLICATION_JSON));
        GenericType<List<SearchResult>> searchResultListType = new GenericType<>() {
        };
        return response.readEntity(searchResultListType);
    }

    /**
     * @param id        of the patient
     * @param directory target directory of the zip of images
     * @throws IOException in case of file exceptions
     */
    public void getImages(String id, String directory) throws IOException {
        WebTarget searchPath = webTarget.path(String.format(DICOM_ARCHIVE_PATH, id));
        Invocation.Builder invocationBuilder = searchPath.request();
        Response response
                = invocationBuilder.get();
        try (InputStream inputStream = response.readEntity(InputStream.class)) {
            File targetFile = new File(directory, id + ".zip");
            java.nio.file.Files.copy(
                    inputStream,
                    targetFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
