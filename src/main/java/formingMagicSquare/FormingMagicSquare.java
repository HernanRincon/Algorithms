package formingMagicSquare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FormingMagicSquare {
	private static int matrixSize = 3;
	private static int[][][] permutations = { { { 8, 1, 6 }, { 3, 5, 7 }, { 4, 9, 2 } },
			{ { 6, 1, 8 }, { 7, 5, 3 }, { 2, 9, 4 } }, { { 4, 9, 2 }, { 3, 5, 7 }, { 8, 1, 6 } },
			{ { 2, 9, 4 }, { 7, 5, 3 }, { 6, 1, 8 } }, { { 8, 3, 4 }, { 1, 5, 9 }, { 6, 7, 2 } },
			{ { 4, 3, 8 }, { 9, 5, 1 }, { 2, 7, 6 } }, { { 6, 7, 2 }, { 1, 5, 9 }, { 8, 3, 4 } },
			{ { 2, 7, 6 }, { 9, 5, 1 }, { 4, 3, 8 } }, };

	// Complete the formingMagicSquare function below.
	static int formingMagicSquare(int[][] s) {

		List<Integer> totalCost = new ArrayList<Integer>();
		IntStream.range(0, permutations.length).parallel().forEach(val -> matrixCost(s, permutations[val], totalCost));

		totalCost.forEach(System.out::println);

		return totalCost.stream().mapToInt(v -> v).min().orElse(0);

	}

	private static List<Integer> matrixCost(int[][] s, int[][] permutation, List<Integer> costs) {
		int total = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				total = total + Math.absExact(s[i][j] - permutation[i][j]);
			}
		}
		costs.add(total);
		return costs;
	}

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(Paths.get("src/main/java/formingMagicSquare/data.txt"));

		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter("src/main/java/formingMagicSquare/output_file.txt"));

		int[][] s = new int[matrixSize][matrixSize];

		for (int i = 0; i < 3; i++) {
			String[] sRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			for (int j = 0; j < 3; j++) {
				Integer sItem = Integer.parseInt(sRowItems[j]);
				s[i][j] = sItem;

			}
		}

		int result = formingMagicSquare(s);
		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

}
