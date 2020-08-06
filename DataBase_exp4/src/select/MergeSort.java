package select;

import disk.Display;
import disk.Func;
import extmem.Buffer;

public class MergeSort {

	public static byte[] mergeSort(byte[] data, int length) {
		if (length <= 0)
			return null;

		// steps为步长，将data[]分组，每组steps个，然后两两merge
		// steps < length能保证最后肯定被分成两组
		for (int steps = 1; steps < length; steps *= 2) {
			// 假设merge的两个分组为[left_min,right_min),[right_min,right_max)
			int left_min, right_min, right_max;
			int temp;
			// length - left_min为还没有merge的元素个数，它们应该至少能分两组，才继续进行么merge
			for (left_min = 0; steps < length - left_min; left_min = right_max) {
				right_min = left_min + steps;
				right_max = right_min + steps;
				if (right_max > length)
					right_max = length;

				// merge
				while (left_min < right_min && right_min < right_max) {
					while (left_min < right_min
							&& getIndexIntArray(data, left_min * 8) <= getIndexIntArray(data, right_min * 8))
						left_min++;
					if (left_min == right_min)
						break;

					temp = right_min;
					while (right_min < right_max
							&& getIndexIntArray(data, right_min * 8) < getIndexIntArray(data, left_min * 8))
						right_min++;

					data = RingShift(data, left_min, temp, right_min);

					left_min += right_min - temp;
				}
			}
		}
		return data;
	}

	// 交换字节数组中第a个元组和第b个元组
	public static byte[] swap(byte[] data, int a, int b) {
		for (int i = 0; i < 8; i++) {
			byte temp = data[a * 8 + i];
			data[a * 8 + i] = data[b * 8 + i];
			data[b * 8 + i] = temp;
		}
		return data;
	}

	// 反转data[first,last]
	public static byte[] reverse(byte[] data, int first, int last) {
		if (data.length == 0)
			return null;
		while (first < last) {
			int a = first++;
			int b = last--;
			data = swap(data, a, b);
		}
		return data;
	}

	// data[begin,mid)和data[mid,end)两部分交换位置
	public static byte[] RingShift(byte[] data, int begin, int mid, int end) {
		if (data.length == 0)
			return null;
		data = reverse(data, begin, mid - 1);
		data = reverse(data, mid, end - 1);
		data = reverse(data, begin, end - 1);
		return data;
	}

	// 获取字节数组中第index元组的第一个属性值（R_A或S_C）
	public static int getIndexIntArray(byte[] data, int index) {
		return Func.byteToInt(data, index);
	}

	public static byte[] cover1(byte[] blk,int numBlock, int byteBlock) {
		for(int k = 0 ;k<numBlock;k++) {
			for(int j = 0;j<byteBlock;j++) {
				blk[k*(byteBlock-8)+j] = blk[k*(byteBlock+1)+j+1];	
			}
		}
		return blk;
	}
	
	public static byte[] cover2(byte[] blk,int numBlock, int byteBlock) {
		for(int k = 0 ;k<numBlock;k++) {
			for(int j = 0;j<byteBlock;j++) {
				blk[k*(byteBlock)+j] = blk[k*(byteBlock+1)+j+1];	
			}
		}
		return blk;
	}
	
	public static byte[] insert(byte[] blk,int numBlock, int byteBlock) {
		for(int k = 0 ;k<numBlock;k++) {
			for(int j = 0 ;j <byteBlock;j++) {
				blk[(byteBlock*(numBlock-k)-j-1)+(numBlock-k)] = blk[byteBlock*(numBlock-k)-j-1];
			}
			blk[(byteBlock*(numBlock-k)-byteBlock-1)+(numBlock-k)] = 1;
		}	
		return blk;
	}
	
	public static void firstStepR() {
		int index; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int addrOut = 1;// 输出地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();

		for (int i = 0; i < 16; i++) {
			addr = i + 1;
			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/originData/" + addr);
			
			if (index == -1||i==15) { // 缓冲区已经满了
				blk = cover1(blk, 8, 64);
				blk = mergeSort(blk, 56);
				//System.out.println("---------------");
				for(int k = 0; k < 448; k = k + 8) {
					int R_A = Func.byteToInt(blk, k);
					int R_B = Func.byteToInt(blk, k + 4);
					//System.out.println(R_A + "\t" + R_B);
				}
				
				int temp = 1;
				blk = insert(blk, 7, 64);	
				for(int k = 0 ;k < 7;k++) {
					buf.writeBlockToDisk(temp, "src/disk/SortedData/"+addrOut);
					temp += 65;
					addrOut++;
				}
				buf.freeBuffer();
				/* Initialize the buffer */
				buf = new Buffer(520, 64);
				blk = buf.getBufferData();
				if(i==15) break;
				else i--;
			}		
		}
		//System.out.println("============");
		//Display.show("src/disk/SortedData");
	}
	
	public static void secondStepR() {
		int index = 0; // 缓冲区块偏移首地址
		int addr = 0; // 磁盘块地址
		int addrOut = 1;// 输出地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();
		
		int i = 1;
		int j = i+3;
		while(true) {
			while(i<=j) { //R两路归并，各放4块；S4路归并，各放2块
				addr = i;
				//System.out.println(i);
				//System.out.println(addr);
				/* Read the block from the hard disk */
				index = buf.readBlockFromDisk("src/disk/SortedData/" + addr);
				index = buf.readBlockFromDisk("src/disk/SortedData/" + (addr+7));
				i++;
			}
			blk = cover2(blk, 8, 64);
			blk = mergeSort(blk, 64);
			
			if(i==8) {
				int temp = 1;
				blk = insert(blk, 8, 64);
				for(int k = 0 ;k < 8;k++) {
					buf.writeBlockToDisk(temp, "src/disk/SortedFinalData/"+addrOut);
					temp += 65;
					addrOut++;
				}
				break;
			}else if(i==addr+1) {
				int temp = 1;
				blk = insert(blk, 8, 64);	
				for(int k = 0 ;k < 2;k++) { //R取出前2块，S取出前2块
					buf.writeBlockToDisk(temp, "src/disk/SortedFinalData/"+addrOut);
					temp += 65;
					addrOut++;
				} 
				j = i;
			}
		}	
		//Display.show("src/disk/SortedData");
		//Display.show("src/disk/SortedFinalData");	
	}
	
	public static void firstStepS() {
		int index; // 缓冲区块偏移首地址
		int addr; // 磁盘块地址
		int addrOut = 15;// 输出地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();

		for (int i = 16; i < 49; i++) {
			addr = i + 1;
			/* Read the block from the hard disk */
			index = buf.readBlockFromDisk("src/disk/originData/" + addr);
			
			if (index == -1||i==48) { // 缓冲区已经满了
				blk = cover1(blk, 8, 64);
				blk = mergeSort(blk, 56);
				//System.out.println("---------------");
				for(int k = 0; k < 448; k = k + 8) {
					int R_A = Func.byteToInt(blk, k);
					int R_B = Func.byteToInt(blk, k + 4);
					//System.out.println(R_A + "\t" + R_B);
				}
				
				int temp = 1;
				blk = insert(blk, 7, 64);	
				for(int k = 0 ;k < 7;k++) {
					buf.writeBlockToDisk(temp, "src/disk/SortedData/"+addrOut);
					temp += 65;
					addrOut++;
				}
				buf.freeBuffer();
				/* Initialize the buffer */
				buf = new Buffer(520, 64);
				blk = buf.getBufferData();
				if(i==48) break;
				else i--;
			}		
		}
		//System.out.println("============");
		//Display.show("src/disk/SortedData");
	}
	
	public static void secondStepS1() { 
		//首先归并15-21和22-28，形成1-14；在归并29-35和36到42，形成15-28。最后归并1-14和15-28,形成15-42
		int index = 0; // 缓冲区块偏移首地址
		int addr = 0; // 磁盘块地址
		int addrOut = 1;// 输出地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();
		
		int i = 15;
		int j = i+3;
		while(true) {
			while(i<=j) { //R两路归并，各放4块；S4路归并，各放2块
				addr = i;
				//System.out.println(i);
				//System.out.println(addr);
				/* Read the block from the hard disk */
				index = buf.readBlockFromDisk("src/disk/SortedData/" + addr);
				index = buf.readBlockFromDisk("src/disk/SortedData/" + (addr+7));
				i++;
			}
			blk = cover2(blk, 8, 64);
			blk = mergeSort(blk, 64);
			
			if(i==22) {
				int temp = 1;
				blk = insert(blk, 8, 64);
				for(int k = 0 ;k < 8;k++) {
					buf.writeBlockToDisk(temp, "src/disk/SortedFinal1Data/"+addrOut);
					temp += 65;
					addrOut++;
				}
				break;
			}else if(i==addr+1) {
				int temp = 1;
				blk = insert(blk, 8, 64);	
				for(int k = 0 ;k < 2;k++) { //R取出前2块，S取出前2块
					buf.writeBlockToDisk(temp, "src/disk/SortedFinal1Data/"+addrOut);
					temp += 65;
					addrOut++;
				} 
				j = i;
			}
		}	
		//Display.show("src/disk/SortedFinal1Data");	
	}
	
	public static void secondStepS2() { //首先归并15-21和22-28，形成1-14；在归并29-35和36到42，形成15-28。最后归并1-14和15-28,形成15-42
		int index = 0; // 缓冲区块偏移首地址
		int addr = 0; // 磁盘块地址
		int addrOut = 15;// 输出地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();
		
		int i = 29;
		int j = i+3;
		while(true) {
			while(i<=j) { //R两路归并，各放4块；S4路归并，各放2块
				addr = i;
				//System.out.println(i);
				//System.out.println(addr);
				/* Read the block from the hard disk */
				index = buf.readBlockFromDisk("src/disk/SortedData/" + addr);
				index = buf.readBlockFromDisk("src/disk/SortedData/" + (addr+7));
				i++;
			}
			blk = cover2(blk, 8, 64);
			blk = mergeSort(blk, 64);
			
			if(i==36) {
				int temp = 1;
				blk = insert(blk, 8, 64);
				for(int k = 0 ;k < 8;k++) {
					buf.writeBlockToDisk(temp, "src/disk/SortedFinal1Data/"+addrOut);
					temp += 65;
					addrOut++;
				}
				break;
			}else if(i==addr+1) {
				int temp = 1;
				blk = insert(blk, 8, 64);	
				for(int k = 0 ;k < 2;k++) { //R取出前2块，S取出前2块
					buf.writeBlockToDisk(temp, "src/disk/SortedFinal1Data/"+addrOut);
					temp += 65;
					addrOut++;
				} 
				j = i;
			}
		}	
		//Display.show("src/disk/SortedFinal1Data");	
	}
	
	public static void secondStepS3() { 
		//首先归并15-21和22-28，形成1-14；在归并29-35和36到42，形成15-28。最后归并1-14和15-28,形成15-42
		int index = 0; // 缓冲区块偏移首地址
		int addr = 0; // 磁盘块地址
		int addrOut = 15;// 输出地址
		
		/* Initialize the buffer */
		Buffer buf = new Buffer(520, 64);
		byte[] blk = buf.getBufferData();
		
		int i = 1;
		int j = i+3;
		while(true) {
			while(i<=j) { //R两路归并，各放4块；S4路归并，各放2块
				addr = i;
				//System.out.println(i);
				//System.out.println(addr);
				/* Read the block from the hard disk */
				index = buf.readBlockFromDisk("src/disk/SortedFinal1Data/" + addr);
				index = buf.readBlockFromDisk("src/disk/SortedFinal1Data/" + (addr+14));
				i++;
			}
			blk = cover2(blk, 8, 64);
			blk = mergeSort(blk, 64);
			
			if(i==15) {
				int temp = 1;
				blk = insert(blk, 8, 64);
				for(int k = 0 ;k < 8;k++) {
					buf.writeBlockToDisk(temp, "src/disk/SortedFinalData/"+addrOut);
					temp += 65;
					addrOut++;
				}
				break;
			}else if(i==addr+1) {
				int temp = 1;
				blk = insert(blk, 8, 64);	
				for(int k = 0 ;k < 2;k++) { //R取出前2块，S取出前2块
					buf.writeBlockToDisk(temp, "src/disk/SortedFinalData/"+addrOut);
					temp += 65;
					addrOut++;
				} 
				j = i;
			}
		}	
		//Display.show("src/disk/SortedFinalData");	
	}
	
	public static void main(String[] args) {
		firstStepR();
		secondStepR();
		firstStepS();
		secondStepS1();
		secondStepS2();
		secondStepS3();
		Display.show("src/disk/SortedFinalData");	
	}
}
