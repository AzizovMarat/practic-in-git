package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Controller {
    private static final String DIVIDE = "\\";
    private static final String MULTIPLY = "*";
    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String EQUAL = "=";
    private static final String SQRT = "SQRT";
    private static final String SQR = "SQR";
    private static final String ONEX = "1 \\";
    private static final String PERCENT = "%";

    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final String FOUR = "4";
    private static final String FIVE = "5";
    private static final String SIX = "6";
    private static final String SEVEN = "7";
    private static final String EIGHT = "8";
    private static final String NINE = "9";
    private static final String ZERO = "0";

    private String action = "";
    private BigDecimal firstNumber;
    private BigDecimal secondNumber;
    private BigDecimal result;
    private boolean firstIn = true;

    @FXML
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
            btnC, btnCE, btnPoint, btnBackSpace,
            btnDivide, btnMultiply, btnPlus, btnMinus, btnEqual, btnSQRT, btnSQR, btnONEX, btnPercent;
    @FXML
    private TextField workSpace, lastAction;

    @FXML
    private void click(ActionEvent event) {

        final String wsText = workSpace.getText();
        final String laText = lastAction.getText();

        Button sourceButton = (Button) event.getSource();

        if (sourceButton.equals(btn0)) {
            setTextOnWorkSpace(ZERO, wsText);
        } else if (sourceButton.equals(btn1)) {
            setTextOnWorkSpace(ONE, wsText);
        } else if (sourceButton.equals(btn2)) {
            setTextOnWorkSpace(TWO, wsText);
        } else if (sourceButton.equals(btn3)) {
            setTextOnWorkSpace(THREE, wsText);
        } else if (sourceButton.equals(btn4)) {
            setTextOnWorkSpace(FOUR, wsText);
        } else if (sourceButton.equals(btn5)) {
            setTextOnWorkSpace(FIVE, wsText);
        } else if (sourceButton.equals(btn6)) {
            setTextOnWorkSpace(SIX, wsText);
        } else if (sourceButton.equals(btn7)) {
            setTextOnWorkSpace(SEVEN, wsText);
        } else if (sourceButton.equals(btn8)) {
            setTextOnWorkSpace(EIGHT, wsText);
        } else if (sourceButton.equals(btn9)) {
            setTextOnWorkSpace(NINE, wsText);
        }

        if (sourceButton.equals(btnPoint)) {
            if (!workSpace.getText().contains(",")) {
                workSpace.setText(workSpace.getText() + ",");
                firstIn = false;
            }
        }

        if (sourceButton.equals(btnC)) {
            firstNumber = null;
            secondNumber = null;
            result = null;
            action = "";
            lastAction.clear();
            workSpace.setText("0");
            firstIn = true;
        }

        if (sourceButton.equals(btnCE)) {
            workSpace.setText("0");
            firstIn = true;
        }

        if (sourceButton.equals(btnBackSpace)) {
            if (!(wsText.length() < 2)) {
                String temp = wsText.substring(0, wsText.length() - 1);
                workSpace.setText(temp);
                if (temp.equals(ZERO)) {
                    firstIn = true;
                }
            } else {
                workSpace.setText("0");
                firstIn = true;
            }
        }
        if (sourceButton.equals(btnEqual)) {
            doAction(action, wsText);
            if (result != null) {
//                String temp = deleteZero(String.valueOf(result).replace('.', ','));
                lastAction.setText(laText + " " + deleteZero(wsText) + " = ");
                result = null;
                action = EQUAL;
            }
        } else if (sourceButton.equals(btnDivide)) {
            doAction(DIVIDE, wsText);
        } else if (sourceButton.equals(btnMultiply)) {
            doAction(MULTIPLY, wsText);
        } else if (sourceButton.equals(btnPlus)) {
            doAction(PLUS, wsText);
        } else if (sourceButton.equals(btnMinus)) {
            doAction(MINUS, wsText);
        } else if (sourceButton.equals(btnSQRT)) {
            doAction(SQRT, wsText);
        } else if (sourceButton.equals(btnSQR)) {
            doAction(SQR, wsText);
        } else if (sourceButton.equals(btnONEX)) {
            doAction(ONEX, wsText);
        } else if (sourceButton.equals(btnPercent)) {
            doAction(PERCENT, wsText);
        }
    }

    private void doAction(String act, String wsText) {

        String temp;

        if (act.equals(SQRT)) {
            firstNumber = new BigDecimal(wsText.replace(',', '.'));
            result = firstNumber.sqrt(new MathContext(10));
            temp = deleteZero(String.valueOf(result).replace('.', ','));
            workSpace.setText(temp);
            lastAction.setText(act + "(" + wsText + ")");

        } else if (act.equals(SQR)) {
            firstNumber = new BigDecimal(wsText.replace(',', '.'));
            result = firstNumber.pow(2);
            temp = deleteZero(String.valueOf(result).replace('.', ','));
            workSpace.setText(temp);
            lastAction.setText(act + "(" + wsText + ")");

        } else if (act.equals(ONEX)) {
            firstNumber = new BigDecimal(wsText.replace(',', '.'));
            try {
                result = BigDecimal.ONE.divide(firstNumber, 8, RoundingMode.HALF_UP);
                temp = deleteZero(String.valueOf(result).replace('.', ','));
                workSpace.setText(temp);
                lastAction.setText(act + "(" + wsText + ")");
            } catch (ArithmeticException e) {
                workSpace.setText("Результат не определен");
                firstNumber = null;
                secondNumber = null;
                result = null;
                action = "";
                lastAction.clear();
                firstIn = true;
            }

        } else if (action.equals("") || firstIn || action.equals(EQUAL)) {
            wsText = deleteZero(wsText);
            lastAction.setText(wsText + " " + act);
            workSpace.setText(wsText);
            firstNumber = new BigDecimal(wsText.replace(',', '.'));

        } else {
            secondNumber = new BigDecimal(wsText.replace(',', '.'));
            switch (act) {
                case DIVIDE:
                    try {
                        result = firstNumber.divide(secondNumber, 8, RoundingMode.HALF_UP);
                    } catch (ArithmeticException e) {
                        workSpace.setText("Результат не определен");
                        firstNumber = null;
                        secondNumber = null;
                        result = null;
                        action = "";
                        lastAction.clear();
                        firstIn = true;
                    }

                    temp = deleteZero(String.valueOf(result).replace('.', ','));
                    workSpace.setText(temp);
                    lastAction.setText(temp + " " + act);
                    break;
                case MULTIPLY:
                    result = firstNumber.multiply(secondNumber);
                    temp = deleteZero(String.valueOf(result).replace('.', ','));
                    workSpace.setText(temp);
                    lastAction.setText(temp + " " + act);
                    break;
                case PLUS:
                    result = firstNumber.add(secondNumber);
                    temp = deleteZero(String.valueOf(result).replace('.', ','));
                    workSpace.setText(temp);
                    lastAction.setText(temp + " " + act);
                    break;
                case MINUS:
                    result = firstNumber.subtract(secondNumber);
                    temp = deleteZero(String.valueOf(result).replace('.', ','));
                    workSpace.setText(temp);
                    lastAction.setText(temp + " " + act);
                    break;
                case PERCENT:
                    try {
                        result = firstNumber.multiply(secondNumber.divide(new BigDecimal(100), 8, RoundingMode.HALF_UP));
                    } catch (ArithmeticException e) {
                        workSpace.setText("Результат не определен");
                        firstNumber = null;
                        secondNumber = null;
                        result = null;
                        action = "";
                        lastAction.clear();
                        firstIn = true;
                    }

                    temp = deleteZero(String.valueOf(result).replace('.', ','));
                    workSpace.setText(temp);
                    lastAction.setText(lastAction.getText() + " " + temp);
                    break;
            }
            firstNumber = result;
        }
        firstIn = true;
        if (!act.equals(PERCENT)) {
            action = act;
        }
    }

    private void setTextOnWorkSpace(String num, String wsText) {

        if (firstIn) {
            workSpace.setText(num);
        } else {
            if (wsText.length() > 14) return;
            workSpace.setText(wsText + num);
        }
        if (!num.equals(ZERO)) {
            firstIn = false;
        }
    }

    private String deleteZero(String str) {
        if (str.contains(",")) {
            while (str.charAt(str.length() - 1) == '0') {
                str = str.substring(0, str.length() - 1);
            }
            if (str.charAt(str.length() - 1) == ',') {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    public void initialize() {
    }
}
