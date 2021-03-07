package diagonalDifference;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.nio.file.Files;


public class DiagonalDiference {

	/*
	 * Complete the 'diagonalDifference' function below.
	 *
	 * The function is expected to return an INTEGER. The function accepts
	 * 2D_INTEGER_ARRAY arr as parameter.
	 */

	public static int diagonalDifference(List<List<Integer>> arr, int n) {
		int diagonal1=n-n;
		int diagonal2=n-1;
		
		int sumD1=arr.stream().mapToInt(d1 -> d1.get(diagonal1+arr.indexOf(d1))).sum();
		int sumD2=arr.stream().mapToInt(d2 -> d2.get(diagonal2-arr.indexOf(d2))).sum();
		
		return Math.abs(sumD1-sumD2);

	}
	


	public static void main(String[] args) throws IOException {
	
		Path path = Paths.get("src/main/java/diagonalDifference/data.txt");
		BufferedReader bufferedReader = Files.newBufferedReader(path) ;
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/java/diagonalDifference/output_file.txt"));

		int n = Integer.parseInt(bufferedReader.readLine().trim());

		List<List<Integer>> arr = new ArrayList<List<Integer>>();
	
		IntStream.range(0, n).forEach(i -> {
			try {
				arr.add(Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" ")).map(Integer::parseInt)
						.collect(toList()));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		int result = diagonalDifference(arr,n);

		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();

		bufferedReader.close();
		bufferedWriter.close();
	}
}
