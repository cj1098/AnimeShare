package cj1098.animeshare.util;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import cj1098.animeshare.xmlobjects.Anime;
import cj1098.animeshare.xmlobjects.Info;
import cj1098.animeshare.xmlobjects.Item;

/**
 * Created by chris on 12/26/16.
 */

@Singleton
public class XMLUtil {

    private static final String TAG = XMLUtil.class.getSimpleName();

    private DatabaseUtil mDatabaseUtil;

    public XMLUtil(DatabaseUtil databaseUtil) {
        mDatabaseUtil = databaseUtil;

    }

    public List<Item> parseInitialFullList(String fullList) {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            final XmlPullParser parser = pullParserFactory.newPullParser();

            final InputStream is = new ByteArrayInputStream(fullList.getBytes("UTF-8"));
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            return parseInitialFullList(parser);
        } catch (final XmlPullParserException | IOException e) {
            Log.e(TAG, e.toString());
        }
        return new ArrayList<>();
    }

    private List<Item> parseInitialFullList(XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if ("item".equalsIgnoreCase(name)) {
                        item = new Item();
                    }
                    else if ("id".equalsIgnoreCase(name)) {
                        item.setId(parser.nextText());
                    }
                    else if ("name".equalsIgnoreCase(name)) {
                        item.setName(parser.nextText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ("item".equalsIgnoreCase(name)) {
                        itemList.add(item);
                    }
            }
            eventType = parser.next();
        }
        return itemList;
    }

    public List<Anime> parseFiftyAnimeEntries(String fiftyEntries)  {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            final XmlPullParser parser = pullParserFactory.newPullParser();

            final InputStream is = new ByteArrayInputStream(fiftyEntries.getBytes("UTF-8"));
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            return parseFiftyAnimeEntries(parser);
        } catch (final XmlPullParserException | IOException e) {
            Log.e(TAG, e.toString());
        }
        return new ArrayList<>();
    }

    private List<Anime> parseFiftyAnimeEntries(XmlPullParser parser) throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
        List<Anime> animeList = new ArrayList<>();
        Anime animeObject = new Anime();
        Info infoObject;
        List<Info> infoObjectList = new ArrayList<>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if ("anime".equalsIgnoreCase(name)) {
                        animeObject = new Anime();
                        animeObject.setId(parser.getAttributeValue(null, "id"));
                        animeObject.setGid(parser.getAttributeValue(null, "gid"));
                        animeObject.setType(parser.getAttributeValue(null, "type"));
                        animeObject.setName(parser.getAttributeValue(null, "name"));
                        animeObject.setPrecision(parser.getAttributeValue(null, "precision"));
                        animeObject.setGeneratedOn(parser.getAttributeValue(null, "generated-on"));
                    }
                    else if ("info".equalsIgnoreCase(name)) {
                        infoObject = new Info();
                        infoObject.setGid(parser.getAttributeValue(null, "gid"));
                        infoObject.setType(parser.getAttributeValue(null, "type"));
                        infoObject.setThumbnailImageSrc(parser.getAttributeValue(null, "src"));
                        infoObject.setWidth(parser.getAttributeValue(null, "width"));
                        infoObject.setHeight(parser.getAttributeValue(null, "height"));
                        infoObject.setLang(parser.getAttributeValue(null, "lang"));
                        infoObject.setWebsiteUrl(parser.getAttributeValue(null, "href"));
                        infoObject.setText(returnNextTextIfAvailable(parser, parser.getAttributeValue(null, "type")));
                        infoObjectList.add(infoObject);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if ("anime".equalsIgnoreCase(name)) {
                        animeObject.setInfoList(infoObjectList);
                        animeList.add(animeObject);
                        infoObjectList = new ArrayList<>();
                    }
            }
            eventType = parser.next();
        }
        return animeList;
    }



    private String returnNextTextIfAvailable(XmlPullParser parser, String type) throws XmlPullParserException, IOException {
        String nextText = "";
        boolean hasNextText = false;
        switch (type) {
            case "Main title":
            case "Alternative title":
            case "Vintage":
            case "Opening Theme":
            case "Ending Theme":
            case "Official website":
                hasNextText = true;
                default:
        }
        if (hasNextText) {
            return parser.nextText();
        }
        else {
            return nextText;
        }
    }

}
