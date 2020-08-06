package disk;

import java.io.File;
import java.io.FileInputStream;

public class Display {

	static File dir;
    
	public static int byteToInt(byte[] blk , int off) {
		int b0 = blk[off] & 0xFF;
		int b1 = blk[off + 1] & 0xFF;
		int b2 = blk[off + 2] & 0xFF;
		int b3 = blk[off + 3] & 0xFF;
		return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
	}
	
	public static void showFile(String addr) {
		try {
			FileInputStream is = new FileInputStream(addr);
			byte[] a = new byte[8];
			while((is.read(a, 0, 8) != -1)) {
				int temp1 = byteToInt(a, 0);
				int temp2 = byteToInt(a, 4);
				System.out.println(temp1+"\t"+temp2);
				
				
			}
			System.out.println("---------------------");
			is.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void showJoinFile(String addr) {
		try {
			FileInputStream is = new FileInputStream(addr);
			byte[] a = new byte[12];
			int i = 0 ;
			while((is.read(a, 0, 12) != -1)) {
				i++;
				int temp1 = byteToInt(a, 0);
				int temp2 = byteToInt(a, 4);
				int temp3 = byteToInt(a, 8);
				if(i<6){
					System.out.println(temp1+"\t"+temp2+"\t"+temp3);
				}
				
			}
			System.out.println("---------------------");
			is.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void showRAndS() {
		for(int i = 0;i<16;i++) {
			System.out.println(i+1);
			showFile("src/disk/originData/"+(i+1));
		}
	}
	
	public static void showSelect() {
		dir = new File("src/disk/linearSelect/");
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		for(File f : files) {
			System.out.println(f.getName());
			showFile(f.getAbsolutePath());
		}
	}
	
	 
	
	public static void show(String addr) {
		dir = new File(addr);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		for(File f : files) {
			System.out.println(f.getName());
			showFile(f.getAbsolutePath());
		}
	}
	public static void showJoin(String addr) {
		dir = new File(addr);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		for(File f : files) {
			System.out.println(f.getName());
			showJoinFile(f.getAbsolutePath());
		}
	}
	public static void main(String[] args) {
		Display.show("src/disk/HashBucket");
	}
}
