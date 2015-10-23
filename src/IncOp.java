// Oodrye

public class IncOp extends UnaryOp{
    boolean time;
    public IncOp(String name, String symbol, STO op, boolean time)
    {
        super(name, symbol, op);
        this.time=time;
    }

    public IncOp(STO op)
    {
        super("Inc", "++", op);
    }
    
    public STO checkOperands(STO a) 
    {
        Type aType = a.getType();
        //check if a is the same as b
        if ( !(aType.isNumeric()) ) 
        {
            // error
            return new ErrorSTO("IncOp",aType,symbol);
        }
        else 
        {
        	Type typ;
        	if(aType instanceof FloatType)
        	{
	            typ = new FloatType();
	            return new ExprSTO("Float", typ);
        	}
        	typ = new IntType();
        	return new ExprSTO("Int", typ);
        }
    }

}