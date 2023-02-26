package com.example.projectreport;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainActivity extends AppCompatActivity {
    //    Biến sẽ tham chiếu đến ô hiểm thị kết quả
    EditText edtResult;
    //    Biến lưu  giá trị kết quả
    static Double result = null;
    //    Biến lưu gía trị của số thứ 1
    static Double a = null;
    //    Biến lưu giá trị của số số thứ 2
    static Double b = null;
    //    Kiểm tra phép toán có được thực hiện chưa
    boolean operation = false;
    //    Kiểm tra dấu chấm đã được dùng chưa
    boolean dot = true;
    //    Kiểm tra xem lỗi divide by zero
    boolean divideByZero = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
//        Tham chiếu đến edtResult trong activity_main.xml
        edtResult = findViewById(R.id.edtResult);
//        Ẩn hint
        edtResult.setHint("");
//        Không cho người dùng nhập dữ liệu
        edtResult.setInputType(InputType.TYPE_NULL);
//        Cho phép chọn nội dung để copy
        edtResult.setTextIsSelectable(true);
//        Sự kiện khi thay đổi text -> dùng kể kiểm tra số lượng ký tự đã nhập
        edtResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Nếu nhập vào quá 15 số
                if (result == null) {
                    if (editable.length() >= 15) {
                        Toast.makeText(getApplicationContext(), "Không nhập nhiều hơn 15 chữ số.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void buttonAllClear_Click(View view) {
//        Không cho phép thực hiện phép toán (phần nhập dấu sẽ kiểm tra nếu có dấu trừ ở đầu và cho nhập)
        operation = false;
//        Cho phép nhập giấu chấm
        dot = true;
//        Gán biến a về mặc định
        a = null;
//        Gán biến b về mặc định
        b = null;
//        Gán kết quả về mặc định
        result = null;
//        Gán kết quả về rỗng
        edtResult.setText("");
//        Gán lỗi chia cho không chưa xảy ra
        divideByZero = false;
    }

    public void buttonNumber_Click(View view) {
//        Tham chiếu thông qua id của sự kiện click
        Button btn = findViewById(view.getId());
//        Lấy giá trị của EditText
        String text = edtResult.getText().toString();
//        Thực hiện cộng liên tục (gán a = kết quả trước, kết quả về null)
        if (a != null && result != null) {
            a = result;
            result = null;
            String txt = text + btn.getText().toString();
            edtResult.setText(txt);
        }
//        Nếu nhập số 0 ở đầu thì không cho nhập số 0 nữa
        if (text.equals("0") || (a != null && text.endsWith("0"))) {
            return;
        }
//        Mặc định lấy ký tự của nút bấm thêm vào sau chuỗi
        String src = text + btn.getText().toString();
        edtResult.setText(src);
//        Cho phép nhập phép toán nếu chưa có phép (vừa nhập số)
        operation = true;
    }

    public void buttonDel_Click(View view) {
        String text = edtResult.getText().toString();
//        Nếu text rỗng thì không làm gì
        if (text.length() != 0) {
            int index = text.length() - 1;
//            Lấy ký tự ở vị trí cuối cùng
            char symbol = text.charAt(index);
//            Xóa ký tự ở cuối trong EditText
            edtResult.setText(text.substring(0, index));
//            Nếu mà ký tự đó là phép toán thì cho phép nhập lại phép toán và reset biến a
            if (symbol == '+' || symbol == '-' || symbol == '×' || symbol == '/') {
                a = null;
                operation = true;
//                Nếu ký tự đã xóa là dấu chấm cho phép nhập dấu chấm
            } else if (symbol == '.') {
                dot = true;
            }
//            Nếu ký tự đã xóa là số 0 và bị lỗi chia 0
            else if (symbol == '0' && divideByZero) {
                divideByZero = false;
                dot = true;
            }
//            Nếu edt bắt đầu bằng dấu - (a âm) và không tồn tại dấu trừ phía sau đó, VD: -5
//            hoặc không tồn tại dấu cộng phái sau đó, 5
//            hoặc không tồn tại dấu nhân phái sau đó, 5
//            hoặc không tồn tại dấu chia phái sau đó, 5
//            thì cho phép nhập dấu vào
            else if ((text.startsWith("-") && text.indexOf('-', 1) == -1) ||
                    text.indexOf('+', 1) == -1 || text.indexOf('×', 1) == -1 ||
                    text.indexOf('/', 1) == -1) {
                operation = true;
            }
        }
    }

    public void buttonOperation_Click(View view) {
        Button btn = findViewById(view.getId());
//        Ký tự trên btn
        String text = btn.getText().toString();
//        Chuỗi trên edt
        StringBuilder screen = new StringBuilder(edtResult.getText().toString());
//        Nếu đã có dấu thì không cho nhập nữa (dấu - ở đầu sẽ được xử lý ở nơi khác)
        if (screen.toString().endsWith("-") || screen.toString().endsWith("+") || screen.toString().endsWith("/") || screen.toString().endsWith("×")) {
            operation = false;
        } else if (screen.toString().endsWith(".")) {
            dot = false;
        }
//        ký tự - và chuỗi trống
        else if (text.equals("-") && screen.toString().equals("")) {
            edtResult.setText(text);
//        Nếu đã có rs và người dùng bấm = liên tục
        } else if (operation && result != null) {
//            Gán a = rs để nếu người dùng muốn thực hiện phép toán tiếp chỉ gán rs = a + b
            a = result;
//            edt thêm giá trị của nút bấm vào
            screen.append(btn.getText().toString());
            edtResult.setText(screen);
//            Nếu chưa nhập giá trị a và phép toán được phép nhập
        } else if (a != null && operation) {
            String symbol = btn.getText().toString();
            char _symbol = symbol.charAt(0);
//            Lấy ra ký tự của btn nếu edt chưa có chứa ký tự đó thì cho phép thực hiện phép toán
//            Tương tự việc bấm dấu =
//            VD: 5 + 3, 5-3, 5*3, 5/3
            String tmp = edtResult.getText().toString();
            if (tmp.indexOf(_symbol, 1) != -1) {
                btnResult_Click(view);
            }
//            Nếu được phép thực hiện phép toán và không có lỗi chia cho không
        } else if (operation && !divideByZero) {
//            gán giá trị cho biến a
            try {
                a = Double.parseDouble(screen.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
//            Thêm phép toán vào chuỗi
            screen.append(btn.getText().toString());
            edtResult.setText(screen);
//            Không cho thực hiện phép toán vì đã tồn tại
            operation = false;
//            Cho phép số b bắt đầu = dấu .
            dot = true;
//            Nếu đã có phép toán /, * ở cuối chuỗi có thể nhập thêm toán tử trừ
        } else if (screen.toString().endsWith("/") || screen.toString().endsWith("×")) {
            if (btn.getText().toString().equals("-")) {
                screen.append(btn.getText().toString());
                edtResult.setText(screen);
            }
        }
    }

    public void buttonDot_Click(View view) {
//        Nếu chưa có dấu chấm và nó không có lỗi chia 0
//        thì cho phép thêm dấu .
//        Hoặc nếu như số a đã có dấu chấm thì kiểm tra xem, nếu số b chưa có dấu . thì cho
//        nhập vào dấu . cho b
        String string_a = String.valueOf(a);
        String txt = edtResult.getText().toString();
        boolean checked;
        try {
            checked = txt.substring(string_a.length()).contains(".");
        } catch (Exception e) {
            checked = false;
        }
        if ((dot && !divideByZero) || (a != null && !checked)) {
            Button btn = findViewById(view.getId());
            StringBuilder builder = new StringBuilder();
            builder.append(edtResult.getText().toString()).append(btn.getText().toString());
            edtResult.setText(builder);
//            không thể nhập thêm dấu. nữa
            dot = false;
//            Sau dấu . phải là số
            operation = false;
        }
    }

    public void btnResult_Click(View view) {
        String text = edtResult.getText().toString();
//       Giá trị biến a khác null thì được phép thực hiện phép toán
//        (Biến a được lấy giá trị khi người dùng nhập phép toán)
        if (a != null) {
            handleOperation(text);
//            Cho người dùng spam nút = khi đã có kết quả
        } else if (result != null) {
//            Spam
            result += result;
            BigDecimal _result = BigDecimal.valueOf(result);
            result = _result.doubleValue();
            String rs = String.valueOf(result);
            if (_result.intValue() == result) {
                rs = String.valueOf(_result.intValue());
            }
            edtResult.setText(rs);
//           nếu kết quả không có dấu chấm thì được thêm dấu .
            dot = !edtResult.getText().toString().contains(".");
//            Reset
            operation = true;
            a = null;
            b = null;
        }
    }

    public void handleOperation(String text) {
//        Thực hiện phép toán khi nó được kết thức bằng 1 con số
        if (!text.endsWith("-") && !text.endsWith("+") && !text.endsWith("×") && !text.endsWith("/") && (!text.endsWith("."))) {
            if (text.contains("+")) {
                add(text);
            } else if (text.contains("-")) {
//                Loại bỏ trường hợp dấu - ở đầu
                int index = text.indexOf('-', 1);
//                nếu không có dấu - ở giũa và không có dấu / ở cuối và không có dấu *,/ ở trước dấu trừ
//                thì được thực hiện phép trừ
                if (index != -1 && text.charAt(index - 1) != '/' && text.charAt(index - 1) != '×') {
                    subtract(text, index);
                } else {
//                    Nếu a âm và nó không phải phép toán  từ thì thực hiện phép toán khác
                    if (text.contains("+")) {
                        add(text);
                    } else if (text.contains("×")) {
                        multiple(text);
                    } else if (text.contains("/")) {
                        divide(text);
                    }
                }
            } else if (text.contains("×")) {
                multiple(text);
            } else if (text.contains("/")) {
                divide(text);
            }
//          Có thể thực hiện được phép toán
            operation = true;
//            Nếu không có dấu chấn thì được phép thêm .
            dot = !edtResult.getText().toString().contains(".");
        }
    }

    private void subtract(String text, int index) {
//        bỏ qua dấu trừ ở giũa để lấy số b và thực hiện trừ
        try {
            b = Double.parseDouble(text.substring(index + 1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String rs = resultOfOperation("-");
        edtResult.setText(rs);
//        Reset
        a = null;
        b = null;
    }

    private void add(String text) {
        int index = text.indexOf('+');
        try {
            b = Double.parseDouble(text.substring(index + 1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String rs = resultOfOperation("+");
        edtResult.setText(rs);
        a = null;
        b = null;
    }

    private void divide(String text) {
        int index = text.indexOf('/');
        try {
            b = Double.parseDouble(text.substring(index + 1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (b != 0) {
            String rs = resultOfOperation("/");
            edtResult.setText(rs);
            a = null;
            b = null;
        } else {
            Toast.makeText(this, "Không thể chia cho 0.", Toast.LENGTH_LONG).show();
            edtResult.setText(text);
//            Nếu bị lỗi thì có thể biến a còn được dùng nên không reset
//            3/0
//            3/.x (được kiểm tra ở btn dot)
            dot = false;
            operation = false;
            divideByZero = true;
        }
    }

    private void multiple(String text) {
        int index = text.indexOf('×');
        try {
            b = Double.parseDouble(text.substring(index + 1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String rs = resultOfOperation("×");
        edtResult.setText(rs);
        a = null;
        b = null;
    }

    private String resultOfOperation(String op) {
        String rs = "";
        switch (op) {
            case "+": {
                BigDecimal _a = BigDecimal.valueOf(a);
                BigDecimal _b = BigDecimal.valueOf(b);
                BigDecimal _result = _a.add(_b);
                result = _result.doubleValue();
                if (result == _result.intValue()) {
                    rs = String.valueOf(_result.intValue());
                } else if (result > _result.intValue()) {
                    rs = String.valueOf(result);
                }
            }
            break;
            case "-": {
                BigDecimal _a = BigDecimal.valueOf(a);
                BigDecimal _b = BigDecimal.valueOf(b);
                BigDecimal _result = _a.subtract(_b);
                result = _result.doubleValue();
                if (result == _result.intValue()) {
                    rs = String.valueOf(_result.intValue());
                } else if (result > _result.intValue()) {
                    rs = String.valueOf(result);
                }
            }
            break;
            case "×": {
                BigDecimal _a = BigDecimal.valueOf(a);
                BigDecimal _b = BigDecimal.valueOf(b);
                BigDecimal _result = _a.multiply(_b);
                result = _result.doubleValue();
                if (result == _result.intValue()) {
                    rs = String.valueOf(_result.intValue());
                } else if (result > _result.intValue()) {
                    rs = String.valueOf(result);
                }
            }
            break;
            case "/": {
                BigDecimal _a = BigDecimal.valueOf(a);
                BigDecimal _b = BigDecimal.valueOf(b);
                BigDecimal _result = _a.divide(_b, RoundingMode.FLOOR);
                result = _result.doubleValue();
                if (result == _result.intValue()) {
                    rs = String.valueOf(_result.intValue());
                } else if (result > _result.intValue()) {
                    rs = String.valueOf(result);
                }
            }
            break;
        }
        return rs;
    }
}