package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by root on 5/3/17.
 */
@Path("hello")
public class HelloRest {
    @GET
    public String sayHello(@QueryParam("videoId") String videoId){
        return "video id = " + videoId;
    }

}
