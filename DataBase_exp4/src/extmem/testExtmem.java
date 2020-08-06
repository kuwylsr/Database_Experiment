package extmem;

public class testExtmem {

	public static void main(String[] args) {

		int i;// 缓冲区块偏移首地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(20, 8);
		byte[] blk = buf.getBufferData();
		
		/* Get a new block in the buffer */
		i = buf.getNewBlockInBuffer();

		/* Fill data into the block */
		for (int j = 0; j < 8; j++) {
			blk[i+j] = (byte) ('a' + j);
		}
	
		/* Write the block to the hard disk */
		if (buf.writeBlockToDisk(i, "src/extmem/"+31415926) != 0) {
			System.err.println("Writing Block Failed!\n");
			System.exit(1);
		}
		
		/* Read the block from the hard disk */
		if ((i = buf.readBlockFromDisk("src/extmem/"+31415926)) == -1) {
			System.err.println("Reading Block Failed!\n");
			System.exit(1);
		}
		
		/* Process the data in the block */
		for (int j = 0; j < 8; j++) {
			System.out.print((char)blk[i+j]);
		}
		System.out.println();
		/* Check the number of IO's */
		System.out.println("# of IO's is "+buf.getIO());
	}

}
