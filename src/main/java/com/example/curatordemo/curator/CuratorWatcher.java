package com.example.curatordemo.curator;

import org.apache.zookeeper.WatchedEvent;

public class CuratorWatcher implements org.apache.curator.framework.api.CuratorWatcher {

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        System.out.println("触发watcher，节点路径为"+watchedEvent.getPath()+"----"+watchedEvent.getType());
    }
}
