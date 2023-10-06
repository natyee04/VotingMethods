package sde.virginia.edu.hw3;

public class ApportionmentMethodFactory {
    public ApportionmentMethod getDefaultMethod() {
        return new HuntingtonHillMethod();
    }

    public ApportionmentMethod getMethod(String method) {
        if (method.equalsIgnoreCase("adams")) {
            return new AdamsMethod();
        }
        if (method.equalsIgnoreCase("jefferson")) {
            return new JeffersonMethod();
        }
        if (method.equalsIgnoreCase("huntington")) {
            return new HuntingtonHillMethod();
        }

        throw new IllegalArgumentException("the given input is invalid. valid inputs: adams, jefferson, or huntington");
    }
}