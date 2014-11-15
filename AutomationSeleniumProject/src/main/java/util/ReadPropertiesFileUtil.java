package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by bs211w on 7/9/2014.
 */
public class ReadPropertiesFileUtil {

    public Properties readPropertiesFile(String propertiesFile) throws IOException {
        String result = "";
        Properties prop = new Properties();
        String propFileName = propertiesFile;

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        prop.load(inputStream);
        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
        return prop;
    }
}
