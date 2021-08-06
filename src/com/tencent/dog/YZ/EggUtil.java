package com.tencent.dog.YZ;
import java.io.*;
import android.telephony.*;
import android.os.*;
import java.lang.reflect.*;
import android.content.*;
import java.security.*;
import android.widget.*;
import com.alibaba.fastjson.*;
import RC4;
public class EggUtil
{
	private static final String hexDigIts[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	private String inToStr(InputStream in)
	{
		StringBuilder sb=new StringBuilder();
		try
		{
			InputStreamReader isr=new InputStreamReader(in);
			BufferedReader buf=new BufferedReader(isr);
			String strLine="";
			while ((strLine = buf.readLine()) != null)
			{
				sb.append(strLine);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}


	public static String getImei(Context context)
	{
		//String p=context.getFilesDir().getPath();
		String p="/sdcard";
		String f="/imeiKey10001";
		if (!new File(p + f).isFile())
		{
			EggUtil.createImei(p, f,context);
		}
		String r= RC4.decrypt(EggUtil.readFile(p + f),EasyConfig.APIKEY);
		
		return EggUtil.getid(context);
	}
	
	
	public static String getid(Context context){
	    return  android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

	public static boolean createImei(String p, String f,Context context)
	{

		boolean b=writeFile(p + f,RC4.encrypt(getMD5(System.currentTimeMillis()+""),EasyConfig.APIKEY));
		if (b)
		{
			return true;
		}
		return false;
	}


	public static String getMD5(String origin)
	{
		String charsetname="utf8";
        String resultString = null;
        try
		{
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (null == charsetname || "".equals(charsetname))
			{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }
			else
			{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }
		catch (Exception e)
		{
        }
        return resultString;
    }



	public static String str2HexStr(String str)
	{
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++)
		{
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }


    public static String byteArrayToHexString(byte b[])
	{
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
		{
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    public static String byteToHexString(byte b)
	{
        int n = b;
        if (n < 0)
		{
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigIts[d1] + hexDigIts[d2];
    }



	public static boolean isJson(String content)
	{
		try
		{
			JSONObject jsonStr= JSONObject.parseObject(content);
			return  true;
		}
		catch (Exception e)
		{
			return false;
		}
	}


	public static boolean writeFile(String filePath, String con)
	{

		try
		{
			File file =new File(filePath);

			if (!file.isFile())
			{
				file.createNewFile();
			}

			Writer out =new FileWriter(file);
			out.write(con);
			out.close();
		}
		catch (FileNotFoundException e)
		{}
		catch (IOException e)
		{}
		return false;
	}

	public static String readFile(String fileName)
	{
		StringBuffer strB=null;
		try
		{
            strB = new StringBuffer();
            FileInputStream in = new FileInputStream(new File(fileName));
            Reader reader2 = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader2);
            String line;
			String end="\n";
            while ((line = bufferedReader.readLine()) != null)
			{
				strB.append(line + end.trim());
            }
            bufferedReader.close();
        }
		catch (Exception e)
		{
            // TODO: handle exception
        }

		return strB.toString();
	}
}
