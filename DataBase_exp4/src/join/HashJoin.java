package join;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class HashJoin {

	public static byte[] freeBlock(byte[] blk, int index) {
		for (int i = index; i < index + 64; i++) {
			blk[i] = 0;
		}
		return blk;
	}
	
	public static void hash() {
		int addr;
		int index;
		int addrOut0 =0; 
		int addrOut1 =0; 
		int addrOut2 =0; 
		int addrOut3 =0; 
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();
		
		int indexRemainder0 = buf.getNewBlockInBuffer();
		int indexRemainder1 = buf.getNewBlockInBuffer();
		int indexRemainder2 = buf.getNewBlockInBuffer();
		int indexRemainder3 = buf.getNewBlockInBuffer();
		int tempIndex0 = indexRemainder0;
		int tempIndex1 = indexRemainder1;
		int tempIndex2 = indexRemainder2;
		int tempIndex3 = indexRemainder3;
		int count0 = 0;
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int RBlocks = 0;
		for(int i = 16 ;i<48;i++) {
			addr = i + 1;
			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/originData/"+addr);
			System.out.println(index);
			if (index == -1) { //每次放四块进去，充分利用缓冲区
				RBlocks = 4;
				i--;
			} else if(i!=47){
				continue;
			}
			for(int b = 0; b < RBlocks ;b++) {
				index = 261 + 65*b;
				for(int k = index ;k<index+56;k=k+8) {
					
					int R_A = Func.byteToInt(blk, k);
					int R_B = Func.byteToInt(blk, k + 4);
					int value = R_A%4;
					if(value == 0) {
						count0++;
						// 将目标元组写入缓存
						blk = Func.intToByte(blk, R_A, tempIndex0);
						tempIndex0 += 4;
						blk = Func.intToByte(blk, R_B, tempIndex0);
						tempIndex0 += 4;
						if (count0 == 8) {
							buf.writeBlockToDisk(indexRemainder0, "src/disk/HashBucket/00" + addrOut0);
							indexRemainder0 = buf.getNewBlockInBuffer();
							tempIndex0 = indexRemainder0;
							blk = freeBlock(blk, indexRemainder0);
							addrOut0++;
							count0 = 0;
						}
					}else if(value == 1) {
						count1++;
						// 将目标元组写入缓存
						blk = Func.intToByte(blk, R_A, tempIndex1);
						tempIndex1 += 4;
						blk = Func.intToByte(blk, R_B, tempIndex1);
						tempIndex1 += 4;
						if (count1 == 8) {
							buf.writeBlockToDisk(indexRemainder1, "src/disk/HashBucket/11" + addrOut1);
							indexRemainder1 = buf.getNewBlockInBuffer();
							tempIndex1 = indexRemainder1;
							blk = freeBlock(blk, indexRemainder1);
							addrOut1++;
							count1 = 0;
						}
					}else if(value == 2) {
						count2++;
						// 将目标元组写入缓存
						blk = Func.intToByte(blk, R_A, tempIndex2);
						tempIndex2 += 4;
						blk = Func.intToByte(blk, R_B, tempIndex2);
						tempIndex2 += 4;
						if (count2 == 8) {
							buf.writeBlockToDisk(indexRemainder2, "src/disk/HashBucket/22" + addrOut2);
							indexRemainder2 = buf.getNewBlockInBuffer();
							tempIndex2 = indexRemainder2;
							blk = freeBlock(blk, indexRemainder2);
							addrOut2++;
							count2 = 0;
						}
					}else {
						count3++;
						// 将目标元组写入缓存
						blk = Func.intToByte(blk, R_A, tempIndex3);
						tempIndex3 += 4;
						blk = Func.intToByte(blk, R_B, tempIndex3);
						tempIndex3 += 4;
						if (count3 == 8) {
							buf.writeBlockToDisk(indexRemainder3, "src/disk/HashBucket/33" + addrOut3);
							indexRemainder3 = buf.getNewBlockInBuffer();
							tempIndex3 = indexRemainder3;
							blk = freeBlock(blk, indexRemainder3);
							addrOut3++;
							count3 = 0;
						}
					}
				}
			}
			for (int j = 0; j < 4; j++) {
				buf.freeBlockInBuffer(261 + 65*j);
			}
		}
		if(count0 != 0) {
			buf.writeBlockToDisk(indexRemainder0, "src/disk/HashBucket/00" + addrOut0);
		}
		if(count1 != 0) {
			buf.writeBlockToDisk(indexRemainder1, "src/disk/HashBucket/11" + addrOut1);
		}
		if(count2 != 0) {
			buf.writeBlockToDisk(indexRemainder2, "src/disk/HashBucket/22" + addrOut2);
		}
		if(count3 != 0) {
			buf.writeBlockToDisk(indexRemainder3, "src/disk/HashBucket/33" + addrOut3);
		}
		Display.show("src/disk/HashBucket");
	}
	
	public static void main(String[] args) {
		hash();
		
		int indexR; // 缓冲区块偏移首地址
		int indexS; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int indexOut; // 缓冲区输出块偏移首地址
		int addrOut = 1056; // 输出到的磁盘块地址

		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();

		indexOut = buf.getNewBlockInBuffer();
		int tempIndexOut = indexOut;
		int count = 0;
		for(int i = 0 ; i < 3 ; i++) {
			addr = i;
			/* Read the block from the hard disk */
			indexR = buf.readBlockFromDisk("src/disk/HashBucket/3" + addr);
			
			for(int j = 0 ; j < 8 ;j ++) {
				addr = j;
				indexS = buf.readBlockFromDisk("src/disk/HashBucket/33" + addr);
				for(int k = indexR ; k < indexR + 64 ;k = k + 8) {
					int R_A = Func.byteToInt(blk, k);
					int R_B = Func.byteToInt(blk, k + 4);
					for(int s = indexS ; s<indexS +64;s=s+8) {
						int S_C = Func.byteToInt(blk, s);
						int S_D = Func.byteToInt(blk, s + 4);
						if (R_A == S_C && R_A != 0) {
							count++;
							// 将目标元组写入缓存
							blk = Func.intToByte(blk, R_A, tempIndexOut);
							tempIndexOut += 4;
							blk = Func.intToByte(blk, R_B, tempIndexOut);
							tempIndexOut += 4;
							blk = Func.intToByte(blk, S_D, tempIndexOut);
							tempIndexOut += 4;

							if (count == 5) {
								buf.writeBlockToDisk(indexOut, "src/disk/HashJoin/" + addrOut);
								indexOut = buf.getNewBlockInBuffer();
								tempIndexOut = indexOut;
								blk = freeBlock(blk, indexOut);
								addrOut++;
								count = 0;
							}
						}
					}
				}
				buf.freeBlockInBuffer(indexS);
			}
			buf.freeBlockInBuffer(indexR);
		}
		if(count != 0) {
			buf.writeBlockToDisk(indexOut, "src/disk/HashJoin/" + addrOut);
		}
		Display.showJoin("src/disk/HashJoin");
		
	}

}
