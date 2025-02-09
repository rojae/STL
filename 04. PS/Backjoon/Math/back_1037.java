import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class back_1037{
    
    public static void main(String args[]) throws NumberFormatException, IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // numberCount 변수는 의미가 없네..?
        int numberCount = Integer.parseInt(br.readLine());

        String[] str = br.readLine().split(" ");
        int[] numbers = new int[str.length];

        for(int j=0; j<str.length; j++){
            numbers[j] = Integer.parseInt(str[j]);
        }

        // ascending
        Arrays.sort(numbers);

        System.out.println(numbers[0] * numbers[numbers.length - 1]);
    }
}