package de.prosiebensat1digital.argondemo;

import com.google.gson.annotations.SerializedName;

import de.prosiebensat1digital.argon.annotation.ArgonName;

public class ConfigModel {
    public boolean showHeadline = true;
    public String text = "test";
    public int intValue = 0;
    public float floatValue = 0;
    @ArgonName(name="Long Value")
    public long longValue = 0;
}
