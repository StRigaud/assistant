package net.haesleinhuepf.clincubator.interactive.suggestions;

import net.haesleinhuepf.clincubator.utilities.SuggestedPlugin;

public interface ThresholdDefaultSuggestion extends SuggestedPlugin {
    default Class[] suggestedNextSteps() {
        return new Class[] {
                net.haesleinhuepf.clincubator.interactive.generated.BinaryNot.class
        };
    }

    default Class[] suggestedPreviousSteps() {
        return new Class[]{
                
        };
    }
}
