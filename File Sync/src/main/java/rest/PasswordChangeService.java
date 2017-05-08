package rest;

import repository.manager.DBManager;
import repository.models.PassworChangeModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by root on 5/5/17.
 */
@Path("changePassword")
public class PasswordChangeService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String changePassword(final PassworChangeModel request){
        if(DBManager.getInstance().checkIfCanLogin(request.username,request.oldPassword)){
            final boolean success = DBManager.getInstance().changePassword(request.username,request.newPassword);
            return "{\"success\":"+success+",\"errorMsg\":\"\"}";
        }
        return "{\"success\":false,\"errorMsg\":\"Invalid Credentials\"}";
    }


    @GET
    public String test(){
        return "test";
    }
}
