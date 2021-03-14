package formingMagicSquare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FormingMagicSquareFail {
	private static int matrixSize = 3;
	private static int magicConstant = getMagicConstant(matrixSize);
	private static int nDiagonals = matrixSize + matrixSize + 2;
	private static int middleIndex = (matrixSize - 1) / 2;
	private static int middleValue = BigDecimal.valueOf(matrixSize * matrixSize / (double) 2.0)
			.setScale(0, RoundingMode.HALF_UP).intValue();
//	private static List<Integer> diagonalsValues = getdiagonalsValues();

	// Complete the formingMagicSquare function below.
	static int formingMagicSquare(int[][] s, List<List<Integer>> diagonals) {
		int[] numbers = new int[matrixSize * matrixSize];
		int totalCost = 0;

		List<Integer> isACons = diagonals.stream().map(di -> di.stream().mapToInt(Integer::intValue).sum())
				.collect(Collectors.toList());
		boolean isMagicSq = isACons.parallelStream().allMatch(re -> re.intValue() == magicConstant);

		if (isMagicSq) {
			return 0;
		} else {

			

			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {
					int finalValue = 0;
					int diagonal = magicConstant + 1;
					int positionValue = s[i][j];
					if (middleIndex == i && middleIndex == j && middleValue == s[i][j]) {
						continue;
					} else {
//						if (isACons.get(i).intValue() == magicConstant
//								&& isACons.get(j + matrixSize).intValue() == magicConstant) {
//
//							Long countRow = diagonals.get(i).stream().filter(fil -> fil.intValue() == positionValue)
//									.count();
//							Long countColumn = diagonals.get(j + matrixSize).stream()
//									.filter(fil -> fil.intValue() == positionValue).count();
//							Long countDiagonal = diagonals.get(nDiagonals - 2).stream()
//									.filter(fil -> fil.intValue() == positionValue).count();
//							Long countDiagonal2 = diagonals.get(nDiagonals - 1).stream()
//									.filter(fil -> fil.intValue() == positionValue).count();
//							if ((i == j) && isACons.get(nDiagonals - 2).intValue() == magicConstant && countRow == 1
//									&& countColumn == 1 && countDiagonal == 1) {
//								continue;
//							}
//							if (i + j == s.length - 1 && isACons.get(nDiagonals - 1).intValue() == magicConstant
//									&& countRow == 1 && countColumn == 1 && countDiagonal2 == 1) {
//								continue;
//							}
//						} else {
//							if (isACons.get(i).intValue() == magicConstant
//									&& isACons.get(j + matrixSize).intValue() == magicConstant
//									&& (i != j && i + j != s.length - 1)) {
//								continue;
//							}
//						}
						int newValueI = Math.abs(s[i][j] + (magicConstant - isACons.get(i).intValue()));
						int newValueJ = Math.abs(s[i][j] + (magicConstant - isACons.get(j + matrixSize).intValue()));
						List<Integer> toOverride=Stream.of(newValueI,newValueJ).collect(Collectors.toList());

						long diagonal1 = diagonals.get(nDiagonals - 2).stream()
								.filter(fil -> fil.intValue() == positionValue).count();
						long diagonal2 = diagonals.get(nDiagonals - 1).stream()
								.filter(fil -> fil.intValue() == positionValue).count();

						if (i == j && diagonal1 >= 1 && s[i][j] % 2 != 0 ) {
							diagonal = Math.abs(s[i][j] + (magicConstant - isACons.get(nDiagonals - 2)));
							toOverride.add(diagonal);
							toOverride=toOverride.stream().filter(fil -> esPar(fil)).collect(Collectors.toList());
						}
						if (i + j == s.length - 1 && diagonal2 >= 1 && s[i][j] % 2 != 0) {
							diagonal = Math.abs(s[i][j] +  (magicConstant - isACons.get(nDiagonals - 1)));
							toOverride.add(diagonal);
							toOverride=toOverride.stream().filter(fil -> esPar(fil)).collect(Collectors.toList());
						}
						
						if (i == middleIndex&&i == middleIndex && middleValue != s[middleIndex][middleIndex]) {
							finalValue=middleValue;
						}else {
							finalValue=toOverride.stream().filter(fil->fil.intValue()!=middleValue).mapToInt(v -> v).min().getAsInt();
						}
						
						if (finalValue != s[i][j] && finalValue > 0 && finalValue <= numbers.length) {
							System.out.print(s[i][j]);

							totalCost = totalCost + Math.abs(finalValue - s[i][j]);
							s[i][j] = finalValue;
							s = refreshProcessDiagonals(s, diagonals);
							isACons = refeshIsCons(diagonals);

							System.out.println("-new->" + s[i][j]);
						}
					}
					numbers[s[i][j] - 1] = numbers[s[i][j] - 1] + 1;
				}
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print(s[i][j]+" ");
				}
				System.out.println();
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
	static boolean esPar(int numero){
	    if (numero%2==0) return true; else return false;
	}
//
//	private static List<Integer> getdiagonalsValues() {
//		return IntStream.rangeClosed(0, matrixSize * matrixSize).filter(fil -> (fil % 2 == 0)).boxed()
//				.collect(Collectors.toList());
//	}

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(Paths.get("src/main/java/formingMagicSquare/data.txt"));

		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter("src/main/java/formingMagicSquare/output_file.txt"));

		int[][] s = new int[matrixSize][matrixSize];

		List<List<Integer>> diagonals = new ArrayList<List<Integer>>();
		for (int i = 0; i < nDiagonals; i++) {
			diagonals.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < 3; i++) {
			String[] sRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			for (int j = 0; j < 3; j++) {
				Integer sItem = Integer.parseInt(sRowItems[j]);
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

		int result = formingMagicSquare(s, diagonals);
		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

}
