package formingMagicSquare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FormingMagicSquare {
	private static int matrixSize = 3;
	private static int magicConstant = getMagicConstant(matrixSize);
	private static int nDiagonals = matrixSize + matrixSize + 2;

	// Complete the formingMagicSquare function below.
	static int formingMagicSquare(int[][] s,List<List<Integer>> diagonals,int [] numbers, int totalCost) {
		
		List<Integer> isACons= diagonals.stream().map(di -> di.stream().mapToInt(Integer::intValue).sum()).collect(Collectors.toList());
		boolean isMagicSq=isACons.parallelStream().allMatch(re -> re.intValue()==magicConstant);
		if (isMagicSq) {
			return 0;
		}else {
			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s.length; j++) {
					
					if(isACons.get(i).intValue()==magicConstant && isACons.get(j + matrixSize).intValue()==magicConstant) {
						continue;
					}else {
						if(isACons.get(i).intValue()!=magicConstant && isACons.get(j + matrixSize).intValue()==magicConstant){
							continue;
						}else {
							if(isACons.get(i).intValue()==magicConstant && isACons.get(j + matrixSize).intValue()!=magicConstant){
								continue;
							}else {
								if (i == j && isACons.get(nDiagonals-2).intValue()==magicConstant) {
									continue;
								}else {
									if (i + j == s.length - 1 && isACons.get(nDiagonals-1).intValue()==magicConstant ) {
										continue;
									}
								}
							}
						}
					}
					if(numbers[s[i][j]-1]>1) {
						int newValue=s[i][j]+(magicConstant-isACons.get(i));
						numbers[s[i][j]-1]=numbers[s[i][j]-1]-1;
						numbers[newValue-1]=numbers[newValue-1]+1;
						totalCost=totalCost+Math.abs(newValue-s[i][j]);
						s[i][j]=newValue;
					}
			
				
					
				}
			}
			
			boolean isEnd=Arrays.stream(numbers).allMatch(value -> value!=0 && value == 1);
			if(!isEnd) {
				s=processDiagonals(s, diagonals);
				totalCost=formingMagicSquare(s, diagonals, numbers,totalCost);
			}
			return totalCost;
		}
		
	}

	private static int getMagicConstant(int rows) {
		return (int) ((rows * (Math.pow(rows, 2) + 1)) / 2.0);
	}
	
	private static int[][] processDiagonals (int[][] s,List<List<Integer>> diagonals) {
	
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Integer sItem = s[i][j];
				diagonals.get(i).set(j,sItem);
				diagonals.get(j + matrixSize).set(j,sItem);
				if (i == j) {
					diagonals.get(nDiagonals-2).set(j,sItem);
				}
				if (i + j == s.length - 1) {
					diagonals.get(nDiagonals-1).set(j,sItem);
				}
				
			}
		}
		return s;
	}


	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(Paths.get("src/main/java/formingMagicSquare/data.txt"));

		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter("src/main/java/formingMagicSquare/output_file.txt"));

		int[][] s = new int[matrixSize][matrixSize];
		int [] numbers= new int[matrixSize*matrixSize];

		List<List<Integer>> diagonals = new ArrayList<List<Integer>>();
		for (int i = 0; i < nDiagonals; i++) {
			diagonals.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < 3; i++) {
			String[] sRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			for (int j = 0; j < 3; j++) {
				Integer sItem = Integer.parseInt(sRowItems[j]);
				numbers[sItem-1]=numbers[sItem-1]+1;
				s[i][j] = sItem;
				diagonals.get(i).add(sItem);
				diagonals.get(j + matrixSize).add(sItem);
				if (i == j) {
					diagonals.get(nDiagonals-2).add(sItem);
				}
				if (i + j == s.length - 1) {
					diagonals.get(nDiagonals-1).add(sItem);
				}
				
			}
		}

		
		int result = formingMagicSquare(s,diagonals,numbers,0);
		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedWriter.close();

		scanner.close();
	}

}
