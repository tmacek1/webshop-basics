package com.assignment.webshop.basics;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import org.junit.BeforeClass;
import wiremock.org.apache.http.HttpStatus;

public class OrderFinalizationTest {

    /**
     * 1. Create the Wiremock server and start
     * 2. Configure the server to intercept the request
     * 3. Stub for the request [get request]
     * 		- Create the mock response
     * 4. Shutdown the WireMock Server
     *
     * */
//    private static final int PORT = 8080;
//    private static final String HOST = "localhost";
//
//    private static WireMockServer server = new WireMockServer(PORT);
//
//    @BeforeClass
//    public static void setup() {
//        server.start();
//
//        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
//        mockResponse.withStatus(200)
//                .withBody("[{\"BrandName\":\"Alienware\",\"" +
//                        "Features\":{\"Feature\":" +
//                        "[\"8th Generation Intel® Core™ i5-8300H\"," +
//                        "\"Windows 10 Home 64-bit English\"," +
//                        "\"NVIDIA® GeForce® GTX 1660 Ti 6GB GDDR6\"," +
//                        "\"8GB, 2x4GB, DDR4, 2666MHz\"]},\"Id\":1,\"" +
//                        "LaptopName\":\"Alienware M17\"}]")
//                .withHeader("Content-Type", "application/json");
//
//        WireMock.configureFor(HOST, PORT); // http://localhost:8080
//        WireMock.stubFor(
//                WireMock.get("/laptop-bag/webapi/api/all")
//                        .willReturn(mockResponse)
//        );
//    }
//
//    @Test
//    public void TestGetEndPoint() throws URISyntaxException {
//        RestAssured.given()
//                .accept(ContentType.JSON)
//                .when()
//                .get(new URI("http://localhost:8080/laptop-bag/webapi/api/all"))
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.SC_OK)
//                .and()
//                .body("[0].BrandName", equalTo("Alienware"));
//    }
//
//    @AfterClass
//    public static void teardown() {
//        if(null != server && server.isRunning()){
//            server.shutdownServer();
//        }
//    }

}
