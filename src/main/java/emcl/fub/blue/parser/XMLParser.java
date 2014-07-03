package emcl.fub.blue.parser;

import emcl.fub.blue.entity.Timetable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: admin
 * Date: 6/8/14
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLParser {
    public static Timetable parseXMLFile(String path) {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            saxParser.parse(new File(path), handler);

            Timetable tt = handler.getTimetable();
            return tt;

            //Get Employees list

    } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
