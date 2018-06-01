

import java.util.Arrays;
import java.util.Scanner;

/*
 * 图同构的矩阵初等变换算法判定
 * @author skyward
 */
public class TongGou {
	// 图的顶点个数
	static int n;
	// 是否继续进行同构判定的标志
	static boolean flag = true;
	// 标准输入
	static Scanner scanner = new Scanner(System.in);
	// 图1的邻接矩阵
	static int linjie1[][];
	// 图1的0-1同化矩阵
	static int tonghua1[][];
	// 图1的行码距异或矩阵
	static int yihuo1[][];
	// 图1的行码距同或矩阵
	static int tonghuo1[][];
	// 图2的邻接矩阵
	static int linjie2[][];
	// 图2的0-1同化矩阵
	static int tonghua2[][];
	// 图2的行码距异或矩阵
	static int yihuo2[][];
	// 图2的行码距同或矩阵
	static int tonghuo2[][];

	public static void main(String[] args) {

		System.out.println("开始进行同构判定");
		while (flag) {
			System.out.println("请输入图的顶点个数：");
			n = scanner.nextInt();
			init();
			judge();
			System.out.println("是否继续进行同构判断(true or false):");
			flag = scanner.nextBoolean();
		}
		System.out.println("程序结束");
	}

	/*
	 * 判断两图是否同构
	 */
	public static void judge() {
		int count = 0;
		int lj1[] = new int[n];
		int th1[] = new int[n];
		int yh1[] = new int[n];
		int lj2[] = new int[n];
		int th2[] = new int[n];
		int yh2[] = new int[n];
		/*
		 * 先进行 行码距异或矩阵配对 若配对,再进行邻接矩阵和行码距同或矩阵配对;若均匹配,则根据根据配对行,进行对应行列的行列初等变换
		 * 否则,不同构
		 */
		for (int i = 0; i < n; i++) {
			for (int a = 0; a < n; a++) {
				yh1[a] = yihuo1[i][a];
				th1[a] = tonghuo1[i][a];
				lj1[a] = linjie1[i][a];
			}
			for (int j = i; j < n; j++) {
				for (int z = 0; z < n; z++) {
					yh2[z] = yihuo2[j][z];
					th2[z] = tonghuo2[j][z];
					lj2[z] = linjie2[j][z];
				}
				if (compare(yh1, yh2)) {
					if (compare(th1, th2) && compare(lj1, lj2)) {
						swaprow(tonghuo2, i, j);
						swapcol(tonghuo2, i, j);
						swaprow(linjie2, i, j);
						swapcol(linjie2, i, j);
						swaprow(yihuo2, i, j);
						swapcol(yihuo2, i, j);
						count++;
						break;
					} else {
						System.out.println("不同构");
						return;
					}
				} else if (j == n - 1) {
					System.out.println("不同构");
					return;
				}
			}
		}
		if (count == n) {
			System.out.println("同构");
		} else {
			System.out.println(count);
			System.out.println("不同构");
		}
	}

	/*
	 * 比较行元素是否一样
	 * 
	 * @param arr1,arr2 进行比较的行
	 */
	public static boolean compare(int arr1[], int arr2[]) {
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		for (int i = 0; i < n; i++) {
			if (arr1[i] != arr2[i])
				return false;
		}
		return true;
	}

	/*
	 * 初等行交换
	 * 
	 * @param arr1 要进行交换的矩阵
	 * 
	 * @param x,y 进行交换的行
	 */
	public static void swapcol(int arr1[][], int x, int y) {
		int temp[] = new int[n];
		for (int i = 0; i < n; i++) {
			temp[i] = arr1[i][x];
			arr1[i][x] = arr1[i][y];
			arr1[i][y] = temp[i];
		}
	}

	/*
	 * 初等列交换
	 * 
	 * @param arr1 要进行交换的矩阵
	 * 
	 * @param x,y 进行交换的列
	 */
	public static void swaprow(int arr1[][], int x, int y) {
		int temp[] = new int[n];
		for (int i = 0; i < n; i++) {
			temp[i] = arr1[x][i];
			arr1[x][i] = arr1[y][i];
			arr1[y][i] = temp[i];
		}
	}

	// 接收用户输入,初始化邻接矩阵
	public static void input(int arr[][]) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				arr[i][j] = scanner.nextInt();
			}
		}
	}

	/*
	 * 将邻接矩阵转换成0-1同化矩阵 n*n
	 * 
	 * @param lj 邻接矩阵
	 * 
	 * @param th 0-1同化矩阵
	 */
	public static void lj2th(int lj[][], int th[][]) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (lj[i][j] > 0) {
					th[i][j] = 1;
				} else {
					th[i][j] = 0;
				}
			}
		}
	}

	/*
	 * 由0-1同化矩阵求出行码距同或矩阵 行码距同或矩阵的定义:第i行与第j行对应列上取相同值的诸列上各元素之和
	 * 
	 * @param lj 邻接矩阵
	 * 
	 * @param th 0-1同化矩阵
	 * 
	 * @param thuo 行码距同或矩阵
	 */
	public static void th2th(int lj[][], int th[][], int thuo[][]) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					for (int k = 0; k < n; k++) {
						thuo[i][j] += lj[i][k];
					}
					thuo[i][j] *= 2;
				} else {
					for (int k = 0; k < n; k++) {
						if (th[i][k] == th[j][k]) {
							thuo[i][j] += lj[i][k];
							thuo[i][j] += lj[j][k];
						}
					}
				}
			}
		}
	}

	/*
	 * 由0-1同化矩阵求出行码距异或矩阵
	 * 
	 * 行码距异或矩阵的定义: 第i行与第j行对应列上取不同值(即一个取0,另一个取1或k)的诸列上各元素之和
	 * 
	 * @param lj 邻接矩阵
	 * 
	 * @param th 0-1同化矩阵 最好：3*n^3 最差：2*n^2
	 * 
	 * @param yh 行码距异或矩阵
	 */
	public static void th2yh(int lj[][], int th[][], int yh[][]) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					yh[i][j] = lj[i][j];
				} else {
					for (int k = 0; k < n; k++) {
						if (th[i][k] != th[j][k]) {
							yh[i][j] += lj[i][k];
							yh[i][j] += lj[j][k];
						}
					}
				}
			}
		}
	}

	// 初始化
	public static void init() {
		linjie1 = new int[n][n];
		tonghua1 = new int[n][n];
		yihuo1 = new int[n][n];
		tonghuo1 = new int[n][n];
		linjie2 = new int[n][n];
		tonghua2 = new int[n][n];
		yihuo2 = new int[n][n];
		tonghuo2 = new int[n][n];
		System.out.println("请输入图1的邻接矩阵:");
		input(linjie1);
		System.out.println("请输入图2的邻接矩阵:");
		input(linjie2);
		lj2th(linjie1, tonghua1);
		th2yh(linjie1, tonghua1, yihuo1);
		th2th(linjie1, tonghua1, tonghuo1);
		lj2th(linjie2, tonghua2);
		th2yh(linjie2, tonghua2, yihuo2);
		th2th(linjie2, tonghua2, tonghuo2);
	}

}
