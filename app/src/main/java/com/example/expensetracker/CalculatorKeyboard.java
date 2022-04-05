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
        if (is_reference) {
            //get the keyboard view inflated and initialize components
            keyboard = view.findViewById(R.id.keyboard_parent);
            initializeComponents();
        } else {
            //if no keyboard view is inflated, inflate keyboard layout
            keyboard = View.inflate(context, R.layout.keyboard, null);
            //initialize components
            initializeComponents();
            //remove original view
            ((ViewGroup) view).removeAllViews();
            //and custom keyboard view
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

        for (int i = 0; i < ids.length; i++) {
            final int finalI = i;
            characters[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //string to store character text
                    String ss = characters[finalI].getText().toString();
                    //display text in edittext box
                    editTxtAmount.getText().insert(editTxtAmount.getSelectionStart(), ss);
                }
            });
        }
        //Delete
        keyboard.findViewById(R.id.key_back).setOnTouchListener(new View.OnTouchListener() {
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
        keyboard.findViewById(R.id.key_clear).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    editTxtAmount.setText("");
                }
                return true;
            }
        });
        //Dot
        keyboard.findViewById(R.id.key_dot).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + ".");
        });
        //1
        keyboard.findViewById(R.id.key_one).setOnClickListener((View view) -> {
            editTxtAmount.setSelection(editTxtAmount.getText().length());
            editTxtAmount.setText(editTxtAmount.getText() + "1");
        });
        //2
        keyboard.findViewById(R.id.key_two).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "2");
        });
        //3
        keyboard.findViewById(R.id.key_three).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "3");
        });
        //4
        keyboard.findViewById(R.id.key_four).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "4");
        });
        //5
        keyboard.findViewById(R.id.key_five).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "5");
        });
        //6
        keyboard.findViewById(R.id.key_six).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "6");
        });
        //7
        keyboard.findViewById(R.id.key_seven).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "7");
        });
        //8
        keyboard.findViewById(R.id.key_eight).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "8");
        });
        //9
        keyboard.findViewById(R.id.key_nine).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "9");
        });
        //0
        keyboard.findViewById(R.id.key_zero).setOnClickListener((View view) -> {
            editTxtAmount.setText(editTxtAmount.getText() + "0");
        });
        //Plus
        keyboard.findViewById(R.id.key_plus).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(editTxtAmount.getText() + "");
                    add = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Minus
        keyboard.findViewById(R.id.key_minus).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(editTxtAmount.getText() + "");
                    subtract = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Multiply
        keyboard.findViewById(R.id.key_multiply).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(editTxtAmount.getText() + "");
                    multiply = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Divide
        keyboard.findViewById(R.id.key_divide).setOnClickListener((View view) -> {
            try {
                if (editTxtAmount == null) {
                    editTxtAmount.setText("");
                } else {
                    result = Double.parseDouble(editTxtAmount.getText() + "");
                    divide = true;
                    editTxtAmount.setText(null);
                }
            } catch (Exception ex) {
                Log.d("Amount cannot be empty", ex.getMessage());
            }
        });
        //Equal
        keyboard.findViewById(R.id.key_equal).setOnClickListener((View view) -> {
            try {
                finalResult = Double.parseDouble(editTxtAmount.getText() + "");
                if (add == true) {
                    editTxtAmount.setText(result + finalResult + "");
                    add = false;
                }
                if (subtract == true) {
                    editTxtAmount.setText(result - finalResult + "");
                    subtract = false;
                }
                if (multiply == true) {
                    editTxtAmount.setText(result * finalResult + "");
                    multiply = false;
                }
                if (divide == true) {
                    editTxtAmount.setText(result / finalResult + "");
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
