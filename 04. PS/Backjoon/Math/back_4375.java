import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class back_4375 {
    
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // �Է��� ���� ������ ����ؼ� ó��
        String line;
        
        while ((line = br.readLine()) != null) {
            // �� ���� ó���� �� �ֵ��� null üũ
            if (line.trim().isEmpty()) {
                continue;
            }
            int n = Integer.parseInt(line);

            // ���� ȣ���Ͽ� ��� ���
            System.out.println(new Calculator().getResult(n));
        }
    }

    // 1, 11, 111, 1111, 11111, 111111 ...
    static class Calculator {
        private long sz = 1;

        public long getResult(int n) {
            int remainder = 0;

            while (true) {
                // �������� ����
                remainder = ((10 * remainder) + 1) % n;
                
                // �������� 0�̸� ���, ����� ���
                if (remainder == 0) {
                    return sz;
                } else {
                    sz++;
                }
            }
        }
    }
}
