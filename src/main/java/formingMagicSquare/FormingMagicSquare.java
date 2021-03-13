package formingMagicSquare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FormingMagicSquare {
	private static int matrixSize = 3;
	private static int magicConstant = getMagicConstant(matrixSize);
	private static int nDiagonals = matrixSize + matrixSize + 2;
	private static int middleIndex = (matrixSize - 1) / 2;
	private static int middleValue = BigDecimal.valueOf(matrixSize * matrixSize / (double) 2.0)
			.setScale(0, RoundingMode.HALF_UP).intValue();
	private static boolean isImpar = (middleValue % 2) != 0;

	// Complete the formingMagicSquare function below.
	static int formingMagicSquare(int[][] s, List<List<Integer>> diagonals, int[] numbers, int totalCost) {

		List<Integer> isACons = diagonals.stream().map(di -> di.stream().mapToInt(Integer::intValue).sum())
				.collect(Collectors.toList());
		boolean isMagicSq = isACons.parallelStream().allMatch(re -> re.intValue() == magicConstant);
		if (isMagicSq) {
			return 0;
		} else {

			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {

					if (middleValue == s[i][j]) {
						continue;
					} else {
						totalCost = totalCost + Math.abs(middleValue - s[i][j]);
						s[i][j] = middleValue;
						s = refreshProcessDiagonals(s, diagonals);
						isACons = refeshIsCons(diagonals);
						System.out.print(s[i][j] + "-new->" + middleValue);
					}
					if (isACons.get(i).intValue() == magicConstant
							&& isACons.get(j + matrixSize).intValue() == magicConstant
							&& (i == j || i + j == s.length - 1)) {
						int positionValue = s[i][j];
						Long countRow = diagonals.get(i).stream().filter(fil -> fil.intValue() == positionValue)
								.count();
						Long countColumn = diagonals.get(j + matrixSize).stream()
								.filter(fil -> fil.intValue() == positionValue).count();
						Long countDiagonal = diagonals.get(nDiagonals - 2).stream()
								.filter(fil -> fil.intValue() == positionValue).count();
						Long countDiagonal2 = diagonals.get(nDiagonals - 1).stream()
								.filter(fil -> fil.intValue() == positionValue).count();
						
						if (isACons.get(nDiagonals - 2).intValue() == magicConstant  && countRow==1 && countColumn==1 && countDiagonal==1) {
							continue;
						}
						if (isACons.get(nDiagonals - 1).intValue() == magicConstant && countRow==1 && countColumn==1 && countDiagonal2==1) {
							continue;
						}
					} else {
						if (isACons.get(i).intValue() == magicConstant
								&& isACons.get(j + matrixSize).intValue() == magicConstant
								&& (i != j && i + j != s.length - 1)) {
							continue;
						} 
					}

					int newValueI = Math.abs(s[i][j] + (magicConstant - isACons.get(i)));
					int newValueJ = Math.abs(s[i][j] + (magicConstant - isACons.get(j + matrixSize)));
					int finalValue = newValueI > newValueJ ? newValueJ : newValueI;
					int diagonal = magicConstant + 1;

					if (i == j) {
						diagonal = Math.abs(s[i][j] + (magicConstant - isACons.get(nDiagonals - 2)));
						finalValue = finalValue > diagonal ? diagonal : finalValue;
					}
					if (i + j == s.length - 1) {
						diagonal = Math.abs(s[i][j] + (nDiagonals - 1));
						finalValue = finalValue > diagonal ? diagonal : finalValue;
					}
					if (finalValue <= numbers.length) {
						System.out.print(s[i][j]);

						numbers[finalValue - 1] = numbers[finalValue - 1] + 1;

						totalCost = totalCost + Math.abs(finalValue - s[i][j]);
						s[i][j] = finalValue;
						s = refreshProcessDiagonals(s, diagonals);
						isACons = refeshIsCons(diagonals);

						System.out.println("-new->" + s[i][j]);
					}

				}
			}

			return totalCost;
		}

	}

	private static int getMagicConstant(int rows) {
		return (int) ((rows * (Math.pow(rows, 2) + 1)) / 2.0);
	}

	private static int[][] refreshProcessDiagonals(int[][] s, List<List<Integer>> diagonals) {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Integer sItem = s[i][j];
				diagonals.get(i).set(j, sItem);
				diagonals.get(j + matrixSize).set(i, sItem);
				if (i == j) {
					diagonals.get(nDiagonals - 2).set(i, sItem);
				}
				if (i + j == s.length - 1) {
					diagonals.get(nDiagonals - 1).set(i, sItem);
				}

			}
		}
		return s;
	}

	private static List<Integer> refeshIsCons(List<List<Integer>> diagonals) {
		return diagonals.stream().map(di -> di.stream().mapToInt(Integer::intValue).sum()).collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(Paths.get("src/main/java/formingMagicSquare/data.txt"));

		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter("src/main/java/formingMagicSquare/output_file.txt"));

		int[][] s = new int[matrixSize][matrixSize];
		int[] numbers = new int[matrixSize * matrixSize];

		List<List<Integer>> diagonals = new ArrayList<List<Integer>>();
		for (int i = 0; i < nDiagonals; i++) {
			diagonals.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < 3; i++) {
			String[] sRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			for (int j = 0; j < 3; j++) {
				Integer sItem = Integer.parseInt(sRowItems[j]);
				numbers[sItem - 1] = numbers[sItem - 1] + 1;
				s[i][j] = sItem;
				diagonals.get(i).add(sItem);
				diagonals.get(j + matrixSize).add(sItem);
				if (i == j) {
					diagonals.get(nDiagonals - 2).add(sItem);
				}
				if (i + j == s.length - 1) {
					diagonals.get(nDiagonals - 1).add(sItem);
				}

			}
		}

		int result = formingMagicSquare(s, diagonals, numbers, 0);
		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

}
