/**
 * Created by austinsun on 10/15/15.
 */
abstract class NumericType extends BasicType{
    public NumericType(){
    	super("Numeric", 4);
    }

    public NumericType(String str){
    	super(str, 4);
    }

    @Override
    public boolean  isNumeric() { return true; }



}
