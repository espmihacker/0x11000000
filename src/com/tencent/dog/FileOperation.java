package com.tencent.dog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOperation {

	//创建文件
	public static void 创建文件(String path) {
        //新建一个File类型的成员变量，传入文件名路径。
		File mFile = new File(path);
        //判断文件是否存在，存在就删除
        if (mFile.exists()) {
            mFile.delete();
        }
        try {
			//创建文件
			mFile.createNewFile();
			//给一个吐司提示，显示创建成功

		} catch (IOException e) {
			e.printStackTrace();
        }


    }

	//创建文件夹
    public static void 创建文件夹(String path) {
        //新建一个File，传入文件夹目录
        File file = new File(path);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            file.mkdirs();
        }

    }


    /*
     * 写入文件内容
     */
    public static boolean 写入文件(String path, String txt) {
        byte[] sourceByte = txt.getBytes();
        if (null != sourceByte) {
            try {
                File file = new File(path); 
                if (!file.exists()) {   
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);
                outStream.write(sourceByte);
                outStream.close();  
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }



    /*
     * 读取文件内容
     */
    public static String 读取文件(String path)

    {

        String str="";

        File file=new File(path);

        try {

            FileInputStream in=new FileInputStream(file);

            // size  为字串的长度 ，这里一次性读完

            int size=in.available();

            byte[] buffer=new byte[size];

            in.read(buffer);

            in.close();

            str=new String(buffer,"GB2312");

        } catch (IOException e) {

            e.printStackTrace();

        }
        return str;
    }
}
