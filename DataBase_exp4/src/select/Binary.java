package select;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class Binary {

	public static byte[] freeBlock(byte[] blk,int index) {
		for(int i = index ;i<index+64;i++) {
			blk[i] = 0;
		}
		return blk;
	}

	public static void main(String[] args) {

		//Display.show("src/disk/SortedFinalData");
		int index; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int indexOut; // 缓冲区输出块偏移首地址
		int addrOut = 1002; // 输出到的磁盘块地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();
		
		indexOut = buf.getNewBlockInBuffer();
		int tempIndexOut = indexOut;
		int count = 0;

		int low = 1;
		int high = 14;
		int flag = 0;
		while(low <= high) {
			int mid = (low + high) /2;

			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/SortedFinalData/"+mid);
			int firstS_C = Func.byteToInt(blk, index);
			int lastS_C = Func.byteToInt(blk, index+56);
			if(firstS_C > 40) {
				high = mid - 1;
			}else if(lastS_C < 40) {
				low = mid + 1;
			}else if(firstS_C <= 40 && lastS_C >= 40){
				for(int k = index ; k< index + 64;k=k+8) {
					int S_C = Func.byteToInt(blk, k);
					int S_D = Func.byteToInt(blk, k+4);
					if(S_C==40) {
						if(flag == 1) flag =2 ;
						count ++;
						//将目标元组写入缓存
						blk = Func.intToByte(blk, S_C, tempIndexOut);
						tempIndexOut += 4;
						blk = Func.intToByte(blk, S_D, tempIndexOut);
						tempIndexOut += 4;	
						
						if(count == 8) {
							buf.writeBlockToDisk(indexOut, "src/disk/binarySelect/"+addrOut);
							indexOut = buf.getNewBlockInBuffer();
							tempIndexOut = indexOut;
							blk = freeBlock(blk, indexOut);
							addrOut ++;
							count = 0;						
						}
					}		
				}
				
				if(firstS_C == 40) {
					flag = 1;
					low--;
					high--;
				}else if(lastS_C == 40) {
					flag = 1;
					low++;
					high++;
				}
			}
			if(flag == 2 || low > high) {
				buf.writeBlockToDisk(indexOut, "src/disk/binarySelect/"+addrOut);
				break;
			}
			buf.freeBlockInBuffer(index);
		}	
		Display.show("src/disk/binarySelect");
	}
}
