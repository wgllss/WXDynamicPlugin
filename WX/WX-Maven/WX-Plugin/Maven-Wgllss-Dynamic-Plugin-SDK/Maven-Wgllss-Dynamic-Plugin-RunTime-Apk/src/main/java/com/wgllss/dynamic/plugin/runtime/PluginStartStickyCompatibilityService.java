package com.wgllss.dynamic.plugin.runtime;

public class PluginStartStickyCompatibilityService extends HostPluginService {

    @Override
    public int onStartCommand() {
        return START_STICKY_COMPATIBILITY;
    }
}
