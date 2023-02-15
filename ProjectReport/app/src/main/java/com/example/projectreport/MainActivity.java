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
        edtResult.setInputType(InputType.TYPE_NULL);
        edtResult.setTextIsSelectable(true);
        edtResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 15 && result == null) {
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
        result = null;
        edtResult.setText("");
        error = false;
    }

    public void buttonNumber_Click(View view) {
        Button btn = findViewById(view.getId());
        String text = edtResult.getText().toString();
        if (text.equals("0") || (a != null && text.endsWith("0"))) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(edtResult.getText().toString()).append(btn.getText().toString());
        edtResult.setText(builder);
        operation = true;
    }

    public void buttonDel_Click(View view) {
        String text = edtResult.getText().toString();
        if (text.length() != 0) {
            int index = text.length() - 1;
            char symbol = text.charAt(index);
            edtResult.setText(text.substring(0, index));
            if (symbol == '+' || symbol == '-' || symbol == '×' || symbol == '/') {
                a = null;
                operation = true;
            } else if (symbol == '.') {
                dotted = true;
            } else if ((text.startsWith("-") && text.indexOf('-', 1) == -1) || text.indexOf('+', 1) == -1 || text.indexOf('×', 1) == -1 || text.indexOf('/', 1) == -1) {
                operation = true;
            }
        }
    }

    public void buttonOperation_Click(View view) {
        Button btn = findViewById(view.getId());
        String text = btn.getText().toString();
        StringBuilder screen = new StringBuilder(edtResult.getText().toString());
        if (text.equals("-") && screen.toString().equals("")) {
            edtResult.setText(text);
        } else if (operation && result != null) {
            a = result;
            screen.append(btn.getText().toString());
            edtResult.setText(screen);
        } else if (a != null && operation) {
            String symbol = btn.getText().toString();
            char _symbol = symbol.charAt(0);
            String tmp = edtResult.getText().toString();
            if (tmp.indexOf(_symbol, 1) != -1) {
                btnResult_Click(view);
            }
        } else if (operation && !error) {
            a = Double.parseDouble(screen.toString());
            screen.append(btn.getText().toString());
            edtResult.setText(screen);
            operation = false;
            dotted = true;
        } else if (screen.toString().endsWith("/") || screen.toString().endsWith("×")) {
            if (btn.getText().toString().equals("-")) {
                screen.append(btn.getText().toString());
                edtResult.setText(screen);
            }
        }
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
            operation = true;
            dotted = !edtResult.getText().toString().contains(".");
            a = null;
            b = null;
        }
    }


    public void handleOperation(String text) {
        if (!text.endsWith("-") && !text.endsWith("+") && !text.endsWith("×") && !text.endsWith("/") && !text.endsWith(".")) {
            if (text.contains("+")) {
                add(text);
            } else if (text.contains("-")) {
                int index = text.indexOf('-', 1);
                if (index != -1 && text.charAt(index - 1) != '/' && text.charAt(index - 1) != '×') {
                    subtract(text, index);
                } else {
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
            operation = true;
            dotted = !edtResult.getText().toString().contains(".");
            a = null;
            b = null;
        }
    }

    private void subtract(String text, int index) {
        b = Double.parseDouble(text.substring(index + 1));
        result = a - b;
        String tmp = String.valueOf(result);
        edtResult.setText(rounding(tmp));

    }

    private void add(String text) {
        int index = text.indexOf('+');
        b = Double.parseDouble(text.substring(index + 1));
        result = a + b;
        String tmp = String.valueOf(result);
        edtResult.setText(rounding(tmp));
    }

    private void divide(String text) {
        int index = text.indexOf('/');
        b = Double.parseDouble(text.substring(index + 1));
        if (b != 0) {
            result = a / b;
            String tmp = String.valueOf(result);
            edtResult.setText(rounding(tmp));
        } else {
            Toast.makeText(this, "Không thể chia cho 0.", Toast.LENGTH_LONG).show();
            edtResult.setText(text);
            dotted = false;
            operation = false;
            error = true;
        }
    }

    private void multiple(String text) {
        int index = text.indexOf('×');
        b = Double.parseDouble(text.substring(index + 1));
        result = a * b;
        String tmp = String.valueOf(result);
        edtResult.setText(rounding(tmp));
    }

    private String rounding(String temp) {
        int temp_a = a.intValue();
        int temp_b = b.intValue();
        if (a == temp_a && b == temp_b) {
            return temp.substring(0, temp.length() - 2);
        }
        return temp;
    }
}