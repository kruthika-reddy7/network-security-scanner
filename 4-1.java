import java.io.File;

public class ListFiles {
    public static void listAllFiles(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listAllFiles(file.getAbsolutePath());
                } else {
                    System.out.println("File: " + file.getAbsolutePath());
                }
            }
        }
    }

    public static void main(String[] args) {
        String path = "C:\\your\\directory\\path";  // change as needed
        listAllFiles(path);
    }
}
