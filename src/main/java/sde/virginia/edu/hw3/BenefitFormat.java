package sde.virginia.edu.hw3;

public class BenefitFormat implements RepresentationFormat{
    private DisplayOrder displayOrder;

    /**
     * Creates a population format in ascending order
     */
    public BenefitFormat() { this(DisplayOrder.DESCENDING);}

    public BenefitFormat(DisplayOrder displayOrder) {setDisplayOrder(displayOrder);}

    /**
     * Returns the display order
     * @return Either {@link DisplayOrder#ASCENDING ascending} or {@link DisplayOrder#DESCENDING descending}
     */
    public DisplayOrder getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(DisplayOrder displayOrder) {
        if (displayOrder == null) {
            throw new IllegalArgumentException("The display order cannot be null!");
        }
        this.displayOrder = displayOrder;
    }

    @Override
    public String getFormattedString(Representation representation) {
        //TODO
        return null;
    }
}
