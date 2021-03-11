package plusMinus;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class PlusMinus {

	// Complete the plusMinus function below.
	static void plusMinus(int[] arr) {
		double divide = (double) arr.length;
		DecimalFormat df = new DecimalFormat("0.00000000");
		double positive = (double) Arrays.stream(arr).filter(fil -> fil > 0).count() / divide;
		double zeros = (double) Arrays.stream(arr).filter(fil -> fil == 0).count() / divide;
		double negative =(double) Arrays.stream(arr).filter(fil -> fil < 0).count() / divide;

		System.out.println(df.format(positive));
		System.out.println(df.format(negative));
		System.out.println(df.format(zeros));
	}

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(Paths.get("src/main/java/plusMinus/data.txt"));
		int n = scanner.nextInt();
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		int[] arr = new int[n];

		String[] arrItems = scanner.nextLine().split(" ");
		scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

		for (int i = 0; i < n; i++) {
			int arrItem = Integer.parseInt(arrItems[i]);
			arr[i] = arrItem;
		}

		plusMinus(arr);

		scanner.close();
	}
}
