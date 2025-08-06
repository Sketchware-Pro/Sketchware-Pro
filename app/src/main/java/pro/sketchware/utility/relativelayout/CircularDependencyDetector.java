package pro.sketchware.utility.relativelayout;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for detecting IllegalStateException: Circular Dependencies in RelativeLayout parent attributes
 */

public class CircularDependencyDetector {

    private final ArrayList<ViewBean> beans;
    private final ViewBean bean;

    public CircularDependencyDetector(ArrayList<ViewBean> beans, ViewBean bean) {
        this.beans = beans;
        this.bean = bean;
    }

    /**
     * Validates if the given attribute for a target view leads to a circular dependency.
     *
     * @param targetId The ID of the target view.
     * @param attr     The layout attribute to validate.
     * @return True if no circular dependency is detected, false otherwise.
     */
    public boolean isLegalAttribute(String targetId, String attr) {
        for (ViewBean viewBean : beans) {
            if (viewBean.id.equals(targetId)) {
                // check for all related duplicate attributes
                if (viewBean.parentAttributes.containsKey(attr)) {
                    if (bean.id.equals(viewBean.parentAttributes.get(attr))) {
                        return false;
                    }
                }

                // Check for all related opposite attributes
                String oppositeAttr = getOppositeAttribute(attr);
                if (viewBean.parentAttributes.containsKey(oppositeAttr)) {
                    if (bean.id.equals(viewBean.parentAttributes.get(oppositeAttr))) {
                        return false;
                    }
                }

                // Check for all related conflicting attributes
                for (String conflictAttr : getConflictingAttributes(attr)) {
                    if (viewBean.parentAttributes.containsKey(conflictAttr)) {
                        if (bean.id.equals(viewBean.parentAttributes.get(conflictAttr))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private String getOppositeAttribute(String attr) {
        String attribute = attr.substring("android:layout_".length());
        return "android:layout_" + getOpposite(attribute);
    }

    private List<String> getConflictingAttributes(String attr) {
        String attribute = attr.substring("android:layout_".length());
        List<String> conflicts = getConflicts(attribute);
        return conflicts.stream()
                .map(conflict -> "android:layout_" + conflict)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to get the opposite layout attribute.
     *
     * @param attribute The layout relationship.
     * @return The opposite layout relationship.
     */
    private String getOpposite(String attribute) {
        return switch (attribute) {
            case "below" -> "above";
            case "above" -> "below";
            case "toStartOf" -> "toEndOf";
            case "toEndOf" -> "toStartOf";
            case "toLeftOf" -> "toRightOf";
            case "toRightOf" -> "toLeftOf";
            case "alignStart" -> "alignEnd";
            case "alignEnd" -> "alignStart";
            case "alignLeft" -> "alignRight";
            case "alignRight" -> "alignLeft";
            case "alignTop" -> "alignBottom";
            case "alignBottom" -> "alignTop";
            default -> attribute;
        };
    }

    /**
     * Helper method to retrieve conflicts for a given attribute.
     *
     * @param attribute The layout relationship.
     * @return A list of conflicting layout relationships.
     */
    private List<String> getConflicts(String attribute) {
        return switch (attribute) {
            case "below", "above" -> List.of("alignTop", "alignBottom");
            case "toStartOf", "toEndOf" -> List.of("toLeftOf", "toRightOf");
            case "toLeftOf", "toRightOf" -> List.of("toStartOf", "toEndOf");
            case "alignStart", "alignEnd" -> List.of("alignLeft", "alignRight");
            case "alignLeft", "alignRight" -> List.of("alignStart", "alignEnd");
            case "alignTop", "alignBottom" -> List.of("above", "below");
            case "alignBaseline" -> List.of("alignTop", "alignBottom", "toLeftOf", "toRightOf",
                    "toStartOf", "toEndOf", "alignLeft", "alignRight", "alignStart", "alignEnd", "above", "below");
            default -> List.of();
        };
    }
    // I tested this class with some complex Circular Dependency cases,
    // and it seems to be working fine. I hope this code is able to detect all possible cases, or at least most of them.

}
