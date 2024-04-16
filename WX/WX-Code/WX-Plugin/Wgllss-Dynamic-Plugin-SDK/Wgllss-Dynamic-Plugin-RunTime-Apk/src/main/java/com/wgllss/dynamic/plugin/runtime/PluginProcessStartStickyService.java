package com.wgllss.dynamic.plugin.runtime;

public class PluginProcessStartStickyService extends HostPluginService {

    @Override
    public int onStartCommand() {
        return START_STICKY;
    }
}
