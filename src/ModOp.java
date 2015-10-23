/**
 * Created by austinsun on 10/15/15.
 */
public class ModOp extends ArithmeticOp{
    public ModOp(STO a, STO b){
        super(a, "%", b);
    }
    @Override
    STO checkOperands(STO a, STO b) {
        Type aType = a.getType();
        Type bType = b.getType();
        if (!(aType.isEquivalentTo(new IntType()))){
            return new ErrorSTO("Modulus",aType,"%");
        } 
        else if(!(bType.isEquivalentTo(new IntType()))) {
            // error
            return new ErrorSTO("Modulus",bType,"%");
        } else{
            return new ExprSTO("int",new IntType());
        }
    }
}

