package extmem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Buffer {

	static byte BLOCK_AVAILABLE = 0;
	static byte BLOCK_UNAVAILABLE = 1;

	private long numIO; // 外存I/O次数
	private int thisSize; // 缓冲区大小（单位：字节）
	private int blkSize; // 块的大小（单位：字节）
	private int numAllBlk; // 缓冲区内可存放的最多块数
	private int numFreeBlk; // 缓冲区内可用的最多块数
	private byte[] data; // 缓冲区内存区域

	/**
	 * 用特定的缓冲区大小以及块大小来初始化缓冲区（Buffer的构造函数）
	 * 
	 * @param thisSize 缓冲区大小（单位：字节）
	 * @param blkSize  块大小（单位：字节）
	 * @return 若初始化成功返回this对象；否则返回NULL
	 */
	public Buffer(int thisSize, int blkSize) {
		this.numIO = 0;
		this.thisSize = thisSize;
		this.blkSize = blkSize;
		// 缓冲区中的每个磁盘块前都有一个1个byte的标识（判断磁盘块是否空闲）
		this.numAllBlk = thisSize / (blkSize + 1);
		this.numFreeBlk = this.numAllBlk;
		this.data = new byte[thisSize * 1];
	}

	/**
	 * 释放缓冲区this占用的内存空间
	 * 
	 * @param this 缓冲区对象
	 */
	public void freeBuffer(Buffer this) {
		this.data = null;
	}

	/**
	 * 在缓冲区this中申请一个新的块
	 * 
	 * @return 若申请成功返回块的起始地址；否则返回-1
	 */
	public int getNewBlockInBuffer() {
		byte[] blkPtr;

		if (this.numFreeBlk == 0) {
			System.err.println("Buffer is full!\n");
			return -1;
		}

		blkPtr = this.data;

		int i = 0;
		while (i < this.numAllBlk * (this.thisSize + 1)) {
			if (blkPtr[i] == BLOCK_AVAILABLE) {
				break;
			} else {
				i = i + (this.blkSize + 1);// 有一个标识占1byte
			}
		}

		blkPtr[i] = BLOCK_UNAVAILABLE;
		this.numFreeBlk--;
		return i + 1; // 磁盘块首地址（不包含标识）
	}

	/**
	 * 解除块blk对缓冲区的内存占用，即将blk占据的内存区域标记为可用
	 * 
	 * @param i 缓冲区中的块的首地址
	 * @param   this 缓冲区
	 */
	public void freeBlockInBuffer(int i) {
		this.data[i - 1] = BLOCK_AVAILABLE; // 设置块的标识
		this.numFreeBlk++;
	}

	/**
	 * 从磁盘上删除地址为addr的磁盘块内的数据
	 * 
	 * @param addr 所要删除的磁盘块的地址
	 * @return 若删除成功返回0；否则返回-1
	 */
	public int dropBlockOnDisk(int addr) {
		String filename = String.valueOf(addr);
		File file = new File(filename);

		if (!file.delete()) {
			System.err.println("Dropping Block Fails!\n");
			return -1;
		}
		return 0;
	}

	/**
	 * 将磁盘块上地址为addr的磁盘块读入缓冲区this
	 * 
	 * @param addr 磁盘块地址
	 * @param      this 缓冲区
	 * @return 若读取成功返回缓冲区内该块的地址；否则返回NULL。同时，缓冲区this的I/O次数+1
	 */
	public int readBlockFromDisk(String addr) {

		byte[] blkPtr;
		if (this.numFreeBlk == 0) {
			System.err.println("Buffer Overflows!\n");
			return -1;
		}

		blkPtr = this.data;

		int i = 0;
		while (i < this.numAllBlk * (this.thisSize + 1)) {
			if (blkPtr[i] == BLOCK_AVAILABLE) {
				break;
			} else {
				i = i + (this.blkSize + 1);// 有一个标识占1byte
			}
		}

		String filename = addr;
		FileInputStream is;
		try {
			is = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			//System.err.println("Reading Block Failed!\n");
			return -1;
		}

		blkPtr[i] = BLOCK_UNAVAILABLE;
		i++;
		int j = i;
		try {
			while (j < i + this.blkSize) { // 读取一个磁盘块的大小
				is.read(blkPtr, j, 1); //
				j++;
			}
			is.close();
			this.numFreeBlk--;
			this.numIO++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 将缓冲区buffer内的块blk写入磁盘上地址为addr的磁盘块
	 * 
	 * @param i    缓冲区内的块的首地址
	 * @param addr 磁盘块地址
	 * @param this 缓冲区
	 * @return 若写入成功返回0；否则返回-1。同时，缓冲区this的I/O次数+1
	 */
	public int writeBlockToDisk(int i, String addr) {
		String filename = addr;
		FileOutputStream os;
		try {
			os = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			System.err.println("Writing Block Failed!\n");
			return -1;
		}

		byte[] blkPtr = this.data;
		int j = i;
		try {
			while (j < i + this.blkSize) { // 读取一个磁盘块的大小
				os.write(blkPtr, j, 1);
				j++;
			}
			os.close();
			blkPtr[i - 1] = BLOCK_AVAILABLE;
			this.numFreeBlk++;
			this.numIO++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long getIO() {
		return this.numIO;
	}

	public byte[] getBufferData() {
		return this.data;
	}

	public int getFree() {
		return this.numFreeBlk;
	}
}
