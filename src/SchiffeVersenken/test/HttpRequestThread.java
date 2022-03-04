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
import java.util.*;

public class HttpRequestThread extends Thread {

    private File m_oRootDir;
    private Socket m_oSocket;
    private String m_sAppName;
    private String m_sAppVersion;
    private PrintWriter m_oLogPrintWriter;
    private boolean m_bLog;

    public HttpRequestThread(Socket oSocket, File oFilRootDir, String sAppName, String sAppVersion, PrintWriter oLogPrintWriter) {
        m_oSocket = oSocket;
        m_oRootDir = oFilRootDir;
        m_sAppName = sAppName;
        m_sAppVersion = sAppVersion;
        m_oLogPrintWriter = oLogPrintWriter;
        m_bLog = (m_oLogPrintWriter == null ? false : true);
    }

    public void run() {
        BufferedInputStream oBufInStream = null;
        String sDecoded = "";
        String sRemoteAddress;
        String sReferrer = "";
        String queryString = "";
        String requestHeader;
        String host = "";
        int foundHeaders = 0;
        int iStatus = 0;
        File oFile = null;
        try {
            sRemoteAddress = m_oSocket.getInetAddress().getHostAddress();
            m_oSocket.setSoTimeout(30000);
            BufferedReader oBrIn = new BufferedReader(new InputStreamReader(m_oSocket.getInputStream()));
            BufferedOutputStream oOutStr = new BufferedOutputStream(m_oSocket.getOutputStream());

            String sRequest = oBrIn.readLine();
            try {
                while (oBrIn.ready()) {
                    requestHeader = oBrIn.readLine();
                    if (requestHeader.toLowerCase().indexOf("referer:") > -1) {
                        sReferrer = getHeader(requestHeader);
                        foundHeaders += 1;
                    } else if (requestHeader.toLowerCase().indexOf("host:") > -1) {
                        host = getHeader(requestHeader);
                        foundHeaders += 1;
                    }
                    if (foundHeaders == 2) break;
                }
            } catch (IOException e) {
            }
            if (sRequest == null || !(sRequest.endsWith("HTTP/1.0") || sRequest.endsWith("HTTP/1.1"))) {
                sendError(oOutStr, "400 Bad Request", "Bad Request");
                iStatus = 400;
            } else if (!sRequest.startsWith("GET ")) {
                sendError(oOutStr, "501 Not Implemented", "Method Not Implemented");
                iStatus = 501;
            } else {
                String sPath = sRequest.substring(4, sRequest.length() - 9);
                try {
                    sDecoded = URLDecoder.decode(sPath, "UTF-8");
                } catch (Exception e) {
                    sendError(oOutStr, "404 Not Found", "File Not Found");
                    iStatus = 404;
                }
                if (iStatus == 0) {
                    int iPos = sDecoded.indexOf("?");
                    if (iPos > -1) {
                        queryString = sDecoded.substring(iPos);
                        sDecoded = sDecoded.substring(0, iPos);
                    }
                    if (sDecoded.length() > 8 && sDecoded.substring(0, 8).equals("/resolve") && !host.equals("")) {
                        try {
                            String port = "";
                            String path = sDecoded.substring(8);
                            iPos = host.indexOf(":");
                            if (iPos > -1) {
                                port = host.substring(iPos + 1);
                                host = host.substring(0, iPos);
                            }
                            InetAddress addr = InetAddress.getByName(host);
                            host = addr.getHostAddress();
                            if (!port.equals("")) host += ":" + port;
                            sendRedirect(oOutStr, "http://" + host + path + queryString);
                            iStatus = 302;
                        } catch (Exception e) {
                        }
                    }
                    if (iStatus == 0) {
                        oFile = new File(new File(m_oRootDir, sDecoded).getCanonicalPath());
                        if (!oFile.toString().startsWith(m_oRootDir.toString())) {
                            sendError(oOutStr, "403 Forbidden", "Forbidden");
                            iStatus = 403;
                        } else if (oFile.isDirectory()) {
                            File oIndexFile = new File(oFile, "default.htm");
                            if (!oIndexFile.exists() || oIndexFile.isDirectory()) {
                                sendError(oOutStr, "403 Forbidden", "Directory Listing Denied");
                                iStatus = 403;
                            } else {
                                oFile = oIndexFile;
                            }
                        } else if (!oFile.exists()) {
                            sendError(oOutStr, "404 Not Found", "File Not Found");
                            iStatus = 404;
                        }
                    }
                    if (iStatus == 0) {
                        String sContentType = WebServer.getMimeType(oFile);
                        if (sContentType.equals("forbidden")) {
                            sendError(oOutStr, "403 Forbidden", "Forbidden");
                            iStatus = 403;
                        } else {
                            oBufInStream = new BufferedInputStream(new FileInputStream(oFile));
                            sendHeader(oOutStr, "200 OK", sContentType, oFile.length(), oFile.lastModified());
                            iStatus = 200;
                            byte[] abtBuffer = new byte[4096];
                            int iBtRead;
                            while ((iBtRead = oBufInStream.read(abtBuffer)) != -1) {
                                oOutStr.write(abtBuffer, 0, iBtRead);
                            }
                            oBufInStream.close();
                            oOutStr.flush();
                            oOutStr.close();
                        }
                    }
                }
            }
            if (m_bLog) log(iStatus, sRemoteAddress, sRequest, sReferrer);
        } catch (IOException e) {
            if (oBufInStream != null) {
                try {
                    oBufInStream.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    private String getHeader(String rawHeader) {
        return rawHeader.substring(rawHeader.indexOf(":") + 2);
    }

    private void sendError(BufferedOutputStream oOutStr, String sCode, String sMessage) throws IOException {
        sMessage = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"><HTML><HEAD><TITLE>" + sCode +
                "</TITLE></HEAD><BODY><H1>" + sMessage + "</H1><HR><ADDRESS>" +
                m_sAppName + "/" + m_sAppVersion +
                "</ADDRESS></BODY></HTML>";
        sendHeader(oOutStr, sCode, "text/html", sMessage.length(), System.currentTimeMillis());
        oOutStr.write(sMessage.getBytes());
        oOutStr.flush();
        oOutStr.close();
    }

    private void sendRedirect(BufferedOutputStream oOutStr, String location) throws IOException {
        Hashtable<String, String> locationHt = new Hashtable<String, String>();
        locationHt.put("Location", location);
        String sMessage = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"><HTML><HEAD><TITLE>Object moved</TITLE>" +
                "</HEAD><BODY><H1>Object Moved</H1>This object may be found <A HREF=\"" + location +
                "\">here</A>.</BODY></HTML>";
        sendHeader(oOutStr, "302 Object moved", "text/html", sMessage.length(), System.currentTimeMillis(), locationHt);
        oOutStr.write(sMessage.getBytes());
        oOutStr.flush();
        oOutStr.close();
    }

    private void sendHeader(BufferedOutputStream oOutStr, String sCode, String sContentType, long lContentLength, long lLastModified, Hashtable additionalHeaders) throws IOException {
        String additionalHeader = "";
        Object key;
        if (additionalHeaders != null) {
            Enumeration keys = additionalHeaders.keys();
            while (keys.hasMoreElements()) {
                key = keys.nextElement();
                additionalHeader += "\r\n" + key.toString() + ": " + additionalHeaders.get(key).toString();
            }
        }
        oOutStr.write(("HTTP/1.0 " + sCode +
                "\r\nServer: " + m_sAppName + "/" + m_sAppVersion +
                "\r\nDate: " + WebServer.getHttpDate(new Date()) +
                "\r\nContent-Type: " + sContentType +
                "\r\nLast-Modified: " + WebServer.getHttpDate(new Date(lLastModified)) +
                additionalHeader +
                "\r\nContent-Length: " + lContentLength +
                "\r\n\r\n").getBytes());
    }

    private void sendHeader(BufferedOutputStream oOutStr, String sCode, String sContentType, long lContentLength, long lLastModified) throws IOException {
        sendHeader(oOutStr, sCode, sContentType, lContentLength, lLastModified, null);
    }

    private void log(int iStatus, String sRemoteAddress, String sRequest, String sReferrer) {
        Calendar cal = Calendar.getInstance();
        //m_oLogPrintWriter.println(BattleShipsUtility.getTimeStamp(cal) + " " + iStatus + " " +
          //      sRemoteAddress + " " + sRequest + " " + sReferrer);
        m_oLogPrintWriter.flush();
    }

}
