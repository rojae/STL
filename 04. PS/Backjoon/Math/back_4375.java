import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class back_4375 {
    
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력이 있을 때마다 계속해서 처리
        String line;
        
        while ((line = br.readLine()) != null) {
            // 빈 줄을 처리할 수 있도록 null 체크
            if (line.trim().isEmpty()) {
                continue;
            }
            int n = Integer.parseInt(line);

            // 계산기 호출하여 결과 출력
            System.out.println(new Calculator().getResult(n));
        }
    }

    // 1, 11, 111, 1111, 11111, 111111 ...
    static class Calculator {
        private long sz = 1;

        public long getResult(int n) {
            int remainder = 0;

            while (true) {
                // 나머지를 갱신
                remainder = ((10 * remainder) + 1) % n;
                
                // 나머지가 0이면 배수, 결과를 출력
                if (remainder == 0) {
                    return sz;
                } else {
                    sz++;
                }
            }
        }
    }
}
