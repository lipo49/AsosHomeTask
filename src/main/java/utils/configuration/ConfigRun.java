package utils.configuration;

import org.xml.sax.SAXException;
import utils.helpers.Report;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.io.InputStreamReader;

// This class helps to get the property from the configRun.properties file
// You should pass the name of the property and the property will be returned

public class ConfigRun {

    private static Properties properties = null;
    public static String get(String name) throws ParserConfigurationException, SAXException, IOException {
        if (properties == null){
            properties = new Properties();
            FileInputStream input;
            URL url = ConfigRun.class.getClassLoader().getResource("configRun.properties");
            try {
                if (url != null){
                    URI uri = url.toURI();
                    File file = new File(uri);
                    input = new FileInputStream(file);
                    properties.load(new InputStreamReader(input, Charset.forName("UTF-8")));
                    input.close();
                }
            }catch (Exception e){
                Report report = new Report();
                report.fail(e.getMessage());
            }
        }
        return properties.getProperty(name.toString());
    }
}
