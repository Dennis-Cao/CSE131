
abstract class Operator {
    private String op_name;
    public Operator(String name){
        setName(name);
    }
    //getter
    public String getName(){
        return op_name;
    }

    //setter
    private void setName(String name){
        op_name=name;
    }
}
