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
        Type aType = a.getType();
        Type bType = b.getType();
        if (!(aType.isEquivalentTo(new IntType())) || !(bType.isAssignableTo(new IntType()))) {
            // error
            return new ErrorSTO("Error Bitwise");
        }
        else{
            return new ExprSTO("Int",new IntType());
        }
    }
}
