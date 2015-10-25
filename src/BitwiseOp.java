/**
 * Created by austinsun on 10/15/15.
 */
abstract class BitwiseOp extends BinaryOp{
    String symbol;
    public BitwiseOp(STO a, String sym,STO b){
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
        if (!(aType instanceof IntType)) {
            return new ErrorSTO("Bitwise",aType, symbol);
        }
        else if (!(bType instanceof IntType)){
            return new ErrorSTO("Bitwise",bType, symbol);
        }
        else{
            return new ExprSTO("Int",new IntType());
        }
    }
}
