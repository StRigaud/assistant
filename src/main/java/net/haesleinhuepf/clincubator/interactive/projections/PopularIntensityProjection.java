package net.haesleinhuepf.clincubator.interactive.projections;

import net.haesleinhuepf.clincubator.interactive.processing.BackgroundSubtraction;
import net.haesleinhuepf.clincubator.interactive.transform.CylinderProjection;
import net.haesleinhuepf.clincubator.interactive.transform.RigidTransform3D;
import net.haesleinhuepf.clincubator.interactive.transform.SphereProjection;
import net.haesleinhuepf.clincubator.utilities.SuggestedPlugin;

public interface PopularIntensityProjection extends SuggestedPlugin {
    default Class[] suggestedNextSteps() {
        return new Class[] {
        };
    }

    default Class[] suggestedPreviousSteps() {
        return new Class[]{
                BackgroundSubtraction.class,
                CylinderProjection.class,
                SphereProjection.class,
                RigidTransform3D.class
        };
    }

    default void refreshView() {}
}