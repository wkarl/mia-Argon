# Argon-Android
![logo](https://github.com/7factory/mia-Argon/raw/gh-pages/images/argon_400px.png?raw=true "Argon")

Fast integration of custom configuration and debug functionality in your app.

Argon maintains a singleton instance that holds, persists and updates a user defined configuration object of any type.

Debug mode can be enabled at any time to conveniently modify this configuration from.
Whenever your application is running with `Argon` in debug mode, a configurable notification icon is displayed. This notification links to a configuration activity where you can modify the fields defined in your custom configuration object. This object is consistently stored and updated by `Argon`. Changes to the configuration are applied by restarting the app's process to maintain consistency.

You can also adjust the configuration programmatically during runtime using the `Argon` API but changes will only be visible after a restart.

## Using Gradle ##

Add the following lines to your root build.gradle:

``` gradle
allprojects {
    repositories {
        [...]
        maven { url "https://jitpack.io" }
    }
}
```

Then reference the library from your module's build.gradle:

``` gradle
dependencies {
    [...]
    compile 'com.github.7factory:mia-Argon:x.y.z'
}
```

[![Release](https://jitpack.io/v/7factory/mia-Argon.svg)](https://jitpack.io/#7factory/mia-Argon)

## Integration ##

Argon is designed to be easily integrated and configured from your `Application` class. 

1. Create a POJO with your configuration. In the demo example the configuration is deserialized from JSON which might be stored as an asset to provide a default configuration but this is not a necessity.
 ```
String json = "{\n" +
                "\"showHeadline\": true,\n" +
                "\"text\": test,\n" +
                "\"intValue\": 20,\n" +
                "\"longValue\": 200000000000000000,\n" +
                "\"floatValue\": 0.5823\n" +
                "}";
        Gson gson = new Gson();
        ConfigModel defaultConfig = gson.fromJson(json, ConfigModel.class);
```
Optionally, you can use annotations to name your POJO's fields:
 ```
 public class ConfigModel {
    @Name("Headline Flag")
    public boolean showHeadline;
    @Name("Text")
    public String text;
    @Name("Int Value")
    public int intValue;
    @Name("Float Value")
    public float floatValue;
    @Name("Long Value")
    public long longValue;
}
```
The raw field names will be used as labels in the configuration `Activity` if no annotations were specified.

2. Init `Argon` from your `Application` class's `onCreate` Method and configure its appearance. Please note that you should not initialize `Argon` from an `Activity` to ensure correct lifecycle handling.
 ``` 
Argon.init(this, ConfigModel.class, defaultConfig)
                .setIcon(R.drawable.ic_debug)
                .setTitle(R.string.notification_title)
                .setText(R.string.notification_text)
                .setColor(R.color.colorAccent);
```

3. Retrieve your configuration from your components with `Argon.getConfig();`.

4. If you would like to update your configuration, use `Argon.updateConfig(configurationObject);`. Please not that due to consistency considerations these changes will only be available after your app process has been restarted.

[![Build Status](https://travis-ci.org/7factory/mia-Argon.svg?branch=master)](https://travis-ci.org/7factory/mia-Argon)
