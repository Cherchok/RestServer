package ru.job4j.rest.sapData;

import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class XMLresponse {
    private String login;
    private String password;

    private String table;
    private String fieldsQuan;
    private String language;
    private String where;
    private String order;
    private String group;
    private String fieldNames;
    private String response;

    public XMLresponse() {
    }

    public XMLresponse(String table, String fieldsQuan, String language, String where,
                       String order, String group, String fieldNames) {
        this.table = table;
        this.fieldsQuan = fieldsQuan;
        this.language = language;
        this.where = where;
        this.order = order;
        this.group = group;
        this.fieldNames = fieldNames;
        this.response = responseToString();

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String responseToString() {
        // параметры авторизации
        String username = getLogin();
        String password = getPassword();

        // параметры веб сервиса
        String urn = "urn";
        String urnName = "ZTABLEREAD";
        String uri = "urn:sap-com:document:sap:rfc:functions";
        String destination = "http://support.alpeconsulting.com:8201/sap/bc/srt/rfc/sap/ztablereadws/100/" +
                "ztablereadws/ztablereadws";

        // create XML request , get connection, get XML response as a string/
        try {
            // First create the connection
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = soapConnFactory.createConnection();

            // Next, create the actual message
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();

            // авторизация
            String authorization = new sun.misc.BASE64Encoder().encode((username + ":" + password).getBytes());
            MimeHeaders hd = message.getMimeHeaders();
            hd.addHeader("Authorization", "Basic " + authorization);


            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();

            envelope.addNamespaceDeclaration(urn, uri);

            // Create and populate the body
            SOAPBody body = envelope.getBody();

            // enter params for connection
            SOAPElement bodyElement = body.addChildElement(envelope.createName("urn:" + urnName));

            // Add parameters
            bodyElement.addChildElement("FIELDNAMES").addTextNode(fieldNames);
            bodyElement.addChildElement("GROUP").addTextNode(group);
            bodyElement.addChildElement("ORDER").addTextNode(order);
            bodyElement.addChildElement("WHERE").addTextNode(where);
            bodyElement.addChildElement("LANG").addTextNode(language);
            bodyElement.addChildElement("FIELDSQUAN").addTextNode(fieldsQuan);
            bodyElement.addChildElement("TABLENAME").addTextNode(table);


            // Save the message
            message.saveChanges();

            //  message.writeTo(System.out);

            // Send the message and get the reply
            SOAPMessage reply = connection.call(message, destination);

            //get response as a string
            final StringWriter sw = new StringWriter();
            try {
                TransformerFactory.newInstance().newTransformer().transform(
                        new DOMSource(reply.getSOAPPart()),
                        new StreamResult(sw));
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
            response = sw.toString();

            // Close the connection
            connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
