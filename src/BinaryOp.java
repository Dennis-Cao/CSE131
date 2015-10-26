
abstract class BinaryOp extends Operator{
    STO leftOp;
    STO rightOp;
    public BinaryOp(STO a, STO b){
        super("BinaryOp");
        setLeftOp(a);
        setRightOp(b);
    }


    abstract STO checkOperands(STO a, STO b);
    //setter methods
    private void setLeftOp(STO a){
        leftOp=a;
    }
    private void setRightOp(STO b){
        rightOp=b;
    }

    //getter methods
    public STO getLeftOp(){
        return leftOp;
    }
    public STO getRightOp(){
        return  rightOp;
    }
}
