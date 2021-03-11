package formingMagicSquare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FormingMagicSquare {
	private static int magicConstant=15;
	
    // Complete the formingMagicSquare function below.
    static int formingMagicSquare(int[][] s) {
    	
		return 0;
    }
	

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(Paths.get("src/main/java/plusMinus/data.txt"));
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/java/diagonalDifference/output_file.txt"));

		int[][] s = new int[3][3];

		List<List<Integer>> square= new ArrayList<List<Integer>>();
		for (int i = 0; i < 3; i++) {
			String[] sRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			List<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < 3; j++) {
				int sItem = Integer.parseInt(sRowItems[j]);
				row.add(sItem);
				s[i][j] = sItem;
			}
			square.add(row);
		}

		int result = formingMagicSquare(s);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

}
