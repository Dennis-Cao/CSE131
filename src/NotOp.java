// Oodrye

public class NotOp extends UnaryOp{
    public NotOp(String name, String symbol, STO op)
    {
        super(name, symbol, op);
    }

    public NotOp(STO op)
    {
        super("Not", "!", op);
    }

    public STO checkOperands(STO a) 
    {
        Type aType = a.getType();
        if ( !(aType.isBool()) ) 
        {
            return new ErrorSTO("Not",aType.toString(),"!");
        }
        else 
        {
            Type typ = new BoolType();
            return new ExprSTO("Bool", typ);
        }
    }
}