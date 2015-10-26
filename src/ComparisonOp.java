
abstract class ComparisonOp extends BinaryOp{
    String symbol;

    public ComparisonOp(STO a, String sym, STO b){
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
            return new ErrorSTO("Comparison",aType, symbol);
        }
        else if (!(bType instanceof NumericType)){
            return new ErrorSTO("Comparison",bType, symbol);
        }
        else{
            return new ExprSTO("bool",new BoolType());
        }
    }
}
