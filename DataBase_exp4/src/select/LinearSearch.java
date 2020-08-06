package select;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class LinearSearch {

	
	
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
		
		indexOut = buf.getNewBlockInBuffer(); //输出的缓冲区块
		int tempIndexOut = indexOut;
		int count = 0;
		for(int i = 0 ;i < 48; i++) { //遍历所有的磁盘块
			addr = i + 1;		
			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/originData/"+addr);
			if(index == -1) {
				buf.freeBlockInBuffer(520-64);
				i--;
				continue;
			}
			if(i<16) { //R relationship
				for(int k = index ; k< index + 56;k=k+8) {
					int R_A = Func.byteToInt(blk, k);
					int R_B = Func.byteToInt(blk, k+4);
					if(R_A==40) {
						count ++;
						//将目标元组写入缓存
						blk = Func.intToByte(blk, R_A, tempIndexOut);
						tempIndexOut += 4;
						blk = Func.intToByte(blk, R_B, tempIndexOut);
						tempIndexOut += 4;	
					}	
				}
			}else { //S relationship
				for(int k = index ; k< index + 56;k=k+8) {
					int S_C = Func.byteToInt(blk, k);
					int S_D = Func.byteToInt(blk, k+4);
					if(S_C==60) {
						count ++;
						//将目标元组写入缓存
						blk = Func.intToByte(blk, S_C, tempIndexOut);
						tempIndexOut += 4;
						blk = Func.intToByte(blk, S_D, tempIndexOut);
						tempIndexOut += 4;	
					}	
				}
			}
			if((count == 8) || ((i == 47) &&(count != 0))) { //缓冲区中中的块写满，或者是读到最后，将缓冲区写入本地文件磁盘块
				buf.writeBlockToDisk(indexOut, "src/disk/linearSelect/"+addrOut);
				indexOut = buf.getNewBlockInBuffer();
				tempIndexOut = indexOut;
				blk = freeBlock(blk, indexOut);
				addrOut ++;
				count = 0;
			}
			buf.freeBlockInBuffer(index);
		}
		Display.show("src/disk/linearSelect");
	}

}
