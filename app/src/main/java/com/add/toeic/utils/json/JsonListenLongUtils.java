package com.add.toeic.utils.json;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;

import com.add.toeic.model.practice.ListenLong;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by sev_user on 12/29/2016.
 */

public class JsonListenLongUtils {

    public static final String TABLE_TAB = "Questions";
    public static final String VALUE_ANSWER = "answer";
    public static final String VALUE_QUESTION = "questions";
    public static final String VALUE_POINTS = "points";
    public static final String VALUE_DURATION = "duration_in_seconds";
    public static final String VALUE_URL_AUDIO = "url_audio";
    public static final String VALUE_NEGATIVE = "negative_points";
    public static final String VALUE_OPTION = "options";
    public static final String VALUE_OPTION_1 = "option_1";
    public static final String VALUE_OPTION_2 = "option_2";
    public static final String VALUE_OPTION_3 = "option_3";
    public static final String VALUE_QUESTION_TYPE = "question_type";

    public static String readeJsonText(Context context, String nameJson) {
        try {
            String jsonLocation = AssetJSONFile(nameJson, context);
            JSONObject json = (JSONObject) new JSONTokener(jsonLocation).nextValue();
            JSONArray json2 = json.getJSONArray(TABLE_TAB);
            return json2.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String AssetJSONFile(String filename, Context context) throws IOException {
        String PATH_ASSETS = "practice/";
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(PATH_ASSETS + filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        String s = new String(formArray);
        return s;
    }

    public static ArrayList<ListenLong> readerOjectFromJson(Context context, String nameJson) throws IOException {

        String jsontext = readeJsonText(context, nameJson);
        //  Log.i("duy.pq", "1=" + jsontext);
        JsonReader jsonReader = new JsonReader(new StringReader(jsontext));
        return readObjectControlFromJson(jsonReader);
    }

    public static ArrayList<ListenLong> readObjectControlFromJson(JsonReader reader) throws IOException {
        ArrayList<ListenLong> messages = new ArrayList<ListenLong>();
        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }


    public static ListenLong readMessage(JsonReader reader) throws IOException {

        ListenLong listenLong = new ListenLong();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(VALUE_ANSWER)) {
                int[] kq = new int[3];
                reader.beginArray();
                int count = 0;
                while (reader.hasNext()) {
                    kq[count] = reader.nextInt();
                    count++;
                }
                listenLong.setAnswer(kq);
                reader.endArray();

            } else if (name.equals(VALUE_QUESTION)) {
                String[] strings = new String[3];
                reader.beginArray();
                int count = 0;
                while (reader.hasNext()) {
                    strings[count] = reader.nextString();
                    count++;
                }
                listenLong.setQuestions(strings);
                reader.endArray();

            } else if (name.equals(VALUE_POINTS)) { // && reader.peek() != JsonToken.NULL) {
                listenLong.setPoints(reader.nextInt());
            } else if (name.equals(VALUE_DURATION)) {
                listenLong.setDuration_in_seconds(reader.nextInt());
            } else if (name.equals(VALUE_URL_AUDIO)) {
                listenLong.setUrl_audio(reader.nextString());
            } else if (name.equals(VALUE_NEGATIVE)) {
                listenLong.setNegative_points(reader.nextInt());
            } else if (name.equals(VALUE_OPTION)) {

                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    String option = reader.nextName();
                    if (option.equals(VALUE_OPTION_1)) {
                        String[] strings = new String[4];
                        reader.beginArray();
                        int count = 0;
                        while (reader.hasNext()) {
                            strings[count] = reader.nextString();
                            count++;
                        }
                        listenLong.setOption_1(strings);
                        reader.endArray();
                    } else if (option.equals(VALUE_OPTION_2)) {
                        String[] strings = new String[4];
                        reader.beginArray();
                        int count = 0;
                        while (reader.hasNext()) {
                            strings[count] = reader.nextString();
                            count++;
                        }
                        listenLong.setOption_2(strings);
                        reader.endArray();
                    } else if (option.equals(VALUE_OPTION_3)) {
                        String[] strings = new String[4];
                        reader.beginArray();
                        int count = 0;
                        while (reader.hasNext()) {
                            strings[count] = reader.nextString().toString();
                            count++;
                        }
                        listenLong.setOption_3(strings);
                        reader.endArray();
                    }
                    reader.endObject();
                }
                reader.endArray();

            } else if (name.equals(VALUE_QUESTION_TYPE)) {
                listenLong.setQuestion_type(reader.nextInt());
            }
        }
        reader.endObject();
        return listenLong;
    }
}
