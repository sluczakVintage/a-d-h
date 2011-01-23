/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

/**
 *
 * @author Maciek
 */
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CMessageDigestFile {

    private static CMessageDigestFile instance;
    /// @todo odtąd singleton
    private CMessageDigestFile()
    {

    }
    public static synchronized CMessageDigestFile getInstance() {
	if (instance == null) {
		instance = new CMessageDigestFile();
	}
	return instance;
    }

    /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

   private byte[] createChecksum(File file) throws Exception
   {
     InputStream fis =  new FileInputStream(file);
     byte[] buffer = new byte[(int)(file.length())];
     MessageDigest complete = MessageDigest.getInstance("MD5");
     int numRead;
     do {
      numRead = fis.read(buffer);
      if (numRead > 0) {
        complete.update(buffer, 0, numRead);
        }
      } while (numRead != -1);
     fis.close();
     return complete.digest();
   }

   // see this How-to for a faster way to convert
   // a byte array to a HEX string
   public String getMD5Checksum(File file) {
       try {
            byte[] b = createChecksum(file);
            String result = "";
            for (int i=0; i < b.length; i++) {
                result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return result;
       } catch (Exception e) {
           System.err.println(e.getMessage());
           return " bla bla";
       }
   }

    public String getSHA256Checksum(String source) {
        String checksum = "";

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(source.getBytes());

            byte[] byteData = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format : " + sb.toString());
            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            checksum = hexString.toString();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CMessageDigestFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checksum;
    }

    //SHA-1
    private String convToHex(byte[] data) {
                StringBuilder buf = new StringBuilder();
                for (int i = 0; i < data.length; i++) {
                    int halfbyte = (data[i] >>> 4) & 0x0F;
                    int two_halfs = 0;
                    do {
                        if ((0 <= halfbyte) && (halfbyte <= 9))
                            buf.append((char) ('0' + halfbyte));
                        else
                                buf.append((char) ('a' + (halfbyte - 10)));
                        halfbyte = data[i] & 0x0F;
                    } while(two_halfs++ < 1);
                }
                return buf.toString();
            }

    public String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = new byte[40];
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
            return convToHex(sha1hash);
    }

}
