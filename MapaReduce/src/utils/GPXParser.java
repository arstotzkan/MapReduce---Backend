package utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

/**
 * A simple class that Parses .gpx files
 * @author Kharnifex
 */
public class GPXParser {

    private static Document readGPX(String gpxPath) throws ParserConfigurationException, SAXException, IOException {
        File gpx = new File(gpxPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(gpx);
        doc.getDocumentElement().normalize();
        return doc;
    }

    /**
     * Parses a .gpx file and returns a list of GPXWaypoints
     *
     * @param gpxPath the Path of the gpx file
     * @return ArrayList of GPXWaypoints
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws ParseException
     */
    public static ArrayList<GPXWaypoint> parseGPX(String gpxPath) throws ParserConfigurationException, IOException, SAXException, ParseException {
        Document doc = readGPX(gpxPath);
        NodeList nodeList = doc.getElementsByTagName("*");
        ArrayList<GPXWaypoint> waypoints = new ArrayList<GPXWaypoint>();
        String user = null; //not sure where we'll be using this but once we have DAOs its useful

        for (int i = 0; i < nodeList.getLength(); i++) {

            String tempLat = null;
            String tempLon = null;
            String tempEle = null;
            String tempTime = null;

            Node currNode = nodeList.item(i);

            if (currNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) currNode;

                if (currNode.getNodeName().equals("gpx")) {
                    user = element.getAttribute("creator");
                }

                else if (currNode.getNodeName().equals("wpt")){
                    tempLat = element.getAttribute("lat");
                    tempLon = element.getAttribute("lon");

                    String textContent = currNode.getTextContent().replace("\n", "");
                    String[] textArr = textContent.split(" ");
                    List<String> filtered =  Arrays.stream(textArr).toList().stream().filter(x -> !x.isEmpty()).toList();
                    tempEle = filtered.get(0);
                    tempTime = filtered.get(1).replace("T", " ").replace("Z", "");

                    waypoints.add(new GPXWaypoint(tempLat, tempLon, tempEle, tempTime));
                }
            }
        }
        return waypoints;
    }