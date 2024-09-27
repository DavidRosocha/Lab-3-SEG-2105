package com.example.lab3inputcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{

    private double valueOne = Double.NaN;
    private double valueTwo;
    private char currentOperator;
    private EditText display;
    private boolean isOperatorPressed = false; //Track when an operator is pressed
    private boolean isEqualPressed = false;    //Track when the equals button is pressed
    private boolean isNewNumber = true;        //Track if a new number has been entered after operator
    private boolean hasDecimal = false;        //Track if a decimal point has been entered

    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char EQU = '=';

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the display EditText
        display = findViewById(R.id.resultedit);
        display.setText("");  //Clear any default text
    }

    //This method will handle the number button clicks
    public void onClickNumber(View view) {
        if (isOperatorPressed || isEqualPressed)
        {
            // Clear the display after an operator or equals has been pressed
            display.setText("");
            isOperatorPressed = false;  // Reset the flag
            isEqualPressed = false;     // Reset the equals flag
            hasDecimal = false;         // Reset the decimal flag
        }

        //Append the number to the display
        int id = view.getId();
        if (id == R.id.button00)
        {
            display.append("0");
        } else if (id == R.id.button01)
        {
            display.append("1");
        } else if (id == R.id.button02)
        {
            display.append("2");
        } else if (id == R.id.button03)
        {
            display.append("3");
        } else if (id == R.id.button04)
        {
            display.append("4");
        } else if (id == R.id.button05)
        {
            display.append("5");
        } else if (id == R.id.button06)
        {
            display.append("6");
        } else if (id == R.id.button07)
        {
            display.append("7");
        } else if (id == R.id.button08)
        {
            display.append("8");
        } else if (id == R.id.button09)
        {
            display.append("9");
        }

        isNewNumber = true;  //Indicate that a new number has been entered
    }

    //This method handles the decimal button clicks
    public void onClickDecimal(View view) {
        if (!hasDecimal) {  // Only allow one decimal point
            if (isOperatorPressed || isEqualPressed)
            {
                //Clear the display after an operator or equals button has been pressed
                display.setText("");
                isOperatorPressed = false;  //Reset the flag
                isEqualPressed = false;     //Reset the equals flag
            }

            // Append the decimal point
            if (display.getText().toString().isEmpty())
            {
                display.append("0.");  //Start with "0." if the display is empty
            } else
            {
                display.append(".");  //Append the decimal to the exsting number
            }

            hasDecimal = true;  //Set the flag to indicate that a decimal has been added
        }
    }

    //This method handles the operator button clicks
    public void onClickOperator(View view)
    {
        String input = display.getText().toString();

        if (!input.isEmpty())
        {
            if (!Double.isNaN(valueOne) && isNewNumber)
            {
                //Perform the calculation based on the current operator only if a new number was entered
                calculate();
            } else
            {
                //Store the first number (valueOne) if no calculation has been done yet
                valueOne = Double.parseDouble(input);
            }
        }

        //Set the current operator based on which button was clicked
        int id = view.getId();
        if (id == R.id.buttonAdd)
        {
            currentOperator = ADDITION;
        } else if (id == R.id.buttonSub)
        {
            currentOperator = SUBTRACTION;
        } else if (id == R.id.buttonMult)
        {
            currentOperator = MULTIPLICATION;
        } else if (id == R.id.buttonDiv)
        {
            currentOperator = DIVISION;
        }

        isOperatorPressed = true;  // Set the flag to indicate an operator was pressed
        isNewNumber = false;       // Reset the flag since we are waiting for a new number
        hasDecimal = false;        // Reset the decimal flag since we're starting a new number
    }

    //This method handles the equals button clicks
    public void onClickEquals(View view)
    {
        calculate();
        isEqualPressed = true;  //Set the flag to indicate that the equals button was pressed
        isOperatorPressed = false;  //Reset the operator flag for the next operation
        isNewNumber = false;   //Reset since an operation was completed
    }

    //This method is used to perform the calculations
    private void calculate()
    {
        String input = display.getText().toString();

        // Check if the input is a valid number
        if (input.isEmpty() || !input.matches("[-+]?\\d*\\.?\\d+"))
        {
            display.setText("Error");  //Display an error if the input is invalid
            valueOne = 0;  //Set valueOne to 0 as a fallback option
            return;
        }

        valueTwo = Double.parseDouble(input);  //Convert the second value from the display

        switch (currentOperator)
        {
            case ADDITION:
                valueOne = valueOne + valueTwo;
                break;
            case SUBTRACTION:
                valueOne = valueOne - valueTwo;
                break;
            case MULTIPLICATION:
                valueOne = valueOne * valueTwo;
                break;
            case DIVISION:
                if (valueTwo != 0)
                {
                    valueOne = valueOne / valueTwo;
                }
                else
                {
                    display.setText("Error");  //Handle division by zero
                    return;
                }
                break;
        }

        display.setText(String.valueOf(valueOne));  //Display the result
    }

    //This method is used to clear the display
    public void onClickClear(View view)
    {
        display.setText("");
        valueOne = Double.NaN;
        valueTwo = Double.NaN;
        isOperatorPressed = false; //Reset the operator flag
        isEqualPressed = false;    //Reset the equals flag
        isNewNumber = true;        //Ready for new number input
        hasDecimal = false;        //Reset decimal flag
    }
}