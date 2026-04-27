package jdbc.utils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;

import static jdbc.utils.Utils.isNullOrEmpty;

public class SSLUtils {

  public static SSLContext getTrustEverybodySSLContext(String clientCertificateKeyStoreUrl, String clientCertificateKeyStoreType, String clientCertificateKeyStorePassword) throws SSLParamsException {
    // Delegate to unified method with trust-all flag
    TrustManager[] tms = new TrustManager[] { new MyTrustEverybodyManager() };
    // Load KeyManagers (for client certificate authentication)
    KeyManager[] kms = null;
    if (!isNullOrEmpty(clientCertificateKeyStoreUrl)) {
      kms = loadKeyManagers(clientCertificateKeyStoreUrl, clientCertificateKeyStoreType, clientCertificateKeyStorePassword);
    }

    // Create and initialize SSLContext
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(kms, tms, null);
      return sslContext;
    }
    catch (NoSuchAlgorithmException nsae) {
      throw new SSLParamsException("TLS is not a valid SSL protocol.", nsae);
    }
    catch (KeyManagementException kme) {
      throw new SSLParamsException("KeyManagementException: " + kme.getMessage(), kme);
    }
  }

  public static SSLContext getValidatingSSLContext(String truststoreUrl, String truststoreType, String truststorePassword,
          String keystoreUrl, String keystoreType, String keystorePassword) throws SSLParamsException
  {
    // Delegate to unified method with validation enabled
    TrustManager[] tms;
    if (!isNullOrEmpty(truststoreUrl)) {
      // SECURE: Load truststore for server certificate validation
      tms = loadTrustManagers(truststoreUrl, truststoreType, truststorePassword);
    } else {
      // No truststore provided - use default
      tms = null;
    }
    // Load KeyManagers (for client certificate authentication)
    KeyManager[] kms = null;
    if (!isNullOrEmpty(keystoreUrl)) {
      kms = loadKeyManagers(keystoreUrl, keystoreType, keystorePassword);
    }

    // Create and initialize SSLContext
    try {
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(kms, tms, null);
      return sslContext;
    }
    catch (NoSuchAlgorithmException nsae) {
      throw new SSLParamsException("TLS is not a valid SSL protocol.", nsae);
    }
    catch (KeyManagementException kme) {
      throw new SSLParamsException("KeyManagementException: " + kme.getMessage(), kme);
    }
  }

  private static TrustManager[] loadTrustManagers(String truststoreUrl, String truststoreType, String truststorePassword)
          throws SSLParamsException
  {
    InputStream tsIS = null;
    try {
      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      // Use provided type or default to JKS if not specified
      String storeType = isNullOrEmpty(truststoreType) ? KeyStore.getDefaultType() : truststoreType;
      KeyStore trustStore = KeyStore.getInstance(storeType);
      URL tsURL = new URL(truststoreUrl);
      char[] password = (truststorePassword == null) ? new char[0] : truststorePassword.toCharArray();
      tsIS = tsURL.openStream();
      trustStore.load(tsIS, password);
      tmf.init(trustStore);
      return tmf.getTrustManagers();
    }
    catch (NoSuchAlgorithmException nsae) {
      throw new SSLParamsException("Unsupported truststore algorithm [" + nsae.getMessage() + "]", nsae);
    }
    catch (KeyStoreException kse) {
      throw new SSLParamsException("Could not create TrustStore instance [" + kse.getMessage() + "]", kse);
    }
    catch (CertificateException ce) {
      throw new SSLParamsException("Could not load truststore from " + truststoreUrl, ce);
    }
    catch (MalformedURLException mue) {
      throw new SSLParamsException(truststoreUrl + " does not appear to be a valid URL.", mue);
    }
    catch (IOException ioe) {
      throw new SSLParamsException("Cannot open " + truststoreUrl + " [" + ioe.getMessage() + "]", ioe);
    }
    finally {
      if (tsIS != null) {
        try {
          tsIS.close();
        }
        catch (IOException e) {
          // can't close input stream, but truststore can be properly initialized so we shouldn't throw this exception
        }
      }
    }
  }

  private static KeyManager[] loadKeyManagers(String keystoreUrl, String keystoreType, String keystorePassword) throws SSLParamsException
  {
    InputStream ksIS = null;
    try {
      KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      // Use provided type or default to JKS if not specified
      String storeType = isNullOrEmpty(keystoreType) ? KeyStore.getDefaultType() : keystoreType;
      KeyStore keyStore = KeyStore.getInstance(storeType);
      URL ksURL = new URL(keystoreUrl);
      char[] password = (keystorePassword == null) ? new char[0] : keystorePassword.toCharArray();
      ksIS = ksURL.openStream();
      keyStore.load(ksIS, password);
      kmf.init(keyStore, password);
      return kmf.getKeyManagers();
    }
    catch (UnrecoverableKeyException uke) {
      throw new SSLParamsException("Could not recover keys from client keystore. Check password?", uke);
    }
    catch (NoSuchAlgorithmException nsae) {
      throw new SSLParamsException("Unsupported keystore algorithm [" + nsae.getMessage() + "]", nsae);
    }
    catch (KeyStoreException kse) {
      throw new SSLParamsException("Could not create KeyStore instance [" + kse.getMessage() + "]", kse);
    }
    catch (CertificateException ce) {
      throw new SSLParamsException("Could not load keystore from " + keystoreUrl, ce);
    }
    catch (MalformedURLException mue) {
      throw new SSLParamsException(keystoreUrl + " does not appear to be a valid URL.", mue);
    }
    catch (IOException ioe) {
      throw new SSLParamsException("Cannot open " + keystoreUrl + " [" + ioe.getMessage() + "]", ioe);
    }
    finally {
      if (ksIS != null) {
        try {
          ksIS.close();
        }
        catch (IOException e) {
          // can't close input stream, but keystore can be properly initialized so we shouldn't throw this exception
        }
      }
    }
  }

  private static class MyTrustEverybodyManager implements X509TrustManager {

    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
    }

    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
    }

    public X509Certificate[] getAcceptedIssuers() {
      return new X509Certificate[0];
    }
  }

  public static class SSLParamsException extends SQLException {
    public SSLParamsException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
