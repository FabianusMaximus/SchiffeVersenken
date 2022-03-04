
/*
Battleships - The popular game as client/server edition for playing with a friend over the Internet (or on the LAN).
Copyright (C) 2006-2022 Stan's World
http://www.stans-world.de/battleships.html

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details:
http://www.gnu.org/licenses/gpl.html

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package SchiffeVersenken.test;
import java.io.*;
        import java.net.*;
        import java.text.*;
        import java.util.*;

public class WebServer extends Thread {

    private File m_oRootDir;
    private ServerSocket m_oServerSocket;
    private boolean m_bRunning;
    private String m_sAppName;
    private String m_sAppVersion;
    private PrintWriter m_oLogPrintWriter;
    private boolean m_log;
    private int m_dayOfMonth;
    private final boolean DEBUG = false;


    private static final Hashtable<String, String> MIME_TYPES = new Hashtable<String, String>();

    static {
        MIME_TYPES.put(".gif", "image/gif");
        MIME_TYPES.put(".jpg", "image/jpeg");
        MIME_TYPES.put(".png", "image/png");
        MIME_TYPES.put(".ico", "image/x-icon");
        MIME_TYPES.put(".html", "text/html");
        MIME_TYPES.put(".htm", "text/html");
        MIME_TYPES.put(".js", "application/x-javascript");
        MIME_TYPES.put(".au", "audio/basic");
        MIME_TYPES.put(".css", "text/css");
        MIME_TYPES.put(".cfg", "forbidden");
    }

    public WebServer(int iPort, String sAppName, String sAppVersion, boolean bLog) throws IOException {
        m_oRootDir = new File(new File("./").getCanonicalPath());
        m_oServerSocket = new ServerSocket(iPort);
        m_sAppName = sAppName;
        m_sAppVersion = sAppVersion;
        m_log = bLog;
        if (m_log) {
            openLogPrintWriter();
        }
        start();
    }

    public void start() {
        if (!m_bRunning) {
            m_bRunning = true;
            super.start();
        }
    }

    public void interrupt() {
        if (m_bRunning) {
            super.interrupt();
            m_bRunning = false;
            try {
                m_oServerSocket.close();
            } catch (IOException e) {
                if (DEBUG) System.out.println(e.getMessage());
            }
            if (m_log) closeLogPrintWriter();
            if (DEBUG) System.out.println("Webserver: closing server socket");
        }
    }

    public void run() {
        while (m_bRunning) {
            try {
                Socket socket = m_oServerSocket.accept();
                if (m_log) {
                    Calendar cal = Calendar.getInstance();
                    if (cal.get(Calendar.DAY_OF_MONTH) != m_dayOfMonth) {
                        closeLogPrintWriter();
                        openLogPrintWriter();
                    }
                }
                HttpRequestThread requestThread = new HttpRequestThread(socket, m_oRootDir, m_sAppName, m_sAppVersion, m_oLogPrintWriter);
                requestThread.start();
            } catch (Exception e) {
                if (DEBUG) e.printStackTrace();
            }
        }
    }

    public static String getMimeType(File file) {
        String contentType = MIME_TYPES.get(getExtension(file));
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    public static String getHttpDate(Date date) {
		/*
		HTTP applications have historically allowed three different formats for the representation of date/time stamps:

		      Sun, 06 Nov 1994 08:49:37 GMT  ; RFC 822, updated by RFC 1123
		      Sunday, 06-Nov-94 08:49:37 GMT ; RFC 850, obsoleted by RFC 1036
		      Sun Nov  6 08:49:37 1994       ; ANSI C's asctime() format

		The first format is preferred as an Internet standard and represents a fixed-length subset of that defined by RFC 1123 [8] (an update to RFC 822 [9]).
		The second format is in common use, but is based on the obsolete RFC 850 [12] date format and lacks a four-digit year. HTTP/1.1 clients and servers that parse the date value MUST accept all three formats (for compatibility with HTTP/1.0), though they MUST only generate the RFC 1123 format for representing HTTP-date values in header fields. See section 19.3 for further information.

		Date: Wed Oct 22 11:37:33 CEST 2008
		Last-Modified: Wed Oct 22 11:37:33 CEST 2008

		Server: Microsoft-IIS/5.1
		Date: Wed, 22 Oct 2008 09:35:12 GMT
		Content-Type: image/gif
		Last-Modified: Mon, 10 Oct 2005 13:08:54 GMT
		Content-Length: 164
		*/
        DateFormat df = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss zzz", new Locale("en", "US"));
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(date);
    }

    private static String getExtension(File file) {
        String extension = "";
        String filename = file.getName();
        int dotPos = filename.lastIndexOf(".");
        if (dotPos >= 0) {
            extension = filename.substring(dotPos);
        }
        return extension.toLowerCase();
    }

    private void openLogPrintWriter() {
        String sLogFileName;
        Calendar cal = Calendar.getInstance();
        m_dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        sLogFileName = "http-" + cal.get(Calendar.YEAR) + "-" +
                ".log";
        try {
            m_oLogPrintWriter = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(sLogFileName, true), "UTF8"
                    )
            );
            m_oLogPrintWriter.println(new Date().toString());
            m_oLogPrintWriter.println("TimeStamp Status RemoteAddress Request Referer");
            m_oLogPrintWriter.flush();
        } catch (Exception e) {
            System.out.println("Error opening log file: " + e.getMessage());
        }
    }

    private void closeLogPrintWriter() {
        if (m_oLogPrintWriter != null) {
            m_oLogPrintWriter.println();
            m_oLogPrintWriter.close();
        }
    }

}