package com.example.curatordemo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class ZkClient {
    private static final String ZK_ADDRESS = "39.96.85.85:2181";
    private static final String ZK_PATH = "/zktest";

    public static void main(String[] args) throws Exception{
        //初始化
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESS).sessionTimeoutMs(10000).retryPolicy(new ExponentialBackoffRetry(1000,5))
                .build();
        client.start();
 //       String data1="hello";
//        print("created:",ZK_PATH,data1);
//        //检查是否存在
//        Stat stat = client.checkExists().forPath(ZK_PATH);
//        //删除方法
//        if(stat != null){
//            client.delete()
//                    .guaranteed()
//                    .deletingChildrenIfNeeded()
//                    .withVersion(stat.getVersion())
//                    .forPath(ZK_PATH);
//            Thread.sleep(1000);
//        }
//        client.create().creatingParentsIfNeeded().forPath(ZK_PATH,data1.getBytes());
//        print("ls","/");
//        print(client.getChildren().forPath("/")+"");
//        print("get",ZK_PATH);
//        print(client.getChildren().forPath(ZK_PATH)+"");

        String nodePath ="/super/testNode";
//        byte[] data ="this is a test data".getBytes();
//        ///添加方法
//        String result = client.create().creatingParentsIfNeeded()
//                .withMode(CreateMode.PERSISTENT)
//                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//                .forPath(nodePath,data);
////        System.out.println(result+"node created");
//        byte[] modifyData = "this a new data".getBytes();
//        Stat modifyStat = client.setData().withVersion(0).forPath(nodePath,modifyData);
//        Stat getModifyData = new Stat();
//        byte[] rsData = client.getData().storingStatIn(getModifyData).forPath(nodePath);
//        System.out.println("获取的更新以后的数据为"+new String(rsData));
//
//        Thread.sleep(1000);

        //测试watcher 只是一次有作用，监听完一次再不监听
//        client.getData().usingWatcher(new CuratorWatcher()).forPath(nodePath);
//        Thread.sleep(1000000);

        //一次注册，多次监听
//        final NodeCache nodeCache = new NodeCache(client,nodePath);
//        nodeCache.start(true);
//        if(nodeCache.getCurrentData() != null){
//            System.out.println("初始化数据为："+new String(nodeCache.getCurrentData().getData()));
//        }
//        //添加watcher
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("变化状态为："+new String(nodeCache.getCurrentData().getData()));
//            }
//        });
//        Thread.sleep(200000);

        //子节点监听
//        final PathChildrenCache childrenCache = new PathChildrenCache(client,nodePath,true);
//        /**
//         * StartMode: 初始化方式
//         * POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
//         * NORMAL：异步初始化
//         * BUILD_INITIAL_CACHE：同步初始化
//         */
//        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
//        List<ChildData> childDataList = childrenCache.getCurrentData();
//        for(ChildData childData:childDataList){
//            System.out.println("子节点路径"+new String(childData.getPath())+" 数据为："+new String(childData.getData()));
//
//        }
//        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
//                // 通过判断event type的方式来实现不同事件的触发
//                if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {  // 子节点初始化时触发
//                    System.out.println("\n--------------\n");
//                    System.out.println("子节点初始化成功");
//                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {  // 添加子节点时触发
//                    System.out.println("\n--------------\n");
//                    System.out.print("子节点：" + event.getData().getPath() + " 添加成功，");
//                    System.out.println("该子节点的数据为：" + new String(event.getData().getData()));
//                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {  // 删除子节点时触发
//                    System.out.println("\n--------------\n");
//                    System.out.println("子节点：" + event.getData().getPath() + " 删除成功");
//                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {  // 修改子节点数据时触发
//                    System.out.println("\n--------------\n");
//                    System.out.print("子节点：" + event.getData().getPath() + " 数据更新成功，");
//                    System.out.println("子节点：" + event.getData().getPath() + " 新的数据为：" + new String(event.getData().getData()));
//                }
//            }
//        });
//        Thread.sleep(200000);

        //ACL


        //分布式锁
        Thread t1=new Thread(()->{
            dowithLock(client);
        },"t1");
        Thread t2=new Thread(()->{
            dowithLock(client);
        },"t2");
        t1.start();
        t2.start();
        // 关闭
        client.close();
        //获取状态
        System.out.println(client.getState()+"---");
    }

    private static void print(String... cmds){
        StringBuilder text = new StringBuilder("$ ");
        for(String cmd:cmds){
            text.append(cmd).append(" ");
        }
        System.out.println(text.toString());
    }

    //分布式锁
    private static void dowithLock(CuratorFramework client){
        InterProcessMutex lock = new InterProcessMutex(client,ZK_PATH);
        try {
            if(lock.acquire(10*1000,TimeUnit.SECONDS)){
                System.out.println(Thread.currentThread().getName()+"hold lock");
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+"release lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
