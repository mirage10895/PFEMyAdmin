package fr.eseo.dis.amiaudluc.pfeproject.decoder;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lucasamiaud on 30/12/2017.
 */

public class CacheFileGenerator {

    public static final String CORE_USER = "pfemyadmin_user.json";

    public static final String MYPRJ = "pfemyadmin_myprj.json";

    public static final String LIPRJ = "pfemyadmin_liprj.json";

    public static final String MYJUR = "pfemyadmin_myjur.json";

    public static final String LIJUR = "pfemyadmin_lijur.json";

    public static final String JYINF = "pfemyadmin_jyinf.json";

    public static final String CORE_USER_LOGIN = "pfemyadmin_user_login.json";
    // The list of entities registered in the core data
    public static final String[] CORE_ENTITIES = {MYPRJ, LIPRJ, MYJUR, LIJUR, JYINF, CORE_USER_LOGIN,
            CORE_USER};
    private static CacheFileGenerator instance;

    private CacheFileGenerator() {
    }

    public static synchronized CacheFileGenerator getInstance() {
        if (instance == null) {
            instance = new CacheFileGenerator();
        }
        return instance;
    }

    /**
     * This function remove all the content of the cache files.
     *
     * @param cxt : The current context.
     */
    public void removeAll(Context cxt) {
        for (String entity : CacheFileGenerator.CORE_ENTITIES) {
            write(cxt, entity, "");
        }
    }

    /**
     * This function extract the string contained in the cache file.
     *
     * @param cxt        : The current context.
     * @param coreEntity : The name of the cache file where the data are going to be extracted.
     * @return
     */
    public String read(Context cxt, String coreEntity) {
        String result = "";
        File cacheFile = new File(cxt.getCacheDir() + "/" + coreEntity);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(cacheFile);
            result = convertStreamToString(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * This function will write the data passed in arguments in the cache file.
     *
     * @param cxt        : The current context.
     * @param coreEntity : The name of the file where the data are going to be written.
     * @param data       : The json string received.
     */
    public void write(Context cxt, String coreEntity, String data) {
        File cacheFile = new File(cxt.getCacheDir() + "/" + coreEntity);
        try {
            try {
                FileOutputStream stream = new FileOutputStream(cacheFile);
                try {
                    try {
                        stream.write(data.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } finally {
                    stream.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
