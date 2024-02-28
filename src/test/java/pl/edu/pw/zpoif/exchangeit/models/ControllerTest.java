package pl.edu.pw.zpoif.exchangeit.models;

import jakarta.persistence.*;

@Entity
@Table(name = "controllers_tests")
public class ControllerTest {
    @Id
    @Column(name = "test_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    @Column(name = "endpoint_name")
    private String endpointName;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "expected_value")
    private String expectedContent;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "response_type")
    private String responseType;

    public String getEndpointName() {
        return endpointName;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getResponseType() {
        return responseType;
    }

    public Long getTestId() {
        return testId;
    }

    public String getUri() {
        return uri;
    }

    public String getExpectedContent() {
        return expectedContent;
    }
}
