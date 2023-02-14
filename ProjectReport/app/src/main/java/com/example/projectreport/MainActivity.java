package com.example.projectreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edtResult;

    static double result = 0;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onLoadConfig();
    }

    private void onLoadConfig() {
        edtResult = findViewById(R.id.edtResult);
        edtResult.setHint("");
    }

    public void buttonAllClear_Click(View view) {
        edtResult.setText("");
    }

    public void buttonNumber_Click(View view) {
        Button btn = findViewById(view.getId());
        StringBuilder builder = new StringBuilder();
        builder.append(edtResult.getText().toString()).append(btn.getText().toString());
        edtResult.setText(builder);
    }

    public void buttonDel_Click(View view) {
        StringBuilder builder = new StringBuilder(edtResult.getText().toString());
        if (builder.length() != 0) {
            builder.deleteCharAt(builder.length() - 1);
            edtResult.setText(builder);
        }
    }

    public void buttonDot_Click(View view) {
        StringBuilder builder = new StringBuilder(edtResult.getText().toString());
        if (!builder.toString().contains(".") && !builder.toString().contains("+") && !builder.toString().contains("-") &&
                !builder.toString().contains("x") && !builder.toString().contains("/")) {
            builder.append(".");
            edtResult.setText(builder);
        }
    }

    public void buttonAdd_Click(View view) {
        StringBuilder builder = new StringBuilder(edtResult.getText().toString());
        if (!(builder.toString().contains("+") || builder.toString().contains("-") ||
                builder.toString().contains("x") || builder.toString().contains("/")) && builder.length() != 0) {
            builder.append("+");
            edtResult.setText(builder);
        }
    }

    public void buttonSubtract_Click(View view) {
        StringBuilder builder = new StringBuilder(edtResult.getText().toString());
        if (!(builder.toString().contains("+") || builder.toString().contains("-") ||
                builder.toString().contains("x") || builder.toString().contains("/")) && builder.length() != 0) {
            builder.append("-");
            edtResult.setText(builder);
        }
    }

    public void buttonDivide_Click(View view) {
        StringBuilder builder = new StringBuilder(edtResult.getText().toString());
        if (!(builder.toString().contains("+") || builder.toString().contains("-") ||
                builder.toString().contains("x") || builder.toString().contains("/")) && builder.length() != 0) {
            builder.append("/");
            edtResult.setText(builder);
        }
    }

    public void buttonMultiply_Click(View view) {
        StringBuilder builder = new StringBuilder(edtResult.getText().toString());
        if (!(builder.toString().contains("+") || builder.toString().contains("-") ||
                builder.toString().contains("x") || builder.toString().contains("/")) && builder.length() != 0) {
            builder.append("x");
            edtResult.setText(builder);
        }
    }

    public void btnResult_Click(View view) {
        String currentText = edtResult.getText().toString();
        if (currentText.contains("+")) {
            int index = currentText.indexOf("+") + 1;
            if (index != currentText.length()) {
                try {
                    double number = Double.parseDouble(currentText.substring(index));
                    double currentNumber = Double.parseDouble(currentText.substring(0, index - 1));
                    result += number + currentNumber;
                    edtResult.setText(String.valueOf(result));
                    result = 0;
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                edtResult.setText(currentText);
            }
        } else if (currentText.contains("-")) {
            int index = currentText.indexOf("-") + 1;
            if (index != currentText.length()) {
                try {
                    double number = Double.parseDouble(currentText.substring(index));
                    double currentNumber = Double.parseDouble(currentText.substring(0, index - 1));
                    result += (number - currentNumber);
                    edtResult.setText(String.valueOf(result));
                    result = 0;
                    flag = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (currentText.contains("x")) {
            int index = currentText.indexOf("x") + 1;
            if (index != currentText.length()) {
                double number = Double.parseDouble(currentText.substring(index));
                double currentNumber = Double.parseDouble(currentText.substring(0, index - 1));
                result += (number * currentNumber);
                edtResult.setText(String.valueOf(result));
                result = 0;
                flag = true;
            }
        } else if (currentText.contains("/")) {
            int index = currentText.indexOf("/") + 1;
            if (index != currentText.length()) {
                double number = Double.parseDouble(currentText.substring(index));
                double currentNumber = Double.parseDouble(currentText.substring(0, index - 1));
                result += (number / currentNumber);
                edtResult.setText(String.valueOf(result));
                result = 0;
                flag = true;
            }
        } else {
            if (flag) {
                result = Double.parseDouble(currentText) + Double.parseDouble(currentText);
                edtResult.setText(String.valueOf(result));
                result = 0;
            }
        }
    }
}