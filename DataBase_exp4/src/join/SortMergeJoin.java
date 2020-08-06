package join;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class SortMergeJoin {

	public static byte[] freeBlock(byte[] blk, int index) {
		for (int i = index; i < index + 64; i++) {
			blk[i] = 0;
		}
		return blk;
	}

	public static void main(String[] args) {

		int indexR; // 缓冲区块偏移首地址
		int indexS; // 缓冲区块偏移首地址
		int indexOut; // 缓冲区输出块偏移首地址
		int addrOut = 1000; // 输出到的磁盘块地址

		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();

		indexOut = buf.getNewBlockInBuffer();
		int tempIndexOut = indexOut;
		int count = 0;

		int i = 1;
		int j = 15;
		/* Read the block from the hard disk */
		indexR = buf.readBlockFromDisk("src/disk/SortedFinalData/" + i);
		/* Read the block from the hard disk */
		indexS = buf.readBlockFromDisk("src/disk/SortedFinalData/" + j);
		int kR = indexR;
		int kS = indexS;
		int EOFR = indexR + 64;// 磁盘块结束位置
		int EOFS = indexS + 64;// 磁盘块结束位置
		int flag = 0;
		
		while (i < 15 && j < 43) {
			while (kR < EOFR && kS < EOFS) {
				int R_A = Func.byteToInt(blk, kR);
				int R_B = Func.byteToInt(blk, kR+4);
				int S_C = Func.byteToInt(blk, kS);
				int S_D = Func.byteToInt(blk, kS+4);
				if(R_A > S_C) {
					kS+=8;
					S_C = Func.byteToInt(blk, kS);
					S_D = Func.byteToInt(blk, kS+4);
				}else if(R_A < S_C) {
					kR+=8;
					R_A = Func.byteToInt(blk, kR);
					R_B = Func.byteToInt(blk, kR+4);
				}else {
					count++;
					// 将目标元组写入缓存
					blk = Func.intToByte(blk, R_A, tempIndexOut);
					tempIndexOut += 4;
					blk = Func.intToByte(blk, R_B, tempIndexOut);
					tempIndexOut += 4;
					blk = Func.intToByte(blk, S_D, tempIndexOut);
					tempIndexOut += 4;
					if (count == 5) {
						buf.writeBlockToDisk(indexOut, "src/disk/SortMergeJoin/" + addrOut);
						indexOut = buf.getNewBlockInBuffer();
						tempIndexOut = indexOut;
						blk = freeBlock(blk, indexOut);
						addrOut++;
						count = 0;
					}
					
					int kSTemp = kS+8;
					int S_CTemp = Func.byteToInt(blk, kSTemp);
					int S_DTemp = Func.byteToInt(blk, kSTemp+4);
					int addrSTemp = 196;
					int jTemp = j;
					int EOFSTemp = addrSTemp + 64;
					if(kSTemp >= EOFS) {
						flag = 1;
						blk = freeBlock(blk, addrSTemp);
						buf.freeBlockInBuffer(addrSTemp);
						jTemp++;
						/* Read the block from the hard disk */
						int indexSTemp = buf.readBlockFromDisk("src/disk/SortedFinalData/" + jTemp);
						kSTemp = indexSTemp;
						S_CTemp = Func.byteToInt(blk, kSTemp);
						S_DTemp = Func.byteToInt(blk, kSTemp+4);
					}
					while(jTemp<43 && R_A == S_CTemp) {
						count++;
						// 将目标元组写入缓存
						blk = Func.intToByte(blk, R_A, tempIndexOut);
						tempIndexOut += 4;
						blk = Func.intToByte(blk, R_B, tempIndexOut);
						tempIndexOut += 4;
						blk = Func.intToByte(blk, S_DTemp, tempIndexOut);
						tempIndexOut += 4;
						if (count == 5) {
							buf.writeBlockToDisk(indexOut, "src/disk/SortMergeJoin/" + addrOut);
							indexOut = buf.getNewBlockInBuffer();
							tempIndexOut = indexOut;
							blk = freeBlock(blk, indexOut);
							addrOut++;
							count = 0;
						}
						kSTemp+=8;
						S_CTemp = Func.byteToInt(blk, kSTemp);
						S_DTemp = Func.byteToInt(blk, kSTemp+4);
						
						if(kSTemp >= EOFS && flag == 0) {
							flag = 1;
							blk = freeBlock(blk, addrSTemp);
							buf.freeBlockInBuffer(addrSTemp);
							jTemp++;
							/* Read the block from the hard disk */
							int indexSTemp = buf.readBlockFromDisk("src/disk/SortedFinalData/" + jTemp);
							kSTemp = indexSTemp;
							S_CTemp = Func.byteToInt(blk, kSTemp);
							S_DTemp = Func.byteToInt(blk, kSTemp+4);
						}
						if (kSTemp >= EOFSTemp) {
							blk = freeBlock(blk, addrSTemp);
							buf.freeBlockInBuffer(addrSTemp);
							jTemp++;
							/* Read the block from the hard disk */
							int indexSTemp = buf.readBlockFromDisk("src/disk/SortedFinalData/" + jTemp);
							kSTemp = indexSTemp;
							S_CTemp = Func.byteToInt(blk, kSTemp);
							S_DTemp = Func.byteToInt(blk, kSTemp+4);
						}
					}
					flag = 0;
					
					int kRTemp = kR + 8;
					int R_ATemp = Func.byteToInt(blk, kRTemp);
					int R_BTemp = Func.byteToInt(blk, kRTemp+4);
					int addrRTemp = 196;
					int iTemp = i;
					int EOFRTemp = addrRTemp + 64;
					if (kRTemp >= EOFR) {
						flag = 1;
						blk = freeBlock(blk, addrRTemp);
						buf.freeBlockInBuffer(addrRTemp);
						iTemp++;
						/* Read the block from the hard disk */
						int indexRTemp = buf.readBlockFromDisk("src/disk/SortedFinalData/" + iTemp);
						kRTemp = indexRTemp;
						R_ATemp = Func.byteToInt(blk, kRTemp);
						R_BTemp = Func.byteToInt(blk, kRTemp+4);
					}
					while(iTemp<15 && R_ATemp == S_C) {
						count++;
						// 将目标元组写入缓存
						blk = Func.intToByte(blk, R_ATemp, tempIndexOut);
						tempIndexOut += 4;
						blk = Func.intToByte(blk, R_BTemp, tempIndexOut);
						tempIndexOut += 4;
						blk = Func.intToByte(blk, S_D, tempIndexOut);
						tempIndexOut += 4;
						if (count == 5) {
							buf.writeBlockToDisk(indexOut, "src/disk/SortMergeJoin/" + addrOut);
							indexOut = buf.getNewBlockInBuffer();
							tempIndexOut = indexOut;
							blk = freeBlock(blk, indexOut);
							addrOut++;
							count = 0;
						}
						kRTemp+=8;
						R_ATemp = Func.byteToInt(blk, kRTemp);
						R_BTemp = Func.byteToInt(blk, kRTemp+4);
						
						if(kRTemp >= EOFR && flag == 0) {
							flag = 1;
							blk = freeBlock(blk, addrRTemp);
							buf.freeBlockInBuffer(addrRTemp);
							iTemp++;
							/* Read the block from the hard disk */
							int indexRTemp = buf.readBlockFromDisk("src/disk/SortedFinalData/" + iTemp);
							kRTemp = indexRTemp;
							R_ATemp = Func.byteToInt(blk, kRTemp);
							R_BTemp = Func.byteToInt(blk, kRTemp+4);
						}
						if (kRTemp >= EOFRTemp) {
							blk = freeBlock(blk, addrRTemp);
							buf.freeBlockInBuffer(addrRTemp);
							iTemp++;
							/* Read the block from the hard disk */
							int indexRTemp = buf.readBlockFromDisk("src/disk/SortedFinalData/" + iTemp);
							kRTemp = indexRTemp;
							R_ATemp = Func.byteToInt(blk, kRTemp);
							R_BTemp = Func.byteToInt(blk, kRTemp+4);
						}
					}
					flag = 0;
					
					kS+=8;
					kR+=8;
				}
			}
			if(kR >= EOFR) {
				buf.freeBlockInBuffer(indexR);
				i++;
				/* Read the block from the hard disk */
				indexR = buf.readBlockFromDisk("src/disk/SortedFinalData/" + i);
				kR = indexR;
			}
			if (kS >= EOFS) {
				buf.freeBlockInBuffer(indexS);
				j++;
				/* Read the block from the hard disk */
				indexS = buf.readBlockFromDisk("src/disk/SortedFinalData/" + j);
				kS = indexS;
			}
		}
		if (count != 0) {
			buf.writeBlockToDisk(indexOut, "src/disk/SortMergeJoin/" + addrOut);
		}
		//Display.show("src/disk/SortedFinalData");
		Display.showJoin("src/disk/SortMergeJoin");
		//Display.showJoin("src/disk/NestLoopJoin");
	}

}
