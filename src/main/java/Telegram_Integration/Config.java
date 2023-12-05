package Telegram_Integration;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    private static String getPath(){
        return Thread.currentThread().getContextClassLoader().getResource("").getPath() + "application.properties";
    }

    public static Properties getAppProps() {
        Properties appProps = new Properties();
        try{
            String A = getPath();
            appProps.load(new FileInputStream(A));
        }
        catch (Exception E){
            E.printStackTrace();
        }
        return appProps ;
    }
}
