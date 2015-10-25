/**
 * Created by austinsun on 10/16/15.
 */
public class EqualOp extends ComparisonOp{
    public EqualOp(STO a, STO b){
        super(a,"==",b);
    }
    public STO checkOperands(STO a, STO b){
    	Type aType,bType;
        if(a.isFunc())
            aType = ((FuncSTO)a).getReturnType();
        else
            aType = a.getType();
        if(b.isFunc())
            bType = ((FuncSTO)b).getReturnType();
        else
            bType = b.getType();
        if (((aType instanceof NumericType) && (!(bType instanceof NumericType))) || ((aType instanceof BoolType) && (!(bType instanceof BoolType)))){
            return new ErrorSTO("Equality",aType.toString(),bType.toString(), symbol);
        }
        else{
            return new ExprSTO("bool",new BoolType());
        }
    }
}

