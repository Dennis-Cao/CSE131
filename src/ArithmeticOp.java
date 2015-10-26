
abstract class ArithmeticOp extends BinaryOp{
    String symbol;

    public ArithmeticOp(STO a, String sym, STO b){
        super(a,b);
        setSymbol(sym);
    }

    public String getSymbol(){
        return symbol;
    }

    private void setSymbol(String sym){
        symbol=sym;
    }

    STO checkOperands(STO a, STO b) {
        Type aType,bType;
        if(a.isFunc())
            aType = ((FuncSTO)a).getReturnType();
        else
            aType = a.getType();
        if(b.isFunc())
            bType = ((FuncSTO)b).getReturnType();
        else
            bType = b.getType();
        if (!(aType instanceof NumericType)){
            return new ErrorSTO("Arithmetic",aType, symbol);
        }
        else if (!(bType instanceof NumericType)){
            return new ErrorSTO("Arithmetic",bType, symbol);
        }
        else if (aType instanceof IntType && bType instanceof IntType) {
            return new ExprSTO("int",new IntType());
        } else {
            return new ExprSTO("float",new FloatType());
        }
    }
}
