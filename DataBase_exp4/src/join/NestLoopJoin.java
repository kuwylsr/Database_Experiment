package join;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class NestLoopJoin {

	public static byte[] freeBlock(byte[] blk, int index) {
		for (int i = index; i < index + 64; i++) {
			blk[i] = 0;
		}
		return blk;
	}

	public static void main(String[] args) {

		int indexR; // 缓冲区块偏移首地址
		int indexS; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int indexOut; // 缓冲区输出块偏移首地址
		int addrOut = 1000; // 输出到的磁盘块地址

		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();

		indexOut = buf.getNewBlockInBuffer();
		int tempIndexOut = indexOut;
		int count = 0;
		int RBlocks = 0;
		for (int i = 0; i < 16; i++) { //遍历每个R，一次放6个，最后一次放4个
			addr = i + 1;
			/* Read the block from the hard disk */
			indexR = buf.readBlockFromDisk("src/disk/originData/" + addr);

			if (indexR == 1 + 65 * 7) {
				RBlocks = 6;
				buf.freeBlockInBuffer(indexR);
				i--;
			} else if (i !=15) {
				continue;
			} else {
				RBlocks = 4;
			}
			for(int j = 16;j<48;j++) {//遍历每个S,一次放一个
				addr = j + 1;
				indexS = buf.readBlockFromDisk("src/disk/originData/" + addr);
				for (int b = 0; b < RBlocks; b++) {
					indexR = 66 + 65*b;
					for (int k = indexR; k < indexR + 56; k = k + 8) {
						int R_A = Func.byteToInt(blk, k);
						int R_B = Func.byteToInt(blk, k + 4);
						for (int s = indexS; s < indexS + 56; s = s + 8) {
							int S_C = Func.byteToInt(blk, s);
							int S_D = Func.byteToInt(blk, s + 4);
							if (R_A == S_C) {
								count++;
								// 将目标元组写入缓存
								blk = Func.intToByte(blk, R_A, tempIndexOut);
								tempIndexOut += 4;
								blk = Func.intToByte(blk, R_B, tempIndexOut);
								tempIndexOut += 4;
								blk = Func.intToByte(blk, S_D, tempIndexOut);
								tempIndexOut += 4;

								if (count == 5) {
									buf.writeBlockToDisk(indexOut, "src/disk/NestLoopJoin/" + addrOut);
									indexOut = buf.getNewBlockInBuffer();
									tempIndexOut = indexOut;
									blk = freeBlock(blk, indexOut);
									addrOut++;
									count = 0;
								}
							}
						}
						
					}
				}
				buf.freeBlockInBuffer(indexS);
			}
			if ((i == 15) && count != 0) {
				buf.writeBlockToDisk(indexOut, "src/disk/NestLoopJoin/" + addrOut);
				break;
			}
			for (int j = 0; j < 6; j++) {
				buf.freeBlockInBuffer(66+65*j);
			}
		}
		//Display.show("src/disk/originData");
		Display.showJoin("src/disk/NestLoopJoin");
	}

}
