package com.example.projectreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText edtResult;

    static Double result = null;

    static Double a = null;

    static Double b = null;

    boolean operation = false;
    boolean dotted = true;

    boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edtResult = findViewById(R.id.edtResult);
        edtResult.setHint("");
        edtResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 15) {
                    Toast.makeText(getApplicationContext(), "Không nhập nhiều hơn 15 chữ số.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void buttonAllClear_Click(View view) {
        operation = false;
        dotted = true;
        a = null;
        b = null;
        edtResult.setText("");
        error = false;
//        Gán lại ban đầu
    }

    public void buttonNumber_Click(View view) {
        Button btn = findViewById(view.getId());
        String text = edtResult.getText().toString();
        if ((btn.getText().toString().equals("0") && text.equals("0"))
                || (a != null && text.endsWith("0"))) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(edtResult.getText().toString()).append(btn.getText().toString());
        edtResult.setText(builder);
        edtResult.setSelection(edtResult.getText().length());
        operation = true;

        edtResult.setSelection(edtResult.getText().length());
    }

    public void buttonDel_Click(View view) {
        String text = edtResult.getText().toString();
        if (text.length() != 0) {
            int index = text.length() - 1;
            char symbol = text.charAt(index);
            edtResult.setText(text.substring(0, index));
//            Nếu chưa có ký tự (cho nhập trừ) hoặc có ký tự thì xóa cho nhập lại
            if (symbol == '+' || symbol == '-' || symbol == '×' || symbol == '/') {
                a = null;
                operation = true;
            }
//            Nếu ký tự là chấm thì cho xóa cho nhập lại
            else if (symbol == '.') {
                dotted = true;
            } else if ((text.startsWith("-") && text.indexOf('-', 1) == -1)
                    || text.indexOf('+', 1) == -1 ||
                    text.indexOf('×', 1) == -1 ||
                    text.indexOf('/', 1) == -1) {
                operation = true;
            }
            edtResult.setSelection(edtResult.getText().length());
        }
    }

    public void buttonOperation_Click(View view) {
        Button btn = findViewById(view.getId());
        String text = btn.getText().toString();
        StringBuilder screen = new StringBuilder(edtResult.getText().toString());
//        Nếu ký tự đầu là -
        if (text.equals("-") && screen.toString().equals("")) {
            edtResult.setText(text);
            edtResult.setSelection(edtResult.getText().length());
        }
//      Tính kết quả thông qua dấu
        else if (a != null && operation) {
            String symbol = btn.getText().toString();
            char _symbol = symbol.charAt(0);
            String tmp = edtResult.getText().toString();
//            Khoong phai dau tru ban dau
            if (tmp.indexOf(_symbol, 1) != -1) {
                btnResult_Click(view);
            }
        }
        //        Nếu được phép nhập dấu, được phép chấm
        else if (operation && !error) {
            a = Double.parseDouble(screen.toString());
            screen.append(btn.getText().toString());
            edtResult.setText(screen);
            operation = false;
            dotted = true;
        }
        edtResult.setSelection(edtResult.getText().length());
    }

    public void buttonDot_Click(View view) {
        if (dotted && !error) {
            Button btn = findViewById(view.getId());
            StringBuilder builder = new StringBuilder();
            builder.append(edtResult.getText().toString()).append(btn.getText().toString());
            edtResult.setText(builder);
            dotted = false;
            operation = false;
        }
        edtResult.setSelection(edtResult.getText().length());
//            Nếu nhập chấm thì không cho nhập nữa và không cho thực hiện phép toán

    }

    public void btnResult_Click(View view) {
        String text = edtResult.getText().toString();

//        A khac null && độ dài chuỗi hiện tại + 1 ký tự bất kỳ lớn nhỏ hơn màn hình
        if (a != null) {
            handleOperation(text);
        } else if (result != null) {
            //            Cộng kết quả còn tồn lại
            result += result;
            int temp = result.intValue();
            if (result == temp) {
                edtResult.setText(String.valueOf(temp));
            } else {
                edtResult.setText(String.valueOf(result));
            }
            edtResult.setSelection(edtResult.getText().length());
            operation = true;
            dotted = !edtResult.getText().toString().contains(".");
            a = null;
            b = null;
        }
//        Reset
    }


    public void handleOperation(String text) {
        if (text.contains("+")) {
            int index = text.indexOf('+');
            b = Double.parseDouble(text.substring(index + 1));
            result = a + b;
            edtResult.setText(String.valueOf(result));
        } else if (text.contains("-")) {
            int index = text.indexOf('-');
            if (a < 0) {
                index = text.indexOf('-', 1);
            }
            b = Double.parseDouble(text.substring(index + 1));
            result = a - b;
            edtResult.setText(String.valueOf(result));
        } else if (text.contains("×")) {
            int index = text.indexOf('×');
            b = Double.parseDouble(text.substring(index + 1));
            result = a * b;
            edtResult.setText(String.valueOf(result));
        } else if (text.contains("/")) {
            int index = text.indexOf('/');
            b = Double.parseDouble(text.substring(index + 1));
            if (b != 0) {
                result = a / b;
                edtResult.setText(String.valueOf(result));
            } else {
                Toast.makeText(this, "Không thể chia cho 0.", Toast.LENGTH_LONG).show();
                edtResult.setSelection(edtResult.getText().length());
                edtResult.setText(text);
                dotted = false;
                operation = false;
                error = true;
            }
        }
        edtResult.setSelection(edtResult.getText().length());
        operation = true;
        dotted = !edtResult.getText().toString().contains(".");
        a = null;
        b = null;
    }
}