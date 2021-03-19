public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        String str = "2aax2[y]]";
        boolean check = test.CheckValid(str);
        if (check) {
            String res = test.Unpucking(str);
            System.out.println("Unpacked string " + res);
        } else System.out.println("The string is not valid");

    }

    public static int cnt;
    public static int flag;

    public String Unpucking(String str) {
        StringBuilder str_new = new StringBuilder(str);
        ;
        StringBuilder str_tmp = new StringBuilder();
        int index = 0;
        int tmp_index = 0;
        int value = 0;
        cnt = 0;
        while (index < str_new.length()) {
            int tmp_int = Character.getNumericValue(str_new.charAt(index));
            if (tmp_int < -1 || tmp_int > 9) {
                str_new.setCharAt(index, str_new.charAt(index));
                index++;
            } else {
                if (tmp_int > -1) {
                    tmp_index = index;
                    value = Value(str_new.toString(), index);
                    index += cnt;
                    flag++;
                    cnt = 0;
                } else {
                    while (str_new.charAt(index + 1) != ']') {
                        if (Character.getNumericValue(str_new.charAt(index + 1)) < -1 || Character.getNumericValue(str_new.charAt(index + 1)) > 9) {
                            str_tmp.append(str_new.charAt(index + 1));
                            index++;
                        } else {
                            StringBuilder str_tmp_char_2 = new StringBuilder(str_new);
                            str_tmp_char_2.delete(index + 1, str_tmp_char_2.length());
                            str_new.delete(0, index + 1);
                            str_tmp_char_2.append(Unpucking(str_new.toString()));
                            str_new.delete(0, str_new.length());
                            str_new.append(str_tmp_char_2);
                        }
                    }
                    int size_substring = (value - 1) * str_tmp.length();
                    for (int j = 0; j < size_substring; j++) {
                        str_tmp.append(str_tmp.charAt(j % str_tmp.length()));
                    }
                    str_new.replace(tmp_index, index + 2, str_tmp.toString());
                    index = tmp_index + str_tmp.length();
                    str_tmp.delete(0, str_tmp.length());
                    flag--;
                    if (flag != 0) return str_new.toString();
                }
            }
        }
        return str_new.toString();
    }

    public int Value(String str, int cnt) {
        StringBuilder str_Value = new StringBuilder();
        while (str.charAt(cnt) != '[') {
            str_Value.append(str.charAt(cnt));
            cnt++;
            Test.cnt++;
        }
        return Integer.parseInt(str_Value.toString());
    }

    public boolean CheckValid(String str_old) {
        int cnt = 0;
        if (str_old.matches("[a-zA-Z1-9\\[\\]]+")) {
            for (int i = 0; i < str_old.length(); i++) {
                if ((Character.getNumericValue(str_old.charAt(i)) > 0 && Character.getNumericValue(str_old.charAt(i)) <= 10) && Character.getNumericValue(str_old.charAt(i + 1)) > 9) {
                    return false;
                }
                if (str_old.charAt(i) == '[') {
                    if (str_old.charAt(i - 1) != '[') cnt++;
                    else return false;
                }
                if (str_old.charAt(i) == ']') {
                    if (str_old.charAt(i - 1) != '[') cnt--;
                    else return false;
                }
            }
            if (cnt == 0) return true;
        }
        return false;
    }
}