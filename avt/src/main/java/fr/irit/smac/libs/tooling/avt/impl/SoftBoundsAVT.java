/*
 * #%L
 * avt
 * %%
 * Copyright (C) 2014 - 2015 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.libs.tooling.avt.impl;

import fr.irit.smac.libs.tooling.avt.EFeedback;
import fr.irit.smac.libs.tooling.avt.deltamanager.IDeltaManagerFactory;

public class SoftBoundsAVT extends StandardAVT {

    private static final String VALUE_NAN       = "value isNaN";
    private static final String FEEDBACK_NULL   = "feedback is null";
    private static final String LOWER_BOUND_NAN = "lowerBound isNaN";
    private static final String UPPER_BOUND_NAN = "upperBound isNaN";

    public SoftBoundsAVT(double lowerBound, double upperBound, double startValue,
        IDeltaManagerFactory<?> deltaManagerFactory) {
        super(lowerBound, upperBound, startValue, deltaManagerFactory);
    }

    @Override
    public void setLowerBound(double lowerBound) {
        super.setLowerBound(lowerBound);
        this.updateValueFromBounds();
        this.updateDMFromBounds();
    }

    @Override
    public void setUpperBound(double upperBound) {
        super.setUpperBound(upperBound);
        this.updateValueFromBounds();
        this.updateDMFromBounds();
    }

    @Override
    public void setValue(double value) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(VALUE_NAN);
        }
        this.value = value;

        if (!this.getRange().isInsideRange(this.value)) {
            this.updateBoundsFromValue(this.value);
        }

        // the value has been changed and then the lasts feedbacks are
        // not related to this new value
        this.deltaManager.getAdvancedDM().resetState();
    }

    @Override
    public void adjustValue(EFeedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException(FEEDBACK_NULL);
        }

        // 1 - Updates the delta value
        this.updateDelta(feedback);

        // 2 - Adjust the current value within the Double representation
        if (feedback != EFeedback.GOOD) {
            this.value = Math.min(Double.MAX_VALUE, Math.max(-Double.MAX_VALUE,
                this.value + this.deltaManager.getDelta() * (feedback == EFeedback.GREATER ? 1 : -1)));
        }

        // 3 - if value is outside the bounds then move the bounds
        if (!this.range.isInsideRange(this.value)) {
            this.updateBoundsFromValue(this.value);
        }
    }

    @Override
    public double getValueIf(EFeedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException(FEEDBACK_NULL);
        }

        double valueIf = this.value;

        // 1 - Updates the delta value
        double delta = this.deltaManager.getAdvancedDM().getDeltaIf(this.getDirectionFromFreedback(feedback));

        // 2 - Adjust the current value within the Double representation
        if (feedback != EFeedback.GOOD) {
            valueIf = Math.min(Double.MAX_VALUE,
                Math.max(-Double.MAX_VALUE, valueIf + delta * (feedback == EFeedback.GREATER ? 1 : -1)));
        }

        return valueIf;
    }

    private void updateBoundsFromValue(double value) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(VALUE_NAN);
        }

        if (this.getRange().getLowerBound() > value) {
            this.setLowerBoundFromValue(value);
        }

        if (this.getRange().getUpperBound() < value) {
            this.setUpperBoundFromValue(value);
        }

    }

    private void setLowerBoundFromValue(double lowerBound) {
        if (Double.isNaN(lowerBound)) {
            throw new IllegalArgumentException(LOWER_BOUND_NAN);
        }

        super.setLowerBound(lowerBound);
        this.updateDMFromBounds();
    }

    private void setUpperBoundFromValue(double upperBound) {
        if (Double.isNaN(upperBound)) {
            throw new IllegalArgumentException(UPPER_BOUND_NAN);
        }
        super.setUpperBound(upperBound);
        this.updateDMFromBounds();
    }

    private void setValueFromBounds(double value) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException(VALUE_NAN);
        }
        this.value = value;

        // the value has been changed and then the lasts feedbacks are
        // not related to this new value
        this.deltaManager.getAdvancedDM().resetState();
    }

    private void updateValueFromBounds() {
        if (this.effectiveLowerBound > this.value) {
            this.setValueFromBounds(this.effectiveLowerBound);
        }
        else if (this.effectiveUpperBound < this.value) {
            this.setValueFromBounds(this.effectiveUpperBound);
        }
    }
}
