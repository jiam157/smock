package com.mytest;

import java.io.ByteArrayOutputStream;
//调整后字节数组输出流定义
class ResizableByteArrayOutputStream extends ByteArrayOutputStream{
	public void resize(int paramInt){
		this.count = paramInt;//输出流的字节数
	}
}