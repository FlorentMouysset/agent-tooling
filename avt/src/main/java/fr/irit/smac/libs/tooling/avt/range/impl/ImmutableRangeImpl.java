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
package fr.irit.smac.libs.tooling.avt.range.impl;

public class ImmutableRangeImpl extends AbstractRange {

	private final double lowerBound;
	private final double upperBound;

	public ImmutableRangeImpl(double lowerBound, double upperBound) {
		super();

		this.lowerBound = lowerBound;
		this.upperBound = upperBound;

		this.checkBoundsConsistency();
	}

	@Override
	public double getLowerBound() {
		return this.lowerBound;
	}

	@Override
	public double getUpperBound() {
		return this.upperBound;
	}

}
