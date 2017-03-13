
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * Created by vlad on 03.12.2016.
 */
// The Java class will be hosted at the URI path "/helloworld"
@WebServlet("/connect")
public class ConnectServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rightsRequesterDn = request.getParameter("requesterDn");
        String targetDn = request.getParameter("targetDn");
        Client client = ClientBuilder.newClient();
        JSONParser parser = new JSONParser();

        String entity = client.target("http://localhost:18080/SecurityProvider")
                .path("rights")
                .queryParam("requesterDn", "cn=gateway1,ou=Gateways,dc=spital,dc=moinesti,dc=com")
                .queryParam("rightsRequesterDn", rightsRequesterDn)
                .queryParam("targetDn", targetDn)
                .request()
                .get(String.class);

        System.out.println(entity);

        try {
            JSONObject rights = (JSONObject)parser.parse(entity);
            response.getWriter().write(rights.toJSONString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}