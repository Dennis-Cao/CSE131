/**
 * Created by austinsun on 10/15/15.
 */
abstract class BooleanOp extends BinaryOp{
    String symbol;

    public BooleanOp(STO a, String sym, STO b){
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
        if((aType.isEquivalentTo(new BoolType())) && (bType.isEquivalentTo(new BoolType()))) {
            return new ExprSTO("bool",new BoolType());
        }
        else{
            return new ErrorSTO("Boolean",aType.toString(),bType.toString(),symbol);
        }
    }
}
