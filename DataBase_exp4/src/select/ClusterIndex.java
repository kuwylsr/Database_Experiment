package select;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class ClusterIndex {

	public static byte[] freeBlock(byte[] blk, int index) {
		for (int i = index; i < index + 64; i++) {
			blk[i] = 0;
		}
		return blk;
	}

	public static void buildClusterIndex() {
		int index; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int indexOut; // 缓冲区输出块偏移首地址
		int addrOut = 6; // 输出到的磁盘块地址

		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();

		indexOut = buf.getNewBlockInBuffer();
		int tempIndexOut = indexOut;
		int count = 0;
		int preNum = 0; // 物理上，上一个块的最后一个元组的第一个属性值
		for (int i = 14; i < 42; i++) {
			addr = i + 1;
			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/SortedFinalData/" + addr);
			for (int k = index; k < index + 64; k = k + 8) {
				int R_A = Func.byteToInt(blk, k);
				if (R_A != preNum) {
					preNum = R_A;
					count++;
					// 将目标元组写入缓存
					blk = Func.intToByte(blk, R_A, tempIndexOut);
					tempIndexOut += 4;
					blk = Func.intToByte(blk, addr, tempIndexOut);
					tempIndexOut += 4;
					if (count == 8) {
						buf.writeBlockToDisk(indexOut, "src/disk/ClusterIndex/" + addrOut);
						indexOut = buf.getNewBlockInBuffer();
						tempIndexOut = indexOut;
						blk = freeBlock(blk, indexOut);
						addrOut++;
						count = 0;
					}
				}

			}
			buf.freeBlockInBuffer(index);
			preNum = Func.byteToInt(blk, index + 56);
		}
		if (count != 0) {
			buf.writeBlockToDisk(indexOut, "src/disk/ClusterIndex/" + addrOut);
		}
		Display.show("src/disk/ClusterIndex");
	}

	public static void main(String[] args) {
		 //buildClusterIndex();

		int index; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int indexOut; // 缓冲区输出块偏移首地址
		int addrOut = 1001; // 输出到的磁盘块地址

		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();

		indexOut = buf.getNewBlockInBuffer();
		int tempIndexOut = indexOut;
		int count = 0;
		for (int i = 5; i < 11; i++) {
			addr = i + 1;
			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/ClusterIndex/" + addr);
			for (int k = index; k < index + 64; k = k + 8) {
				int R_A = Func.byteToInt(blk, k);
				int indexAddr = Func.byteToInt(blk, k+4);
				if (R_A == 60) {
					int flag = 0;
					/* Read the block from the hard disk */
					int index2 = buf.readBlockFromDisk("src/disk/SortedFinalData/" + indexAddr);
					System.out.println(index2);
					for(int j = index2 ;j < index2+64 ;j=j+8) {
						int R_A1 = Func.byteToInt(blk, j);
						int R_B1 = Func.byteToInt(blk, j+4);
						if(R_A1 == 60) {
							flag = 1;
							count++;
							// 将目标元组写入缓存
							blk = Func.intToByte(blk, R_A1, tempIndexOut);
							tempIndexOut += 4;
							blk = Func.intToByte(blk, R_B1, tempIndexOut);
							tempIndexOut += 4;
						}
						if (count == 8) {
							buf.writeBlockToDisk(indexOut, "src/disk/ClusterSelect/" + addrOut);
							indexOut = buf.getNewBlockInBuffer();
							tempIndexOut = indexOut;
							blk = freeBlock(blk, indexOut);
							addrOut++;
							count = 0;
						}
						if(flag == 1 && R_A1 != 60) break;
						if(j == index2 + 56) {
							buf.freeBlockInBuffer(index2);
							indexAddr++;
							index2 = buf.readBlockFromDisk("src/disk/SortedFinalData/" + indexAddr);
							j = index2-8;
						}
						if(index2 == -1) break;
					}
					break;
				}
			}
			buf.freeBlockInBuffer(index);
		}
		
		if (count != 0) {
			buf.writeBlockToDisk(indexOut, "src/disk/ClusterSelect/" + addrOut);
		}
		//Display.show("src/disk/SortedFinalData");
		Display.show("src/disk/ClusterSelect");
	}
	
}
