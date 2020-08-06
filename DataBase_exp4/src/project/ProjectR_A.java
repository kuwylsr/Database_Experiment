package project;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class ProjectR_A {

	public static byte[] freeBlock(byte[] blk,int index) {
		for(int i = index ;i<index+64;i++) {
			blk[i] = 0;
		}
		return blk;
	}
	
	public static void main(String[] args) {
		
		int index; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int indexOut; // 缓冲区输出块偏移首地址
		int addrOut = 1000; // 输出到的磁盘块地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();
		
		indexOut = buf.getNewBlockInBuffer();
		int tempIndexOut = indexOut;
		int count = 0;
		int preNum = 0;
		for(int i =0 ;i<14;i++) {
			
			addr = i + 1;		
			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/SortedFinalData/"+addr);
			int k = index;
			int j = k + 8;
			while(k< index + 64) {
				int R_A1 = Func.byteToInt(blk, k);
				int R_A2 = Func.byteToInt(blk, j);
				//将目标元组写入缓存
				if(R_A1 != preNum) {//去除与前一个磁盘块重复的元组
					count ++;
					blk = Func.intToByte(blk, R_A1, tempIndexOut);
					tempIndexOut += 4;	
				}
				while(R_A1 == R_A2) { //去除同一磁盘块内重复的元组
					j = j + 8;
					R_A2 = Func.byteToInt(blk, j);
				}
				k = j;
				j = j + 8;
				
				if((count == 16)) {
					buf.writeBlockToDisk(indexOut, "src/disk/projectR_A/"+addrOut);
					indexOut = buf.getNewBlockInBuffer();
					tempIndexOut = indexOut;
					blk = freeBlock(blk, indexOut);
					addrOut ++;
					count = 0;
				}
			}
			if((i == 13)&&count!=0) {
				buf.writeBlockToDisk(indexOut, "src/disk/projectR_A/"+addrOut);
				indexOut = buf.getNewBlockInBuffer();
				tempIndexOut = indexOut;
				blk = freeBlock(blk, indexOut);
				addrOut ++;
				count = 0;
			}
			buf.freeBlockInBuffer(index);
			preNum = Func.byteToInt(blk, index+56);
			//System.err.println(preNum);
		}
		//Display.show("src/disk/SortedFinalData");
		Display.show("src/disk/projectR_A");
	}

}
