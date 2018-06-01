# Java工具类

## FileUtils

* deleteSameFile(String path)——删除指定目录下的相同文件

## ConvertUtils

* bytes2HexString(byte[] data)——字节数组转十六进制字符串

## VerifyUtils

* fileChecksum(String path, String algorithm)——生成文件校验码
* stringChecksum(String string, String algorithm)——生成字符串校验码

## CloseUtils

* close(Closeable closeable)——关闭输入输出流(实现了Closeable接口的对象)

## MiscUtils

* generateSoftwareInfo(String path)——获取Window系统上所有已安装的软件信息，并存储到指定文件中

## TongGou——图的同构算法

## LnkParser——获取Window快捷方式所指向的真实路径

## FileCode，FileCharsetDetector——文件编码检测和文件转码
