package de.sevenfactory.argon;

import android.support.annotation.NonNull;

public class MockStore implements ConfigStore {
    private Object mConfig;

    <T> MockStore(@NonNull T config) {
        mConfig = config;
    }

    @Override
    public <T> void update(@NonNull T config) {
        // Only set config initially and discard any further updates:
        // Updates should not modify the config returned by getConfig
        // and no permanent storage is used for tests.
    }

    @Override
    public <T> T getConfig() {
        return (T) mConfig;
    }
}
