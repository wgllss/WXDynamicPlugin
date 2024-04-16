package com.wgllss.dynamic.plugin.runtime;

public class PluginProcessStartRedeliverIntentService extends HostPluginService {

    @Override
    public int onStartCommand() {
        return START_REDELIVER_INTENT;
    }
}
