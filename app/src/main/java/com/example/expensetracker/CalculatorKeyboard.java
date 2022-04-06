package com.example.expensetracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorKeyboard {
    //variables
    private Context context;
    private View keyboard;
    private TextView[] characters, operators;
    private EditText editTxtAmount;
    private Double result, finalResult;
    private boolean add, subtract, multiply, divide;

    //constructor
    public CalculatorKeyboard(Context c, View view, boolean is_reference) {
        this.context = c;
        //inflate layout
        if (is_reference) {
            keyboard = view.findViewById(R.id.keyboard_parent);
            initializeComponents();
        } else {
            keyboard = View.inflate(context, R.layout.layout_keyboard, null);
            initializeComponents();
            ((ViewGroup) view).removeAllViews();
            ((ViewGroup) view).addView(keyboard);
        }
    }

    private void initializeComponents() {
        //array of ids
        int[] ids = {
                R.id.key_one, R.id.key_two, R.id.key_three, R.id.key_four, R.id.key_five, R.id.key_six,
                R.id.key_seven, R.id.key_eight, R.id.key_nine, R.id.key_dot, R.id.key_zero
        };

        //array of operator ids
        int[] operatorsIds = {
                R.id.key_plus, R.id.key_minus, R.id.key_multiply, R.id.key_equal,
                R.id.key_divide, R.id.key_clear, R.id.key_back
        };

        //array of operator text
        String[] operatorsInputs = {
                "+", "-", "*", "=", "÷", "C", "Back"
        };

        //array of number text
        String[] input = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0"
        };

        //textview for number characters
        characters = new TextView[ids.length];
        for (int i = 0; i < ids.length; i++) {
            //for all number characters, get view and get inputted number
            characters[i] = keyboard.findViewById(ids[i]);
            characters[i].setText(input[i]);
        }
        //textview for operators
        operators = new TextView[operatorsIds.length];
        for (int j = 0; j < operators.length; j++) {
            //for all operators, get view and get inputted operator
            operators[j] = keyboard.findViewById(operatorsIds[j]);
            operators[j].setText(operatorsInputs[j]);
        }

        //Delete
        keyboard.findViewById(operatorsIds[6]).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    editTxtAmount.setSelection(editTxtAmount.getText().length());
                    editTxtAmount.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                }
                return true;
            }
        });
        //Clear
        keyboard.findViewById(operatorsIds[5]).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    editTxtAmount.setText("");
                }
                return true;
            }
        });
        //Dot
        keyboard.findViewById(ids[9]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s.", editTxtAmount.getText()));
        });
        //1
        keyboard.findViewById(ids[0]).setOnClickListener((View view) -> {
            editTxtAmount.setSelection(editTxtAmount.getText().length());
            editTxtAmount.setText(String.format("%s1", editTxtAmount.getText()));
        });
        //2
        keyboard.findViewById(ids[1]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s2", editTxtAmount.getText()));
        });
        //3
        keyboard.findViewById(ids[2]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s3", editTxtAmount.getText()));
        });
        //4
        keyboard.findViewById(ids[3]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s4", editTxtAmount.getText()));
        });
        //5
        keyboard.findViewById(ids[4]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s5", editTxtAmount.getText()));
        });
        //6
        keyboard.findViewById(ids[5]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s6", editTxtAmount.getText()));
        });
        //7
        keyboard.findViewById(ids[6]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s7", editTxtAmount.getText()));
        });
        //8
        keyboard.findViewById(ids[7]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s8", editTxtAmount.getText()));
        });
        //9
        keyboard.findViewById(ids[8]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s9", editTxtAmount.getText()));
        });
        //0
        keyboard.findViewById(ids[10]).setOnClickListener((View view) -> {
            editTxtAmount.setText(String.format("%s0", editTxtAmount.getText()));
        });
        //Plus
        keyboard.findViewById(operatorsIds[0]).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(String.valueOf(editTxtAmount.getText()));
                    add = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Minus
        keyboard.findViewById(operatorsIds[1]).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(String.valueOf(editTxtAmount.getText()));
                    subtract = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Multiply
        keyboard.findViewById(operatorsIds[2]).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(String.valueOf(editTxtAmount.getText()));
                    multiply = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Divide
        keyboard.findViewById(operatorsIds[4]).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(String.valueOf(editTxtAmount.getText()));
                    divide = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Equal
        keyboard.findViewById(operatorsIds[3]).setOnClickListener((View view) -> {
            try {
                finalResult = Double.parseDouble(String.valueOf(editTxtAmount.getText()));
                if (add) {
                    editTxtAmount.setText(String.valueOf(result + finalResult));
                    add = false;
                }
                if (subtract) {
                    editTxtAmount.setText(String.valueOf(result - finalResult));
                    subtract = false;
                }
                if (multiply) {
                    editTxtAmount.setText(String.valueOf(result * finalResult));
                    multiply = false;
                }
                if (divide) {
                    editTxtAmount.setText(String.valueOf(result / finalResult));
                    divide = false;
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
    }

    //when edittext is in focus, slide in the keyboard
    public void show(EditText focus) {
        this.editTxtAmount = focus;
        keyboard.animate().y(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                keyboard.setVisibility(View.VISIBLE);
            }
        });
    }

    //hide the keyboard
    public void hide() {
        if (isVisible()) {
            keyboard.animate().y(keyboard.getHeight()).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    keyboard.setVisibility(View.GONE);
                }
            });
        }
    }

    //make keyboard visible
    boolean isVisible() {
        return keyboard.getVisibility() == View.VISIBLE;
    }
}
