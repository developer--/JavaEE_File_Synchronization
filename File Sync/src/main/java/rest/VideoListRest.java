package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by root on 5/4/17.
 */

public class VideoListRest {
    @GET
    @Path("/hello1")
    public String sayHello1(){
        return "hello 1";
    }

    @GET
    @Path("/hello2")
    public String sayHello2(){
        return "hello 2";
    }
}
