package com.mytest;

import java.io.ByteArrayOutputStream;
//�������ֽ��������������
class ResizableByteArrayOutputStream extends ByteArrayOutputStream{
	public void resize(int paramInt){
		this.count = paramInt;//��������ֽ���
	}
}