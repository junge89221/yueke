package cn.bry.yueke.http.okhttp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件管理器
 */
@SuppressWarnings("unused")
public class FileManager {

    /**
     * 存储文件
     */
    public static void saveFile(InputStream inputStream,String dir,String fileName) {
        File dirFile = new File(dir);
        if (!dirFile.exists()){
            dirFile.mkdir();
        }
        File file = new File(dir,fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fileOutputStream == null || inputStream == null)
            return;

        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
