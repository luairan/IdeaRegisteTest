package com.example.demo;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping()
public class MainController {

    private static final  String pingContent
        = "<PingResponse><message></message><responseCode>OK</responseCode><salt>%s</salt></PingResponse>";

    private static final String tikcetContent
        = "<ObtainTicketResponse><message></message><prolongationPeriod>%s</prolongationPeriod><responseCode>OK"
        + "</responseCode><salt>%s</salt><ticketId>1</ticketId><ticketProperties>licensee=%s\tlicenseType=0\t"
        + "</ticketProperties></ObtainTicketResponse>";

    private static final long prolongationPeriod = 607875500;

    @ResponseBody
    @GetMapping("/")
    public String root() {
        return "Server is starting!";
    }

    @GetMapping("/rpc/ping.action")
    public void ping(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                     @RequestParam("salt") String salt)
        throws Exception {
        httpServletRequest.setCharacterEncoding("UTF-8");
        //httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.addHeader("Content-Type", "text/xml");
        //httpServletResponse.setStatus(200,"OK");

        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(httpServletResponse.getOutputStream()));
        String xmlResponse = String.format(pingContent, salt);
        String xmlsign = RSAtes.bytes2HexString(RSAtes.sign(xmlResponse.getBytes("UTF-8")));

        StringBuilder sb = new StringBuilder();
        sb.append("<!-- ");
        sb.append(xmlsign);
        sb.append(" -->\n");
        sb.append(xmlResponse);
        printWriter.append(sb.toString());
        httpServletResponse.addHeader("Content-Length", String.valueOf(sb.toString().getBytes().length));
        printWriter.flush();
        //printWriter.close();

        //httpServletResponse.getOutputStream().close();
    }

    @GetMapping("/rpc/obtainTicket.action")
    public void obtainTicket(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             @RequestParam("salt") String salt,
                             @RequestParam("userName") String userName)
        throws Exception {
        httpServletRequest.setCharacterEncoding("UTF-8");
        //httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.addHeader("Content-Type", "text/xml");
        //httpServletResponse.setStatus(200,"OK");
        if (!StringUtils.hasText(salt) || !StringUtils.hasText(salt)) {
            httpServletResponse.sendError(500, "error1231231");
            return;
        }
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(httpServletResponse.getOutputStream()));
        String xmlResponse = String.format(tikcetContent, String.valueOf(prolongationPeriod), salt, userName);
        String xmlsign = RSAtes.bytes2HexString(RSAtes.sign(xmlResponse.getBytes("UTF-8")));

        StringBuilder sb = new StringBuilder();

        sb.append("<!-- ");
        sb.append(xmlsign);
        sb.append(" -->\n");
        sb.append(xmlResponse);
        printWriter.append(sb.toString());

        httpServletResponse.addHeader("Content-Length", String.valueOf(sb.toString().getBytes().length));
        printWriter.flush();
        //printWriter.close();
        //httpServletResponse.getOutputStream().close();

    }
}
