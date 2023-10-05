package sde.virginia.edu.hw3;

public class ApportionmentMethodFactory {
    public ApportionmentMethod getDefaultMethod() {
        return new HuntingtonHillMethod();
    }

    public ApportionmentMethod getMethod(String method) {
        if (method.equals("adams")) {
            return new AdamsMethod();
        }
        if (method.equals("jefferson")) {
            return new JeffersonMethod();
        }
        if (method.equals("huntington")) {
            return new HuntingtonHillMethod();
        }

        throw new IllegalArgumentException("the given input is invalid. valid inputs: adams, jefferson, or huntington");
    }
}