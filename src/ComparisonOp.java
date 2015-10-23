/**
 * Created by austinsun on 10/16/15.
 */
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
        Type aType = a.getType();
        Type bType = b.getType();
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
