package sg.edu.nus.iss.app.day13workshop.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {
    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    /* 
    helper method to create directory on the local machine at given path
    */
    public static void createDir(String path) {
        File dir = new File(path);
        // check file exists -> if no, create file -> return boolean file created
        boolean isDirExist = dir.mkdir(); 
        logger.info("Does Dir exist? " + isDirExist);

        // check if OS is not Windows i.e. mac/ubuntu
        // set permissions for file if it is successfully created
        if(isDirExist) {
            String osName = System.getProperty("os.name");
            if(!osName.contains("Windows")){
                String permission = "rwxrwx---"; //owner & grp mem can read, write and exc
                Set<PosixFilePermission> permissions = 
                        PosixFilePermissions.fromString(permission);
                try {
                    Files.setPosixFilePermissions(dir.toPath(), permissions);
                } catch (IOException e) {
                    logger.error("Error", e);
                }
            }
        }
    }


}
