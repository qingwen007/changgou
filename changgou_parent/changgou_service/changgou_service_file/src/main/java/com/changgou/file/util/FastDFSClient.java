package com.changgou.file.util;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FastDFSClient {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);

    /***
     * 初始化加载FastDFS的TrackerServer配置
     */
    static {
        try {
            String filePath=new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
            ClientGlobal.init(filePath);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param file
     * @return
     */
    public  static  String [] upload(FastDFSFile file)
    {
        //获取文件的作者
        NameValuePair [] meta_list=new NameValuePair[1];
        meta_list[0]=new NameValuePair("author",file.getAuthor());
        //接收返回数据
        String [] uploadResult=null;
        StorageClient storageClient=null;

        /***
         * 文件上传
         * 1)文件字节数组
         * 2)文件扩展名
         * 3)文件作者
         */
        try {
            uploadResult = storageClient.upload_file(file.getContent(),file.getExt(),meta_list);
        } catch (IOException e) {
            logger.error("Exception when uploadind the file:" + file.getName(), e);
        } catch (MyException e) {
            e.printStackTrace();
        }
        if(uploadResult==null&&storageClient!=null)
        {
            logger.error("upload file fail, error code:" + storageClient.getErrorCode());
        }
        //获取组名
        String groupName=uploadResult[0];
        //获取文件存储路径
        String remoteFileName=uploadResult[1];

        return  uploadResult;
    }

    /***
     * 获取文件信息
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        try {
            StorageClient storageClient = getTrackerClient();
            return storageClient.get_file_info(groupName, remoteFileName);
        } catch (Exception e) {
            logger.error("Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }

    public static InputStream downFile(String groupName,String remoteFileName)
    {
        try {
            //创建StorageClient
            StorageClient storageClient= getTrackerClient();
            byte [] fileByte=storageClient.download_file(groupName,remoteFileName);
            InputStream inputStream=new ByteArrayInputStream(fileByte);
            return  inputStream;

        }catch (Exception e) {
            logger.error("Exception: Get File from Fast DFS failed", e);
        }
        return null;
    }
    /***
     * 文件删除
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     */
    public  static  void  deleteFile(String groupName,String remoteFileName) throws IOException, MyException {

        StorageClient storageClient=getTrackerClient();
        int i=storageClient.delete_file(groupName,remoteFileName);
    }

    /***
     * 获取Storage组
     * @param groupName
     * @return
     * @throws IOException
     */
    public static  StorageServer [] getStoreStorages(String groupName) throws IOException {
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer= trackerClient.getConnection();
        return  trackerClient.getStoreStorages(trackerServer,groupName);
    }
    /***
     * 获取Storage信息,IP和端口
     * @param groupName
     * @param remoteFileName
     * @return
     * @throws IOException
     */
    public  static  ServerInfo[] getFetchStorages(String groupName,String remoteFileName) throws IOException {
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getConnection();
        return  trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }
    /***
     * 获取Tracker服务地址
     * @return
     * @throws IOException
     */
    public  static  String getTrackerUrl() throws IOException {
        return  "http://"+getTrackerServer().getInetSocketAddress().getHostString()+":"+ClientGlobal.getG_tracker_http_port()+"/";
    }
    /***
     * 获取Storage客户端
     * @return
     * @throws IOException
     */
    private  static  StorageClient getTrackerClient() throws IOException {
        TrackerServer trackerServer=getTrackerServer();
        StorageClient storageClient=new StorageClient(trackerServer,null);
        return  storageClient;
    }

    /***
     * 获取Tracker
     * @return
     * @throws IOException
     */
    private static TrackerServer getTrackerServer() throws IOException {
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer= trackerClient.getConnection();
        return  trackerServer;

    }

}
