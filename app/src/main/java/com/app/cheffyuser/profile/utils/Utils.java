package com.app.cheffyuser.profile.utils;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Utils {



    public static String getCurrentPlace(Context context,String lat, String lon)
    {

        double latitude=Double.parseDouble(lat);
        double longitude=Double.parseDouble(lon);
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        //for getting current location using gps
        //https://developer.android.com/reference/android/location/Address
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

//        String city = addresses.get(0).getLocality();
//        String state = addresses.get(0).getAdminArea();
//        String country = addresses.get(0).getCountryName();
        // String postalCode = addresses.get(0).getPostalCode();

        // String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

        //txtLocation.setText(address);
        //Toast.makeText(this, "Post Code:"+postalCode+" "+knownName, Toast.LENGTH_SHORT).show();

        return address;
    }
}
