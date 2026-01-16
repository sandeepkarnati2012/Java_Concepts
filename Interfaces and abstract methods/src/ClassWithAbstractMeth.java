public abstract class ClassWithAbstractMeth {
    public abstract  String absMeth1(int num);
    protected abstract boolean absMeth2(String str);
    abstract float absMeth3(int num, String str);

    public static void main(String[] args) {
        System.out.println("This is a class with abstract methods");
    }
}