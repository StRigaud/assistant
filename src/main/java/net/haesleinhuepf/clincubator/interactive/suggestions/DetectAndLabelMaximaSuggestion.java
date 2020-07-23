package net.haesleinhuepf.clincubator.interactive.suggestions;

import net.haesleinhuepf.clincubator.utilities.SuggestedPlugin;

public interface DetectAndLabelMaximaSuggestion extends SuggestedPlugin {
    default Class[] suggestedNextSteps() {
        return new Class[] {
                net.haesleinhuepf.clincubator.interactive.generated.ExtendLabelingViaVoronoi.class,
net.haesleinhuepf.clincubator.interactive.generated.ExcludeLabelsOutsideSizeRange.class
        };
    }

    default Class[] suggestedPreviousSteps() {
        return new Class[]{
                net.haesleinhuepf.clincubator.interactive.generated.MaximumZProjection.class,
net.haesleinhuepf.clincubator.interactive.generated.ThresholdDoG.class
        };
    }
}
