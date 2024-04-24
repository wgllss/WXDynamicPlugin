package com.wgllss.dynamic.plugin.runtime;

public class PluginStartStickyService extends HostPluginService {

    @Override
    public int onStartCommand() {
        return START_STICKY;
    }
}
