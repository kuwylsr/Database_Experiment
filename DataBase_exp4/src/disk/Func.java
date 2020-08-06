package disk;

public class Func {

	public static byte[] intToByte(byte[] blk, int b,int index) {
		for(int j = 0 ;j < 4 ;j ++) {
			int offset = (3-j)*8;
			blk[index+j] = (byte)((b >> offset) & 0xFF);
		}
		return blk;
	}
	
	public static int byteToInt(byte[] blk , int off) {
		int b0 = blk[off] & 0xFF;
		int b1 = blk[off + 1] & 0xFF;
		int b2 = blk[off + 2] & 0xFF;
		int b3 = blk[off + 3] & 0xFF;
		return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
	}
}
