/**
 * Created by austinsun on 10/15/15.
 */
public class BoolType extends BasicType {
    public BoolType()
    {
        super("Bool", 1);
    }

    @Override
    public boolean  isBool() { return true; }

    @Override
    public boolean  isAssignableTo(Type typ) 
    {
        if(typ instanceof BoolType)
        {
            return true;
        }
        return false;
    }

    public STO checkOperands(STO a) 
    {
        Type aType = a.getType();
        //check if a is the same as b
        if ( !(aType.isBool()) ) 
        {
            // error
            return new ErrorSTO("Wrong Arithmetic Type");
        }
        else 
        {
            Type typ = new BoolType();
            return new ExprSTO("Bool", typ);
        }
    }

}
