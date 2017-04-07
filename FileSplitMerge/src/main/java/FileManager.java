import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user-00 on 4/7/17.
 */
public class FileManager {

    public static List<File> splitFile(File f) throws IOException {
        int partCounter = 1;
        int sizeOfFiles = 1024 * 1024;// 1MB
        byte[] buffer = new byte[sizeOfFiles];
        final List<File> files = new ArrayList<>();

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(f))) {//try-with-resources to ensure closing stream
            String name = f.getName();

            int tmp;
            while ((tmp = bis.read(buffer)) > 0) {
                //write each chunk of data into separate file with different number in name
                File newFile = new File(f.getParent(), name + "." + String.format("%03d", partCounter++));
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, tmp);//tmp is chunk size
                    files.add(newFile);
                }
            }
        }
        return files;
    }

    public static List<File> listOfFilesToMerge(File oneOfFiles) {
        String tmpName = oneOfFiles.getName();//{name}.{number}
        String destFileName = tmpName.substring(0, tmpName.lastIndexOf('.'));//remove .{number}
        File[] files = oneOfFiles.getParentFile().listFiles((File dir, String name) -> name.matches(destFileName + "[.]\\d+"));
        assert files != null;
        Arrays.sort(files);//ensuring order 001, 002, ..., 010, ...
        return Arrays.asList(files);
    }

    public static void mergeFiles(List<File> files, File into)
            throws IOException {
        try (BufferedOutputStream mergingStream = new BufferedOutputStream(
                new FileOutputStream(into))) {
            for (File f : files) {
                Files.copy(f.toPath(), mergingStream);
                f.delete();
            }
        }
    }

    public static void mergeFiles(File oneOfFiles, File into)
            throws IOException {
        mergeFiles(listOfFilesToMerge(oneOfFiles), into);
    }

    public static List<File> listOfFilesToMerge(String oneOfFiles) {
        return listOfFilesToMerge(new File(oneOfFiles));
    }

    public static void mergeFiles(String oneOfFiles, String into) throws IOException{
        mergeFiles(new File(oneOfFiles), new File(into));
    }
}
