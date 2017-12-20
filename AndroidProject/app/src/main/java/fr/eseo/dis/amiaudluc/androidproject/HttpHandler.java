package fr.eseo.dis.amiaudluc.androidproject;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by lucasamiaud on 20/12/2017.
 */

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {

            URL url = new URL(reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(buildSslSocketFactory());
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
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

    private static SSLSocketFactory buildSslSocketFactory() {
// Add support for self-signed (local) SSL certificates
// Based on http://developer.android.com/training/articles/security-ssl.html#UnknownCa
        try {
// Load CAs from an InputStream
// (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream chainInput = new BufferedInputStream(new FileInputStream("/Users/lucasamiaud/Documents/I3 - LD/Android_Workspace/android_WS_project/PFEProject/app/libs/chain.crt"));
            InputStream interInput = new BufferedInputStream(new FileInputStream("/Users/lucasamiaud/Documents/I3 - LD/Android_Workspace/android_WS_project/PFEProject/app/libs/inter.crt"));
            InputStream rootInput = new BufferedInputStream(new FileInputStream("/Users/lucasamiaud/Documents/I3 - LD/Android_Workspace/android_WS_project/PFEProject/app/libs/root.crt"));
            Certificate chain;
            Certificate inter;
            Certificate root;
            try {
                chain = cf.generateCertificate(chainInput);
                inter = cf.generateCertificate(interInput);
                root = cf.generateCertificate(rootInput);
// System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                chainInput.close();
                interInput.close();
                rootInput.close();
            }
// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("chain", chain);
            keyStore.setCertificateEntry("inter", inter);
            keyStore.setCertificateEntry("root", root);
// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
// Create an SSLContext that uses our TrustManager
            SSLContext.getInstance("TLS").init(null, tmf.getTrustManagers(), null);
            return SSLContext.getInstance("TLS").getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
