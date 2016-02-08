package de.sevenfactory.argon;

import android.support.annotation.NonNull;

public interface ConfigStore {
    <T> void update(@NonNull T config);

    <T> T getConfig();
}
