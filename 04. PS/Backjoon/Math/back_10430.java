import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class back_10430 {
    
    // 첫째 줄에 (A+B)%C
    // 둘째 줄에 ((A%C) + (B%C))%C
    // 셋째 줄에 (A×B)%C
    // 넷째 줄에 ((A%C) × (B%C))%C를 출력한다.

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");

        int a = Integer.parseInt(str[0]);
        int b = Integer.parseInt(str[1]);
        int c = Integer.parseInt(str[2]);

        System.out.println( (a+b) % c);
        System.out.println( ((a%c) + (b%c)) %c);
        System.out.println( (a*b) % c );
        System.out.println( ((a%c) * (b%c)) %c);
    }

}
