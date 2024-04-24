package com.wgllss.dynamic.plugin.runtime;

public class PluginProcessStartNotStickyService extends HostPluginService {

    @Override
    public int onStartCommand() {
        return START_NOT_STICKY;
    }
}
