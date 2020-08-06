package disk;

import extmem.Buffer;

public class InitData {
	
	public static void RandomData() {
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();;
		int index;
		int addr = 1;
		int randomData1;
		int randomData2;
	
		//R关系一共占16个磁盘块
		for(int i = 0;i<16;i++) {
			/* Get a new block in the buffer */
			index = buf.getNewBlockInBuffer();
			
			/* Fill data into the block */
			int tempIndex = index;
			for (int j = 0; j < 7; j++) { //一个块放7个元组，每个元组8字节（两个int数）
				/* Random data */
				randomData1 = (int)(1+Math.random()*40);
				randomData2 = (int)(1+Math.random()*1000);
				blk = Func.intToByte(blk, randomData1, tempIndex);
				tempIndex += 4;
				blk = Func.intToByte(blk, randomData2, tempIndex);
				tempIndex += 4;
			}
			blk = Func.intToByte(blk, addr+1, 60+1);//磁盘块后4个字节存放Bi+1的地址
			
			/* Write the block to the hard disk */
			if (buf.writeBlockToDisk(index, "src/disk/originData/"+addr) != 0) {
				System.err.println("Writing Block Failed!\n");
				System.exit(1);
			}
			addr++;
			for(int k = index ; k< index+64 ;k=k+8) {
				int temp1 = Func.byteToInt(blk, k);
				int temp2 = Func.byteToInt(blk, k+4);
				System.out.println(temp1+"\t"+temp2);
			}
		}
		System.out.println("=========================================");
		//S关系一共占32个磁盘块
		for(int i = 0;i<32;i++) {
			/* Get a new block in the buffer */
			index = buf.getNewBlockInBuffer();
			
			/* Fill data into the block */
			int tempIndex = index;
			for (int j = 0; j < 7; j++) { //一个块放7个元组，每个元组8字节（两个int数）
				/* Random data */
				randomData1 = (int)(20+Math.random()*41);
				randomData2 = (int)(1+Math.random()*1000);
				blk = Func.intToByte(blk, randomData1, tempIndex);
				tempIndex += 4;
				blk = Func.intToByte(blk, randomData2, tempIndex);
				tempIndex += 4;
			}
			blk = Func.intToByte(blk, addr+1, 60+1);//磁盘块后4个字节存放Bi+1的地址
			
			/* Write the block to the hard disk */
			if (buf.writeBlockToDisk(index, "src/disk/originData/"+addr) != 0) {
				System.err.println("Writing Block Failed!\n");
				System.exit(1);
			}
			addr++;
			for(int k = index ; k< index+64 ;k=k+8) {
				int temp1 = Func.byteToInt(blk, k);
				int temp2 = Func.byteToInt(blk, k+4);
				System.out.println(temp1+"\t"+temp2);
			}
		}
	}
	public static void main(String[] args) {
		RandomData();
	}
}
