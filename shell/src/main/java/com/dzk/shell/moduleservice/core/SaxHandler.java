package com.dzk.shell.moduleservice.core;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;

public class SaxHandler extends DefaultHandler {
    private HashMap<String,String>protocals;
    private String key,value;
    private String content;
    public SaxHandler() {

    }

    public HashMap<String, String> getProtocals() {
        return protocals;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        protocals = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        content = new String(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if ("sub-class".equals(qName)){
            key = content;
        }else if ("target-class".equals(qName)){
            value = content;
        }else if ("module-service".equals(qName)){
            protocals.put(key,value);
        }
    }
}
