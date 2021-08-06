package com.tencent.dog.YZ;

final public class EasyConfig
{
    final public static String APPID="1";
    //改成你的软件APPID
    
	final public static String HOST="http://test932.akul.gq/api.card/"+EasyConfig.APPID;
    //域名test.giao.cc改成自己的，后面的不用改
    
	final public static String APIKEY="d294516fedc9a4062e55143274740185";
    //改成自己的软件的apikey，在开发文档有
    
	final public static String PASSLIST="6IL7Bebvyt8gjHZVTdP2qDYlMU1sOpm5N/QXrfcxC40zk9S+JhEAinWaRuG3KoFw";
    //改成你的BASE64编码集，在设置的数据加密配置里有
    
	private EasyConfig(){}

}


/*
使用本源码对接Giao后台系统,需要创建一个单码类软件

创建单码类软件后
，进入管理
以下是需要在后台设置开启的东西:


1.设置-接口加密配置-数据加密方式:设置成 BASE64算法

2.设置-接口加密配置-数据加密转换方式:设置成 转十六进制

3.设置-接口加密配置-全局传输加密功能总开关:开启

4.设置-接口加密配置-全局数据传输参数值加密:开启

5.设置-接口加密配置-全局数据返回传输加密:开启


6.设置-接口加密配置-全局返回值效验返回:开启


其余的按系统默认即可,没说要开启的别去开启

本源码对接了单码登录、单码解绑、软件使用数统计功能，可根据开发文档提供的接口进行对接更多功能，例如公告，更新，代理等
*/
