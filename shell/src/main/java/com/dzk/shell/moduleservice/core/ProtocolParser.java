package com.dzk.shell.moduleservice.core;

import android.content.Context;
import android.content.res.AssetManager;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ProtocolParser {
    public static HashMap<String, String> parse(Context context, String assetFile) throws Exception {
        AssetManager assets = context.getAssets();
        //assets文件打开转换成输入流
        InputStream is = assets.open(assetFile);
        InputSource inputSource = new InputSource(is);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        //获取SaxParser
        SAXParser saxParser = factory.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        SaxHandler saxHandler = new SaxHandler();
        xmlReader.setContentHandler(saxHandler);
        //解析读取
        xmlReader.parse(inputSource);
        return saxHandler.getProtocals();
    }
}
