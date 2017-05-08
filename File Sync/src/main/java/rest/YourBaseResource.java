package rest;

import repository.manager.DBManager;
import repository.manager.MySessionManager;
import repository.models.UploadedFileModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.util.List;

@Path("/")
public class YourBaseResource {

  //this gets injected after the class is instantiated by Jersey    
  @Context UriInfo uriInfo;
  @Context HttpServletRequest request;
  @Context HttpServletResponse response;

  @Path("/videos")
  @GET
  public String method1(){
    try {
        final HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            request.getRequestDispatcher("/videos.jsp").include(request, response);

        final String userId = (String) request.getSession(false).getAttribute("userId");
        final List<UploadedFileModel> files = DBManager.getInstance().getUploadidFiles(userId);

        final String div = "<div style=\"width: 200px;height: 200px; margin: 30px; float: left; \">";
        final String videoHtml = "width=\"200px\" height=\"200px\" controls><source src=\"/files/videos/";

        final StringBuilder content = new StringBuilder();
        String mimeType;
        String playerType;
        String fileName;

        for (int i = 0; i < files.size(); i++) {
            final File[] fileArray = new File(new File(files.get(i).getFullPath()).getParentFile().getPath()).listFiles();
            if (fileArray != null && fileArray.length > 0) {
                final File finalFile = fileArray[0];
                if (finalFile.exists()) {
                    fileName = finalFile.getName();
                    if (fileName.contains("mp3") || fileName.contains("3gpp")) {
                        mimeType = "audio/mpeg";
                        playerType = "<audio ";
                    } else {
                        mimeType = "video/mp4";
                        playerType = "<video ";
                    }

                    String video = div + "<p style=\"text-align: center\">" + fileName + "   size = "+(finalFile.length() / (1024*1024))+" MB </p><p hidden>" + files.get(i).getVideoId() +
                            "</p><br><button  onclick=\"window.location.href='/api/rest/videos/delete?fileId=" + files.get(i).getVideoId() + "'\">Delete</button>" +
                            "<button style=\"float:right\"onclick=\"window.location.href='/api/rest/videos/download?filePath=" + request.getServerName() + ":" + request.getServerPort() + "/files/videos/" + files.get(i).getUserName()
                            + File.separator + files.get(i).getFolderName() + File.separator + fileName + "'\">download</button><br>"
                            + playerType + videoHtml + files.get(i).getUserName()
                            + File.separator + files.get(i).getFolderName() + File.separator + fileName + "\" type=\"" + mimeType + "\"></div>";
                    if (i % 5 == 0)
                        video += "\n";
                    content.append(video);
                }
            }
        }
          response.getWriter().write("<h3 style=\"float:right\">"+String.valueOf(userId) + "</h3><br>");
          response.getWriter().write("<br>"+content.toString());
      } else {
        response.sendRedirect("/api/login");
      }
    }catch (Exception e){
      e.printStackTrace();
    }
    return "";
  }

  @Path("videos/delete")
  @GET
  public Response method2(@QueryParam("fileId") String fileId){
     final HttpSession session = request.getSession(false);
     try {
         if (MySessionManager.isValidLoginSession(session)) {
           DBManager.getInstance().deleteVideo(Long.parseLong(fileId));
           UriBuilder addressBuilder = uriInfo.getBaseUriBuilder();
           addressBuilder.path("videos");
           return Response.seeOther(addressBuilder.build()).build();
        }else {
             response.sendRedirect("/api/login");
      }
     }catch (Exception e) {
         e.printStackTrace();
     }
     return null;
  }

    @Path("videos/download")
    @GET
    public Response downloadFile(@QueryParam("filePath") String filePath){
        final HttpSession session = request.getSession(false);
        try {
            if (MySessionManager.isValidLoginSession(session)) {
                response.sendRedirect("http://"+filePath);
            }else {
                response.sendRedirect("/api/login");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}