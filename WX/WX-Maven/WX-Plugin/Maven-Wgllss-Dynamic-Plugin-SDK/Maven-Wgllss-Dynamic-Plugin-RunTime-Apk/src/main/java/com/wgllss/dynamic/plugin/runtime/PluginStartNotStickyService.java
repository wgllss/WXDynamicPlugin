package com.wgllss.dynamic.plugin.runtime;

public class PluginStartNotStickyService extends HostPluginService {

    @Override
    public int onStartCommand() {
        return START_NOT_STICKY;
    }
}
