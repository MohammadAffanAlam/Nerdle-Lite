import java.util.Random;

public class NerdleEquation {
    private Random random = new Random();
    private Boolean operation;
    private String num1;
    private String num2;
    private String num3;

    public NerdleEquation() { // Creates a random equation
        if (this.random.nextBoolean()) {
            this.operation = Boolean.valueOf(true);
        } else {
            this.operation = Boolean.valueOf(false);
        }
        this.num1 = Integer.toString(this.random.nextInt(100));
        if (this.num1.length() == 1) {
            this.num1 = "0" + this.num1;
        }
        if (this.operation.booleanValue()) {
            this.num2 = Integer.toString(this.random.nextInt(100 - Integer.parseInt(this.num1)));
            if (this.num2.length() == 1)
                this.num2 = "0" + this.num2;
            this.num3 = Integer.toString(Integer.parseInt(this.num1) + Integer.parseInt(this.num2));
        } else {
            this.num2 = Integer.toString(this.random.nextInt(Integer.parseInt(this.num1)));
            if (this.num2.length() == 1)
                this.num2 = "0" + this.num2;
            this.num3 = Integer.toString(Integer.parseInt(this.num1) - Integer.parseInt(this.num2));
            if (this.num3.length() == 1)
                this.num3 = "0" + this.num3;
        }
    }

    public Boolean rightPosCheck(String num, int pos) { // Checks if an integer is in the right position
        String realOperator;
        Boolean rightPos = Boolean.valueOf(false);
        switch (pos) {
            case 0:
                if (num.matches(this.num1.substring(0, 1)))
                    rightPos = Boolean.valueOf(true);
                break;
            case 1:
                if (num.matches(this.num1.substring(1)))
                    rightPos = Boolean.valueOf(true);
                break;
            case 2:
                if (this.operation.booleanValue()) {
                    realOperator = "\\+";
                } else {
                    realOperator = "-";
                }
                if (num.matches(realOperator)) {
                    rightPos = Boolean.valueOf(true);
                    break;
                }
                rightPos = Boolean.valueOf(false);
                break;
            case 3:
                if (num.matches(this.num2.substring(0, 1)))
                    rightPos = Boolean.valueOf(true);
                break;
            case 4:
                if (num.matches(this.num2.substring(1)))
                    rightPos = Boolean.valueOf(true);
                break;
            case 6:
                if (num.matches(this.num3.substring(0, 1)))
                    rightPos = Boolean.valueOf(true);
                break;
            case 7:
                if (num.matches(this.num3.substring(1)))
                    rightPos = Boolean.valueOf(true);
                break;
        }
        return rightPos;
    }

    public int diffPosCheck(String num, intList slots) { // Checks if an integer is in the equation but wrong position
        Boolean diffPos = Boolean.valueOf(false);
        for (int i = 0; i < slots.getSize(); i++) {
            switch (slots.getNode(i).getNum()) {
                case 0:
                    if (num.matches(this.num1.substring(0, 1)))
                        diffPos = Boolean.valueOf(true);
                    break;
                case 1:
                    if (num.matches(this.num1.substring(1)))
                        diffPos = Boolean.valueOf(true);
                    break;
                case 3:
                    if (num.matches(this.num2.substring(0, 1)))
                        diffPos = Boolean.valueOf(true);
                    break;
                case 4:
                    if (num.matches(this.num2.substring(1)))
                        diffPos = Boolean.valueOf(true);
                    break;
                case 6:
                    if (num.matches(this.num3.substring(0, 1)))
                        diffPos = Boolean.valueOf(true);
                    break;
                case 7:
                    if (num.matches(this.num3.substring(1)))
                        diffPos = Boolean.valueOf(true);
                    break;
            }
            if (diffPos.booleanValue())
                return i;
        }
        return -1;
    }

    public void setNum(String num1) { this.num1 = num1; }

    public void setNum2(String num2) { this.num2 = num2; }

    public void setNum3(String num3) { this.num3 = num3; }

    public void setOperation(Boolean Operation) { this.operation = Operation; }

    public String getNum() { return this.num1; }

    public String getNum2() { return this.num2; }

    public String getNum3() { return this.num3; }

    public Boolean getOperation() { return this.operation; }
}
