package aron.library.config.loader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigLoaderTest {
    @Test
    public void loadAndDecryptConfigFile_shouldDecryptConfigFileAndOpenIt() throws IOException {
        final URL appConf = getClass().getClassLoader().getResource("app.conf");
        final URL keyConf = getClass().getClassLoader().getResource("key.conf");
        final ConfigLoader configLoader = new ConfigLoader(keyConf.getPath(), appConf.getPath());


        configLoader.loadAndDecryptConfigFile();
        final Properties properties = configLoader.getProperties();


        assertEquals("test", properties.getProperty("app_node_name"));
        assertEquals("org.postgresql.Driver", properties.getProperty("db_accounts_driver"));
        assertEquals("usr123#", properties.getProperty("db_accounts_password"));
    }
}
