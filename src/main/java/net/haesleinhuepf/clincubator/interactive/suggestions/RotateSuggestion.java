package net.haesleinhuepf.clincubator.interactive.suggestions;

import net.haesleinhuepf.clincubator.utilities.SuggestedPlugin;

public interface RotateSuggestion extends SuggestedPlugin {
    default Class[] suggestedNextSteps() {
        return new Class[] {
                net.haesleinhuepf.clincubator.interactive.generated.MaximumZProjection.class,
net.haesleinhuepf.clincubator.interactive.handcrafted.CylinderTransform.class,
net.haesleinhuepf.clincubator.interactive.generated.TopHat.class
        };
    }

    default Class[] suggestedPreviousSteps() {
        return new Class[]{
                net.haesleinhuepf.clincubator.interactive.generated.Translate.class,
net.haesleinhuepf.clincubator.interactive.generated.TransposeXY.class
        };
    }
}
