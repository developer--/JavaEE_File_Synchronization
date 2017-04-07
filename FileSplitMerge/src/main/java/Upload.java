import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user-00 on 4/7/17.
 */
public class Upload extends JFrame {

    private boolean canUpload = false;
    private int i = 0;
    private JButton resumeButton;
    private JPanel panel1;
    private JButton pauseButton;
    private JButton splitFilesButton;
    private JButton mergeFilesButton;
    private static final String filePath = "/home/user-00/videos/1.mp4";
    private List<File> files = new ArrayList<>();

    Upload() {
        this.setSize(500, 500);
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(2);
        this.setVisible(true);
        resumeButton.addActionListener((ActionListener) -> {
            canUpload = true;
            startUploadingFiles(files);
        });

        pauseButton.addActionListener((ActionListener) -> pauseUpload());

        splitFilesButton.addActionListener((ActionListener) -> splitFiles());

        mergeFilesButton.addActionListener((ActionListener) -> mergeFiles());
    }

    private void pauseUpload() {
        canUpload = false;
    }

    private void splitFiles() {
        try {
            files = FileManager.splitFile(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergeFiles() {
        try {
            FileManager.mergeFiles("/home/user-00/videos/1.mp4.001", "/home/user-00/videos/origin.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startUploadingFiles(List<File> files) {
        if (files.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ასატვირთი არაფერია");
            return;
        }
        new Thread(() -> {
            while (i < files.size() && upload(files.get(i))) {
                i++;
            }
            if (i >= files.size()) {
                JOptionPane.showMessageDialog(null, "ატვირთვა დასრულებულია");
                i = 0;
            }
        }).start();
    }

    private boolean upload(File file) {
        if (canUpload) {
            try {
                uploadFile(file);
                Thread.sleep(5000);
                return true;
//                return canUpload && file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    private void uploadFile(File file) {
        RequestBody reqFile = RequestBody.create(MediaType.parse("video/*"),file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("ser.mp4", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
        // add another part within the multipart request
        // finally, execute the request
        Call<ResponseBody> call = NetworkApi.Factory.getApi().upload(name,body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("success? " + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("error " + t.getMessage());
            }
        });
    }


}
